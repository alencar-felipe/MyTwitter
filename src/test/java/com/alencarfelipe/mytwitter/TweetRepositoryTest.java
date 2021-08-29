package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alencarfelipe.mytwitter.mongodb.TweetRepository;
import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IMException;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.services.ITweetServices;
import org.junit.jupiter.api.Test;

public class TweetRepositoryTest {
    String uri = "mongodb://127.0.0.1:27017";
    String dbName = "MyTwitter";

    @Test
    void tweetRepositoryTest() {
        String username = "test";
        String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + 
            "Quisque massa est, iaculis ut commodo vel, ultrices ac est leo.";
        String bigMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + 
            "Curabitur commodo facilisis augue, et fringilla nibh pharetra eget. " + 
            "Ut eget nunc quis urna tempus lacinia a sed risus. Sed eget auctor purus. " + 
            "Donec sagittis elementum luctus volutpat.";
        String emptyMessage = "";

        Tweet tweet = new Tweet();
        tweet.setUsuario(username);
        tweet.setMensagem(message);

        Tweet invalidUserTweet = new Tweet();
        invalidUserTweet.setUsuario("no_one");
        invalidUserTweet.setMensagem(message);

        Tweet bigTweet = new Tweet();
        bigTweet.setUsuario(username);
        bigTweet.setMensagem(bigMessage);

        Tweet emptyTweet = new Tweet();
        emptyTweet.setUsuario(username);
        emptyTweet.setMensagem(emptyMessage);

        ITweetServices tweetServices = mock(ITweetServices.class);
        when(tweetServices.isTweetValid(tweet)).thenReturn(true);
        when(tweetServices.isTweetValid(bigTweet)).thenReturn(false);
        when(tweetServices.isTweetValid(emptyTweet)).thenReturn(false);

        Perfil perfil = new Perfil(username);
        IRepositorioUsuario repositorioUsuario = mock(IRepositorioUsuario.class);
        when(repositorioUsuario.buscar(username)).thenReturn(perfil);
        
        TweetRepository tweetRepository = new TweetRepository();
        tweetRepository.setUri(uri);
        tweetRepository.setDbName(dbName);
        tweetRepository.setTweetServices(tweetServices);
        tweetRepository.setRepositorioUsuario(repositorioUsuario);
        
        tweetRepository.addTweet(tweet);
        
        try {
            tweetRepository.addTweet(invalidUserTweet);
            tweetRepository.addTweet(bigTweet);
            tweetRepository.addTweet(emptyTweet);
            fail();
        } catch(IMException ex) {

        }
    }
}
