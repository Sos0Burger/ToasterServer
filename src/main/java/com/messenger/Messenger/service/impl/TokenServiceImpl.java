package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.TokenDAO;
import com.messenger.Messenger.exception.ExpiredException;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.repository.TokenRepository;
import com.messenger.Messenger.service.TokenService;
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
