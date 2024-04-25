package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dto.rq.RequestEditMessageDTO;
import com.sosoburger.toaster.dto.rq.RequestMessageDTO;
import com.sosoburger.toaster.dto.rs.ResponseFileDTO;
import com.sosoburger.toaster.dto.rs.ResponseMessageDTO;
import com.sosoburger.toaster.mapper.Mapper;
import com.sosoburger.toaster.rest.api.MessageApi;
import com.sosoburger.toaster.service.MessageService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class MessageController implements MessageApi {
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final UserServiceImpl userService;

    public MessageController(MessageService messageService, UserServiceImpl userService){
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ResponseMessageDTO> create(RequestMessageDTO message) {
        return new ResponseEntity<>(messageService.create(message, getUserDetails().getId()).toDTO(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ResponseMessageDTO>> getAll() {
        List<MessageDAO> messageDAOs =  messageService.getAll();
        List<ResponseMessageDTO> DTOs = new ArrayList<>();
        for (MessageDAO message : messageDAOs
        ) {
            DTOs.add(message.toDTO());
        }
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseMessageDTO>> getDialog(Integer companion, Integer page) {
        List<MessageDAO> messageDAOs = messageService.getDialog(getUserDetails().getId(), companion, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));

        List<ResponseMessageDTO> messageDTOs = new ArrayList<>();
        for (MessageDAO message : messageDAOs
        ) {
            messageDTOs.add(message.toDTO());
        }
        messageDTOs.sort(Comparator.comparing(ResponseMessageDTO::getDate).reversed());
        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessageDTO> editMessage(Integer id, RequestEditMessageDTO message) {
        var response = messageService.edit(id, message);
        return new ResponseEntity<>(response.toDTO(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMessage(Integer id) {
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseFileDTO>> getMessageImages(Integer id) {
        var message = messageService.get(id);
        var response = Mapper.filesToFileDTOList(message.getAttachments().stream().toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public UserDAO getUserDetails(){
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
