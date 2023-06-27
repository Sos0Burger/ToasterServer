package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.repository.PostRepository;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.PostService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileRepository fileRepository;


    @SneakyThrows
    @Override
    public List<PostDAO> getPosts(Integer userid, Pageable pageable) {
        if(userRepository.existsById(userid)){
            return postRepository
                    .findByCreator(userRepository.findById(userid).get(), pageable)
                    .getContent();
        }
        throw new NotFoundException("Файла с таким ID не существует");
    }

    @SneakyThrows
    @Override
    public PostDAO createPost(RequestPostDTO postDTO) {
        if(userRepository.existsById(postDTO.getCreator())) {
            UserDAO creator = userRepository.findById(postDTO.getCreator()).get();
            Set<FileDAO> attachments = new HashSet<>(fileRepository.findAllById(postDTO.getAttachments()));
            //todo добавить добавление в ленту
            return postRepository.save(postDTO.toDAO(creator, attachments));
        }

        throw new NotFoundException("Пользователя с таким ID не существует");
    }
}
