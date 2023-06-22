package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.rest.api.PostApi;
import com.messenger.Messenger.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostApi {

    @Autowired
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Override
    public ResponseEntity<?> getPosts(Integer id, Integer page) {
        return postService.getPosts(id, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));
    }

    @Override
    public ResponseEntity<?> createPost(RequestPostDTO postDTO) {
        return postService.createPost(postDTO);
    }
}
