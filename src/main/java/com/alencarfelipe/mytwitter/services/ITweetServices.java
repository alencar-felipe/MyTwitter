package com.alencarfelipe.mytwitter.services;

import com.alencarfelipe.mytwitter.pojos.Tweet;

import org.springframework.stereotype.Component;

@Component
public interface ITweetServices {
    public boolean isUsuarioValid(Tweet tweet);
    public boolean isMessagemValid(Tweet tweet);
    public boolean isTweetValid(Tweet tweet);
}
