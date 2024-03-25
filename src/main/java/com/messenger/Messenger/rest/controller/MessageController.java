package com.messenger.Messenger.rest.controller;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import com.messenger.Messenger.rest.api.MessageApi;
import com.messenger.Messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class MessageController implements MessageApi {
    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<ResponseMessageDTO> create(RequestMessageDTO message) {
        return new ResponseEntity<>(messageService.create(message).toDTO(), HttpStatus.CREATED);
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
    public ResponseEntity<List<ResponseMessageDTO>> getDialog(Integer userid, Integer companionid, Integer page) {
        List<MessageDAO> messageDAOs = messageService.getDialog(userid, companionid, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "date")));

        List<ResponseMessageDTO> messageDTOs = new ArrayList<>();
        for (MessageDAO message : messageDAOs
        ) {
            messageDTOs.add(message.toDTO());
        }
        messageDTOs.sort(Comparator.comparing(ResponseMessageDTO::getDate).reversed());
        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }
}
