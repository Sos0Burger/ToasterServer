package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.exception.AlreadyExistsException;
import com.messenger.Messenger.exception.AuthException;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDAO create(RequestUserDTO requestUserDTO) {
        if (!userRepository.findByEmail(requestUserDTO.getEmail()).isEmpty()) {
            throw new AlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        return userRepository.save(requestUserDTO.toDAO());
    }

    @SneakyThrows
    @Override
    public UserDAO getUser(Integer id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        throw new NotFoundException("Пользователь не найден");
    }

    @SneakyThrows
    @Override
    public Integer auth(RequestAuth auth) {
        var user = userRepository.findByEmail(auth.getEmail());
        if (!user.isEmpty()) {
            if (user.get(0).getPassword().equals(auth.getHash())) {
                return user.get(0).getId();
            }
        }
        throw new NotFoundException("Аккаунт не найден");
    }

    @SneakyThrows
    @Override
    public UserDAO sendFriendRequest(Integer senderid, Integer receiverid) {
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
                    userRepository.save(sender);
                    userRepository.save(receiver);
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
    public UserDAO acceptFriendRequest(Integer receiverid, Integer senderid) {
        var sender = getUser(senderid);
        var receiver = getUser(receiverid);
        if (receiver.getPending().contains(senderid) && sender.getSent().contains(receiverid)) {
            receiver.getPending().remove(senderid);
            sender.getSent().remove(receiverid);

            receiver.getFriends().add(senderid);
            sender.getFriends().add(receiverid);
            userRepository.save(receiver);
            userRepository.save(sender);
            return sender;
        }
        throw new NotFoundException("Пользователь не отправлял запрос в друзья");
    }


    @SneakyThrows
    @Override
    public List<UserDAO> getFriends(Integer id) {
        var friendList = getUser(id).getFriends();
        List<UserDAO> friends = new ArrayList<>();

        for (var item : friendList
        ) {
            friends.add(userRepository.findById(item).get());
        }

        return friends;
    }

    @SneakyThrows
    @Override
    public List<UserDAO> getPending(Integer id) {
        List<UserDAO> pendingList = new ArrayList<>();
        for (Integer item : getUser(id).getPending()
        ) {
            pendingList.add(userRepository.findById(item).get());
        }
        return pendingList;
    }

    @SneakyThrows
    @Override
    public List<UserDAO> getSent(Integer id) {
        List<UserDAO> pendingList = new ArrayList<>();
        for (Integer item : getUser(id).getSent()
        ) {
            pendingList.add(userRepository.findById(item).get());
        }
        return pendingList;
    }

    @SneakyThrows
    @Override
    public void updatePicture(Integer id, RequestAuth auth, String url) {
        UserDAO userDAO = getUser(id);
        if (userDAO.getEmail().equals(auth.getEmail()) && userDAO.getPassword().equals(auth.getHash())) {
            userDAO.setImage(url);
            userRepository.save(userDAO);
            return;
        }
        throw new AuthException("Неверная почта или пароль");
    }

    @SneakyThrows
    @Override
    public void updateNickname(Integer id, RequestAuth auth, String nickname) {
        UserDAO userDAO = getUser(id);
        if (userDAO.getEmail().equals(auth.getEmail()) && userDAO.getPassword().equals(auth.getHash())) {
            if (userRepository.findByNickname(nickname).isEmpty()) {
                userDAO.setNickname(nickname);
                userRepository.save(userDAO);
                return;
            }
            throw new AlreadyExistsException("Такое имя уже занято");
        }
        throw new AuthException("Неверная почта или пароль");
    }

    @Override
    public void updateToken(Integer id, String token) {
        UserDAO user = getUser(id);
        user.setFirebaseToken(token);
        userRepository.save(user);
    }
}
