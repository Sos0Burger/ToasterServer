package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dto.rq.RequestCommentDTO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import com.sosoburger.toaster.rest.api.CommentApi;
import com.sosoburger.toaster.service.CommentService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController implements CommentApi {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public ResponseEntity<ResponseCommentDTO> updateComment(Integer id, RequestCommentDTO commentDTO) {
        var comment = commentService.update(id, commentDTO);
        return new ResponseEntity<>(comment.toDTO(getUserDetails().getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteComment(Integer id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseCommentDTO> getComment(Integer id) {
        var comment = commentService.get(id);
        return new ResponseEntity<>(comment.toDTO(getUserDetails().getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseCommentDTO> smashLike(Integer id) {
        var comment = commentService.smashLike(id, getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(comment.toDTO(getUserDetails().getUserProfileDAO().getId()), HttpStatus.OK);
    }

    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
