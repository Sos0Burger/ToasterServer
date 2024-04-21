package com.sosoburger.toaster.rest.api;

import com.sosoburger.toaster.dto.rq.RequestCommentDTO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
public interface CommentApi {
    @PutMapping("/{id}")
    ResponseEntity<ResponseCommentDTO> updateComment(@PathVariable("id")Integer id, @RequestBody RequestCommentDTO commentDTO);
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteComment(@PathVariable("id")Integer id);

    @GetMapping("/{id}")
    ResponseEntity<ResponseCommentDTO> getComment(@PathVariable("id")Integer id);

    @PutMapping("/{id}/like")
    ResponseEntity<ResponseCommentDTO> smashLike(@PathVariable("id")Integer id);
}
