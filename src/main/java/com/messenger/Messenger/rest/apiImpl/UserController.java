package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dto.RequestUserDTO;
import com.messenger.Messenger.rest.api.UserApi;
import com.messenger.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> create(RequestUserDTO requestUserDTO) {
        return userService.create(requestUserDTO);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return userService.getAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return userService.delete(id);
    }
}
