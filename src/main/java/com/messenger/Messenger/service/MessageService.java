package com.messenger.Messenger.service;

import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
    ResponseEntity<?> create(RequestMessageDTO message);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getDialog(Integer userid, Integer companionid, Pageable pageable);
}
