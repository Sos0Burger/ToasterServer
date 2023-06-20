package com.messenger.Messenger.rest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
public interface PostApi {

    @GetMapping("/{id}/post")
    ResponseEntity<?> getPosts(@PathVariable("id")Integer id, @RequestParam("page") Integer page);
}
