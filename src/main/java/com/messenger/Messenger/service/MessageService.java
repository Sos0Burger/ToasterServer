package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageDAO create(RequestMessageDTO message);
    List<MessageDAO> getAll();
    List<MessageDAO> getDialog(Integer userid, Integer companionid, Pageable pageable);
}
