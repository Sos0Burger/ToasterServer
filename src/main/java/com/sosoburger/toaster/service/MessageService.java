package com.sosoburger.toaster.service;

import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dto.rq.RequestMessageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageDAO create(RequestMessageDTO message, Integer sender);
    List<MessageDAO> getAll();
    List<MessageDAO> getDialog(Integer userid, Integer companion, Pageable pageable);
}
