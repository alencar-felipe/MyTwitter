package com.alencarfelipe.mytwitter.repositorio;

import java.util.List;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.Tweet;
public interface ITweetRepository {
    /**
     * Adds new tweet to repository
     * 
     * @param tweet
     */
    public void addTweet(Tweet tweet) throws INException;

    /**
     * Get list of tweets made by a user
     * 
     * @param perfil
     */
    public List<Tweet> getPerfilTweets(Perfil perfil) throws UNCException;
}
