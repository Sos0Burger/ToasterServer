package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import com.messenger.Messenger.rest.api.MessageApi;
import com.messenger.Messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController implements MessageApi {
    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<?> create(RequestMessageDTO message) {
        return messageService.create(message);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return messageService.getAll();
    }

    @Override
    public ResponseEntity<?> getDialog(Integer userid, Integer companionid, Integer page) {
        return messageService.getDialog(userid, companionid, (Pageable) PageRequest.of(page, 30, Sort.unsorted()));
    }
}
