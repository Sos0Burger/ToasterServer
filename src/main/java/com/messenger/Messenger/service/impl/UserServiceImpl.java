package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.FriendDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import com.messenger.Messenger.dto.rs.UserSettingsDTO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> create(RequestUserDTO requestUserDTO) {
        if (!userRepository.findByEmail(requestUserDTO.getEmail()).isEmpty()) {
            return new ResponseEntity<>(new ExceptionMessage("Пользователь с такой почтой уже существует"), HttpStatus.CONFLICT);
        }
        userRepository.save(requestUserDTO.toDAO());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
        if (userRepository.existsById(id)) {
            return new ResponseEntity<>(userRepository.findById(id).get().toUserSettingsDTO(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не найден"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<ResponseUserDTO>> getAll() {
        List<UserDAO> DAOlist = userRepository.findAll();
        List<ResponseUserDTO> DTOlist = new ArrayList<>();
        for (UserDAO user : DAOlist
        ) {
            DTOlist.add(user.toDTO());
        }
        return new ResponseEntity<>(DTOlist, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователя с таким id не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> auth(RequestAuth auth) {
        var user = userRepository.findByEmail(auth.getEmail());
        if (!user.isEmpty()) {
            if (user.get(0).getPassword().equals(auth.getPassword())) {
                return new ResponseEntity<>(user.get(0).getId(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ExceptionMessage("Аккаунт не найден"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> findByIds(List<Integer> ids) {
        List<UserDAO> DAOlist = new ArrayList<>();
        for (Integer item : ids
        ) {
            if (userRepository.existsById(item)) {
                DAOlist.add(userRepository.findById(item).get());
            } else {
                DAOlist.add(new UserDAO(-1, null, null, "Удаленный пользователь", null, null, null, null, null, null));
            }
        }
        List<ResponseUserDTO> DTOlist = new ArrayList<>();
        for (UserDAO user : DAOlist
        ) {
            DTOlist.add(user.toDTO());
        }
        return new ResponseEntity<>(DTOlist, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> sendFriendRequest(Integer senderid, Integer receiverid) {
        if (userRepository.existsById(senderid) && userRepository.existsById(receiverid)) {
            //быстрый тест на шизофрению
            if (!(senderid.equals(receiverid))) {
                var sender = userRepository.findById(senderid).get();
                var receiver = userRepository.findById(receiverid).get();
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
                        return new ResponseEntity<>(receiver.toFriendDTO(), HttpStatus.OK);
                    }
                    return new ResponseEntity<>(new ExceptionMessage("Вы уже отправили запрос в друзья этому пользователю"), HttpStatus.CONFLICT);
                }
                return new ResponseEntity<>(new ExceptionMessage("Вы уже добавили этого пользователя в друзья"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(new ExceptionMessage("Иди в дурку проверся"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new ExceptionMessage("Такого id не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> acceptFriendRequest(Integer receiverid, Integer senderid) {
        if (userRepository.existsById(senderid) && userRepository.existsById(receiverid)) {
            var sender = userRepository.findById(senderid).get();
            var receiver = userRepository.findById(receiverid).get();
            if (receiver.getPending().contains(senderid) && sender.getSent().contains(receiverid)) {
                receiver.getPending().remove(senderid);
                sender.getSent().remove(receiverid);

                receiver.getFriends().add(senderid);
                sender.getFriends().add(receiverid);
                userRepository.save(receiver);
                userRepository.save(sender);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ExceptionMessage("Пользователь не отправлял запрос в друзья"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь с таким ID не существует"), HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> getFriends(Integer id) {
        if (userRepository.existsById(id)) {
            var friendList = userRepository.findById(id).get().getFriends();
            List<FriendDTO> friendDTOList = new ArrayList<>();

            for (var item : friendList
            ) {
                friendDTOList.add(userRepository.findById(item).get().toFriendDTO());
            }

            return new ResponseEntity<>(friendDTOList, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не найден"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getPending(Integer id) {
        if (userRepository.existsById(id)) {
            List<FriendDTO> pendingList = new ArrayList<>();
            for (Integer item : userRepository.findById(id).get().getPending()
            ) {
                pendingList.add(userRepository.findById(item).get().toFriendDTO());
            }
            return new ResponseEntity<>(pendingList, HttpStatus.OK);

        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getSent(Integer id) {
        if (userRepository.existsById(id)) {
            List<FriendDTO> pendingList = new ArrayList<>();
            for (Integer item : userRepository.findById(id).get().getSent()
            ) {
                pendingList.add(userRepository.findById(item).get().toFriendDTO());
            }
            return new ResponseEntity<>(pendingList, HttpStatus.OK);

        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> updatePicture(Integer id, RequestAuth auth, String URL) {
        if(userRepository.existsById(id)){
            UserDAO userDAO = userRepository.findById(id).get();
            if(userDAO.getEmail().equals(auth.getEmail()) && userDAO.getPassword().equals(auth.getPassword())){
                userDAO.setImage(URL);
                userRepository.save(userDAO);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(new ExceptionMessage("Неверная почта или пароль"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не найден"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> updateNickname(Integer id, RequestAuth auth, String nickname) {
        if(userRepository.existsById(id)){
            UserDAO userDAO = userRepository.findById(id).get();
            if(userDAO.getEmail().equals(auth.getEmail()) && userDAO.getPassword().equals(auth.getPassword())){
                if(userRepository.findByNickname(nickname).isEmpty()) {
                    userDAO.setNickname(nickname);
                    userRepository.save(userDAO);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(new ExceptionMessage("Такое имя уже занято"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(new ExceptionMessage("Неверная почта или пароль"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователь не найден"), HttpStatus.NOT_FOUND);
    }
}
