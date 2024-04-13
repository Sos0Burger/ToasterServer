package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.TokenDAO;
import com.sosoburger.toaster.exception.ExpiredException;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.TokenRepository;
import com.sosoburger.toaster.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public TokenDAO create(String email) {
        return tokenRepository.save(new TokenDAO(email));
    }

    @Override
    public TokenDAO findByEmailAndToken(String email, String token) {
        var verificationToken = tokenRepository.findFirstByEmailAndToken(email, token);
        if(verificationToken==null){
            throw new NotFoundException("Не найдено");
        }
        if(verificationToken.getExpirationDate().before(new Date())){
            throw new ExpiredException("Код устарел");
        }
        return verificationToken;
    }
}
