package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.PostRepository;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public ResponseEntity<?> getPosts(Integer userid, Pageable pageable) {
        if(userRepository.existsById(userid)){
            Page<PostDAO> posts = postRepository.findByCreator(userRepository.findById(userid).get(), pageable);
            return new ResponseEntity<>(posts.toList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Файла с таким ID не существует"), HttpStatus.NOT_FOUND);
    }
}
