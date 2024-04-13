package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import com.sosoburger.toaster.rest.api.PostApi;
import com.sosoburger.toaster.service.PostService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController implements PostApi {

    @Autowired
    private final PostService postService;

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    public PostController(PostService postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @Override
    public ResponseEntity<List<ResponsePostDTO>> getPosts(Integer page) {
        List<PostDAO> posts = postService.getPosts(getUserDetails().getId(), PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));

        List<ResponsePostDTO> postDTOs = new ArrayList<>();

        for (var item: posts
        ) {
            postDTOs.add(item.toDTO());
        }
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> createPost(RequestPostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO, getUserDetails()).toDTO(), HttpStatus.CREATED);
    }
    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
