package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.exception.AlreadyExistsException;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.UserProfileRepository;
import com.sosoburger.toaster.service.FileService;
import com.sosoburger.toaster.service.UserProfileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FileService fileService;

    @Override
    public UserProfileDAO create(UserDAO userDAO) {
        return userProfileRepository.save(new UserProfileDAO(
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null,
                userDAO));

    }

    @Override
    public UserProfileDAO getUser(Integer id) {
        if (userProfileRepository.existsById(id)) {
            return userProfileRepository.findById(id).get();
        }
        throw new NotFoundException("Пользователь не найден");
    }

    @Override
    public UserProfileDAO sendFriendRequest(Integer senderid, Integer receiverid) {
        //быстрый тест на шизофрению
        if (!(senderid.equals(receiverid))) {
            var sender = getUser(senderid);
            var receiver = getUser(receiverid);
            //Проверка есть ли пользователь в списке друзей
            if (!sender.getFriends().contains(receiverid) || !receiver.getFriends().contains(senderid)) {
                //Проверка на повторную отправку запроса
                if (!sender.getSent().contains(receiverid) || !receiver.getPending().contains(senderid)) {
                    //Проверка на дофига умных
                    if (sender.getPending().contains(receiverid) && receiver.getSent().contains(senderid)) {
                        return acceptFriendRequest(senderid, receiverid);
                    }
                    sender.getSent().add(receiverid);
                    receiver.getPending().add(senderid);
                    userProfileRepository.save(sender);
                    userProfileRepository.save(receiver);
                    return receiver;
                }
                throw new AlreadyExistsException("Вы уже отправили запрос в друзья этому пользователю");
            }
            throw new AlreadyExistsException("Вы уже добавили этого пользователя в друзья");
        }
        throw new AlreadyExistsException("Иди в дурку проверся");
    }

    @SneakyThrows
    @Override
    public UserProfileDAO acceptFriendRequest(Integer receiverid, Integer senderid) {
        var sender = getUser(senderid);
        var receiver = getUser(receiverid);
        if (receiver.getPending().contains(senderid) && sender.getSent().contains(receiverid)) {
            receiver.getPending().remove(senderid);
            sender.getSent().remove(receiverid);

            receiver.getFriends().add(senderid);
            sender.getFriends().add(receiverid);
            userProfileRepository.save(receiver);
            userProfileRepository.save(sender);
            return sender;
        }
        throw new NotFoundException("Пользователь не отправлял запрос в друзья");
    }

    @Override
    public List<UserProfileDAO> getFriends(UserDAO userDAO) {
        var friendList = userDAO.getUserProfileDAO().getFriends();
        List<UserProfileDAO> friends = new ArrayList<>();

        for (var item : friendList
        ) {
            friends.add(userProfileRepository.findById(item).get());
        }

        return friends;
    }

    @Override
    public List<UserProfileDAO> getPending(UserDAO userDAO) {
        List<UserProfileDAO> pendingList = new ArrayList<>();
        for (Integer item : userDAO.getUserProfileDAO().getPending()
        ) {
            pendingList.add(userProfileRepository.findById(item).get());
        }
        return pendingList;
    }

    @Override
    public List<UserProfileDAO> getSent(UserDAO userDAO) {
        List<UserProfileDAO> pendingList = new ArrayList<>();
        for (Integer item : userDAO.getUserProfileDAO().getSent()
        ) {
            pendingList.add(userProfileRepository.findById(item).get());
        }
        return pendingList;
    }

    @Override
    public UserProfileDAO updatePicture(Integer file, UserDAO userDAO) {
        var userProfileDAO = userDAO.getUserProfileDAO();
        userProfileDAO.setImage(fileService.findById(file));
        return userProfileRepository.save(userProfileDAO);
    }


    @Override
    public UserProfileDAO updateNickname(String nickname, UserDAO userDAO) {
        if (userProfileRepository.findByNickname(nickname).isEmpty()) {
            var userProfileDAO = userDAO.getUserProfileDAO();
            userProfileDAO.setNickname(nickname);
            return userProfileRepository.save(userProfileDAO);
        }
        throw new AlreadyExistsException("Такое имя уже занято");
    }

    @Override
    public void updateToken(String token, UserDAO userDAO) {
        var user = userDAO.getUserProfileDAO();
        user.setFirebaseToken(token);
        userProfileRepository.save(user);
    }

    @Override
    public List<PostDAO> getFeed(Integer id, Pageable page) {
        return getUser(id).getFeed().stream().toList();
    }
}
