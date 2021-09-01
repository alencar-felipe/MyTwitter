package com.alencarfelipe.mytwitter.services;

import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TweetServices implements ITweetServices {
    @Autowired
    private IRepositorioUsuario repositorioUsuario;

    @Override
    public boolean isUsuarioValid(Tweet tweet) {
        String usuario = tweet.getUsuario();

        if(usuario == null) {
            return false;
        }

        return (repositorioUsuario.buscar(usuario) != null) ;
    }

    @Override
    public boolean isMessagemValid(Tweet tweet) {
        String message = tweet.getMensagem();

        if(message == null) {
            return false;
        }

        return (message.length() > 0 && message.length() <= 140);
    }

    @Override
    public boolean isTweetValid(Tweet tweet) {
        return isUsuarioValid(tweet) && isMessagemValid(tweet);
    }
    
}
