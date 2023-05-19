package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
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
        if(!userRepository.findByEmail(requestUserDTO.getEmail()).isEmpty()){
            return new ResponseEntity<>(new ExceptionMessage("Пользователь с такой почтой уже существует"), HttpStatus.CONFLICT);
        }
        if(!userRepository.findByNickname(requestUserDTO.getNickname()).isEmpty()){
            return new ResponseEntity<>(new ExceptionMessage("Пользователь с таким именем уже существует"), HttpStatus.CONFLICT);
        }
        userRepository.save(requestUserDTO.toDAO());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ResponseUserDTO>> getAll() {
        List<UserDAO> DAOlist = userRepository.findAll();
        List<ResponseUserDTO> DTOlist = new ArrayList<>();
        for (UserDAO user:DAOlist
             ) {
            DTOlist.add(user.toDTO());
        }
        return new ResponseEntity<>(DTOlist, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.delete(userRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователя с таким id не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> auth(RequestAuth auth) {
        var user = userRepository.findByEmail(auth.getEmail());
        if(!user.isEmpty()){
            if(user.get(0).getPassword().equals(auth.getPassword())){
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> findByIds(List<Integer> ids) {
        return new ResponseEntity<>(userRepository.findAllById(ids), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> sendFriendRequest(Integer senderid, Integer receiverid) {
        if(userRepository.existsById(senderid)&& userRepository.existsById(receiverid)){
            var sender = userRepository.findById(senderid).get();
            var receiver = userRepository.findById(receiverid).get();
            sender.getSent().add(receiverid);
            receiver.getPending().add(senderid);
            userRepository.save(sender);
            userRepository.save(receiver);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Такого id не существует"), HttpStatus.OK);
    }
}
