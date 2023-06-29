package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.dto.rs.ResponsePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface PostApi {

    @GetMapping("/{id}/post")
    ResponseEntity<List<ResponsePostDTO>> getPosts(@PathVariable("id")Integer id, @RequestParam("page") Integer page);

    @PostMapping("/post")
    ResponseEntity<ResponsePostDTO> createPost(@RequestBody RequestPostDTO postDTO);
}
