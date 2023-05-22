package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.MessageRepository;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> create(RequestMessageDTO message) {
        if(userRepository.existsById(message.getSender())&&userRepository.existsById(message.getReceiver())){
            messageRepository.save(message.toDAO());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователя с таким ID не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getAll() {
        var messages = messageRepository.findAll();
        List<ResponseMessageDTO> DTOs = new ArrayList<>();
        for (MessageDAO message: messages
             ) {
            DTOs.add(message.toDTO());
        }
        messages.forEach(MessageDAO::toDTO);
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
}
