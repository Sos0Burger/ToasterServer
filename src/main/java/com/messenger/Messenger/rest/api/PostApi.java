package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestPostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Body;

@RequestMapping("/user")
public interface PostApi {

    @GetMapping("/{id}/post")
    ResponseEntity<?> getPosts(@PathVariable("id")Integer id, @RequestParam("page") Integer page);

    @PostMapping("/post")
    ResponseEntity<?> createPost(@Body RequestPostDTO postDTO);
}
