package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.TokenDAO;

public interface TokenService {
    TokenDAO create(String email);
    TokenDAO findByEmailAndToken(String email, String token);
}
