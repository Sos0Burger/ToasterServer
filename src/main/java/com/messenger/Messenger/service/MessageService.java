package com.messenger.Messenger.service;

import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> create(RequestMessageDTO message);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getDialog(Integer userid, Integer companionid);
}
