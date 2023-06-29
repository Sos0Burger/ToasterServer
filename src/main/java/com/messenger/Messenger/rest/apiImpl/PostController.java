package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.dto.rs.ResponsePostDTO;
import com.messenger.Messenger.rest.api.PostApi;
import com.messenger.Messenger.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController implements PostApi {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Override
    public ResponseEntity<List<ResponsePostDTO>> getPosts(Integer id, Integer page) {
        List<PostDAO> posts = postService.getPosts(id, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));

        List<ResponsePostDTO> postDTOs = new ArrayList<>();

        for (var item: posts
        ) {
            postDTOs.add(item.toDTO());
        }
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePostDTO> createPost(RequestPostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO).toDTO(), HttpStatus.CREATED);
    }
}
