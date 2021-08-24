package com.alencarfelipe.mytwitter.api;

import java.util.List;

import com.alencarfelipe.mytwitter.dtos.Perfil;
import com.alencarfelipe.mytwitter.dtos.Tweet;

/**
 * API contract
 * 
 * @author alencar-felipe
 */

public interface ITwitter {
    /**
     * Responsible for registering the profiles passed as parameter
     * 
     * @param usuario
     */
    public void criarPerfil(Perfil usuario) throws PEException;

    /**
     * Deactivates profile passed as paramenter
     * 
     * @param usuario
     */
    public void cancelarPerfil(String usuario) throws PIException;

    /**
     * Post tweets
     * 
     * if a message is too short (<1) or
     * too long (>140) a MFPException will be thrown.
     * 
     * @param usuario
     * @param mensagem
     */
    public void tweetar(String usuario, String mensagem) throws PIException, MFPException;
    
    /**
     * Retrieves all tweets from the profile timeline
     * 
     * @param usuario
     * @return
     */
    public List<Tweet> timeline(String usuario) throws PIException, PDException;
    
    /**
     * Retrieves all tweets published by the profile passed as parameter
     * 
     * @param usuario
     * @return
     */
    public List<Tweet> tweets(String usuario) throws PIException, PDException;

    /**
     * Retrieves all tweets from the timeline of the profile passed as parameter
     * 
     * @param seguidor
     * @param seguido
     */
    public void seguir(String seguidor, String seguido);

    /**
     * Retrieves the number of followers of the profile passed as parameter
     * 
     * @param usuario
     * @return
     */
    public int numeroSeguidores(String usuario);

    /**
     * Retrieves the followers of the profile passed as parameter
     * 
     * @param usuario
     * @return
     */
    public List<Tweet> seguidores(String usuario);

    /**
     * Retrieves the profiles followed by the profile passed as parameter
     * 
     * @param usuario
     * @return
     */
    public List<Perfil> seguidos(String usuario);
}
