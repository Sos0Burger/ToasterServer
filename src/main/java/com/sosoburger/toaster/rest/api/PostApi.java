package com.sosoburger.toaster.rest.api;

import com.sosoburger.toaster.SortEnum;
import com.sosoburger.toaster.dto.rq.RequestCommentDTO;
import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/post")
public interface PostApi {

    @GetMapping("")
    ResponseEntity<List<ResponsePostDTO>> getPosts(@RequestParam("page") Integer page);

    @PostMapping("")
    ResponseEntity<ResponsePostDTO> createPost(@RequestBody RequestPostDTO postDTO);

    @PostMapping("/{id}/comment")
    ResponseEntity<ResponseCommentDTO> createComment(@PathVariable("id")Integer id, @RequestBody RequestCommentDTO commentDTO);

    @PutMapping("/{id}/like")
    ResponseEntity<ResponsePostDTO> smashLike(@PathVariable("id")Integer id);

    @GetMapping("/{id}")
    ResponseEntity<ResponsePostDTO> getPost(@PathVariable("id") Integer id);

    @GetMapping("/{id}/comments")
    ResponseEntity<List<ResponseCommentDTO>> getPostComments(@PathVariable("id")Integer id, @RequestParam("sort")SortEnum sorting);
}
