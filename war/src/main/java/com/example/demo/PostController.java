/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author hantsy
 */
@RestController
@RequestMapping(value = "/posts")
class PostController {
    
    private final PostRepository posts;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping(value = "")
    public Flux<Post> all() {
        return this.posts.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<Post> get(@PathVariable(value = "id") Long id) {
        return this.posts.findById(id);
    }
    
    @PostMapping(value = "")
    public Mono<Post> create(Post post) {
        return this.posts.createPost(post);
    }
    
}
