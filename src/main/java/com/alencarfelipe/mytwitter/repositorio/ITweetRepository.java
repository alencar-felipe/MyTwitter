package com.alencarfelipe.mytwitter.repositorio;

import com.alencarfelipe.mytwitter.dtos.Perfil;

public interface ITweetRepository {
    /**
     * Adds new tweet to repository
     * 
     * @param tweet
     */
    public void addTweet(Tweet tweet) throws UNCException, INException;

    /**
     * Get list of tweets made by a user
     * 
     * @param perfil
     */
    public List<Tweet> getPerfilTweets(Perfil perfil) throws UNCException;
}
