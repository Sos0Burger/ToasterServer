package com.messenger.Messenger.service;

import com.messenger.Messenger.dto.rq.RequestPostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<?> getPosts(Integer userid, Pageable pageable);

    ResponseEntity<?> createPost(RequestPostDTO postDTO);
 }
