package com.sosoburger.toaster.rest.api;

import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface PostApi {

    @GetMapping("/post")
    ResponseEntity<List<ResponsePostDTO>> getPosts(@RequestParam("page") Integer page);

    @PostMapping("/post")
    ResponseEntity<ResponsePostDTO> createPost(@RequestBody RequestPostDTO postDTO);
}
