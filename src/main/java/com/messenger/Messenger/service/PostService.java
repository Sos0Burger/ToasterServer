package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dto.rq.RequestPostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    List<PostDAO> getPosts(Integer userid, Pageable pageable);

    PostDAO createPost(RequestPostDTO postDTO);
 }
