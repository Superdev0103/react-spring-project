package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.Sort.Order.desc;


@Component
@Slf4j
class DataInitializer implements ApplicationRunner {

    private final DatabaseClient databaseClient;

    public DataInitializer(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start data initialization...");
        this.databaseClient.insert()
                .into("posts")
                //.nullValue("id", Integer.class)
                .value("title", "First post title")
                .value("content", "Content of my first post")
                .map((r, m) -> r.get("id", Integer.class)).all()
                .log()
                .thenMany(
                        this.databaseClient.select()
                                .from("posts")
                                .orderBy(Sort.by(desc("id")))
                                .as(Post.class)
                                .fetch()
                                .all()
                                .log()
                )
                .subscribe(null, null, () -> log.info("initialization is done..."));
    }
}