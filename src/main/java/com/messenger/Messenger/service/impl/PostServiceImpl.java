package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserProfileDAO;
import com.messenger.Messenger.dto.rq.RequestPostDTO;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.repository.PostRepository;
import com.messenger.Messenger.repository.UserProfileRepository;
import com.messenger.Messenger.service.PostService;
import com.messenger.Messenger.service.UserProfileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final UserProfileService userProfileService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public PostServiceImpl(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @SneakyThrows
    @Override
    public List<PostDAO> getPosts(Integer userid, Pageable pageable) {
        return postRepository
                .findByCreator(userProfileService.getUser(userid), pageable)
                .getContent();
    }

    @SneakyThrows
    @Override
    public PostDAO createPost(RequestPostDTO postDTO) {
        UserProfileDAO creator = userProfileService.getUser(postDTO.getCreator());
        Set<FileDAO> attachments = new HashSet<>(fileRepository.findAllById(postDTO.getAttachments()));

        PostDAO post = postRepository.save(postDTO.toDAO(creator, attachments));

        for (var item: creator.getFriends()
             ) {
            UserProfileDAO userProfileDAO = userProfileService.getUser(item);
            userProfileDAO.getFeed().add(post);
            userProfileRepository.save(userProfileDAO);
        }
        return post;
    }
}
