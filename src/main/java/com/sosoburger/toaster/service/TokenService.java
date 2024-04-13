package com.sosoburger.toaster.service;

import com.sosoburger.toaster.dao.TokenDAO;

public interface TokenService {
    TokenDAO create(String email);
    TokenDAO findByEmailAndToken(String email, String token);
}
