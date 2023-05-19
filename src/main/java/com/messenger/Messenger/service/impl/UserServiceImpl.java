package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.UserDAO;
import com.messenger.Messenger.dto.RequestUserDTO;
import com.messenger.Messenger.dto.ResponseUserDTO;
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
            return new ResponseEntity<>("Пользователь с такой почтой уже существует", HttpStatus.CONFLICT);
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
}
