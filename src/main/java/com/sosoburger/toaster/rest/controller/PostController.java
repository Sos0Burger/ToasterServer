package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.state.SortEnum;
import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dto.rq.RequestCommentDTO;
import com.sosoburger.toaster.dto.rq.RequestEditPost;
import com.sosoburger.toaster.dto.rq.RequestPostDTO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import com.sosoburger.toaster.dto.rs.ResponseFileDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.mapper.Mapper;
import com.sosoburger.toaster.rest.api.PostApi;
import com.sosoburger.toaster.service.CommentService;
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
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, UserServiceImpl userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }


    @Override
    public ResponseEntity<List<ResponsePostDTO>> getPosts(Integer page) {
        List<PostDAO> posts = postService.getPosts(getUserDetails().getId(), PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));

        List<ResponsePostDTO> postDTOs = new ArrayList<>();

        for (var item: posts
        ) {
            postDTOs.add(item.toDTO(getUserDetails().getId()));
        }
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> createPost(RequestPostDTO postDTO) {
        return new ResponseEntity<>(
                postService.createPost(
                postDTO,
                getUserDetails()).toDTO(getUserDetails().getId()),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseCommentDTO> createComment(Integer id, RequestCommentDTO commentDTO) {
        var comment = commentService.save(id, getUserDetails(), commentDTO);
        return new ResponseEntity<>(comment.toDTO(getUserDetails().getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> smashLike(Integer id) {
        var post = postService.smashLike(id, getUserDetails().getUserProfileDAO());
        return new ResponseEntity<>(post.toDTO(getUserDetails().getUserProfileDAO().getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> getPost(Integer id) {
        var post = postService.get(id);
        return new ResponseEntity<>(post.toDTO(getUserDetails().getUserProfileDAO().getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseCommentDTO>> getPostComments(Integer id, SortEnum sorting) {
        var response = Mapper.commentsToDTOList(postService.getCommentsWithSorting(id, sorting), getUserDetails().getUserProfileDAO().getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseFileDTO>> getPostFiles(Integer id) {
        var images = postService.get(id).getAttachments();
        if (images == null || images.isEmpty()){
            throw new NotFoundException("У поста нет изображений");
        }
        var response = Mapper.filesToFileDTOList(images);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> updatePost(Integer id, RequestEditPost post) {
        var response = postService.updatePost(id, post);
        return new ResponseEntity<>(response.toDTO(getUserDetails().getUserProfileDAO().getId()), HttpStatus.OK);
    }

    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
