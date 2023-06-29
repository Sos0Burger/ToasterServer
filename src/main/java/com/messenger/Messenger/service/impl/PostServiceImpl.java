package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.repository.PostRepository;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.PostService;
import com.messenger.Messenger.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public PostServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @SneakyThrows
    @Override
    public List<PostDAO> getPosts(Integer userid, Pageable pageable) {
        return postRepository
                .findByCreator(userService.getUser(userid), pageable)
                .getContent();
    }

    @SneakyThrows
    @Override
    public PostDAO createPost(RequestPostDTO postDTO) {
        UserDAO creator = userService.getUser(postDTO.getCreator());
        Set<FileDAO> attachments = new HashSet<>(fileRepository.findAllById(postDTO.getAttachments()));

        PostDAO post = postRepository.save(postDTO.toDAO(creator, attachments));

        for (var item: creator.getFriends()
             ) {
            UserDAO userDAO = userService.getUser(item);
            userDAO.getFeed().add(post);
            userRepository.save(userDAO);
        }
        return post;
    }
}
