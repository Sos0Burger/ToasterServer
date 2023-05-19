package com.messenger.Messenger.service;

import com.messenger.Messenger.dto.RequestUserDTO;
import com.messenger.Messenger.dto.ResponseUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService{
    ResponseEntity<?> create(RequestUserDTO requestUserDTO);
    ResponseEntity<List<ResponseUserDTO>> getAll();

    ResponseEntity<?> delete(Integer id);
}
