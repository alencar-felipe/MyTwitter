package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.alencarfelipe.mytwitter.mongodb.TweetRepository;
import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IMException;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.services.ITweetServices;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class TweetRepositoryTest {
    private static String uri = "mongodb://127.0.0.1:27017";
    private static String dbName = "MyTwitter";

    private static String username = "test";
    private static String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + 
        "Quisque massa est, iaculis ut commodo vel, ultrices ac est leo.";
    private static String bigMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + 
        "Curabitur commodo facilisis augue, et fringilla nibh pharetra eget. " + 
        "Ut eget nunc quis urna tempus lacinia a sed risus. Sed eget auctor purus. " + 
        "Donec sagittis elementum luctus volutpat.";
    private static String emptyMessage = "";

    private static Tweet tweet, invalidUserTweet, bigTweet, emptyTweet;

    private static Perfil perfil;

    private static TweetRepository tweetRepository;

    @BeforeAll
    private static void setup() {
        tweet = new Tweet();
        tweet.setUsuario(username);
        tweet.setMensagem(message);

        invalidUserTweet = new Tweet();
        invalidUserTweet.setUsuario("no_one");
        invalidUserTweet.setMensagem(message);

        bigTweet = new Tweet();
        bigTweet.setUsuario(username);
        bigTweet.setMensagem(bigMessage);

        emptyTweet = new Tweet();
        emptyTweet.setUsuario(username);
        emptyTweet.setMensagem(emptyMessage);

        ITweetServices tweetServices = mock(ITweetServices.class);
        when(tweetServices.isTweetValid(tweet)).thenReturn(true);
        when(tweetServices.isTweetValid(bigTweet)).thenReturn(false);
        when(tweetServices.isTweetValid(emptyTweet)).thenReturn(false);

        perfil = new Perfil(username);
        IRepositorioUsuario repositorioUsuario = mock(IRepositorioUsuario.class);
        when(repositorioUsuario.buscar(username)).thenReturn(perfil);
        when(repositorioUsuario.exists(username)).thenReturn(true);
        
        tweetRepository = new TweetRepository();
        tweetRepository.setUri(uri);
        tweetRepository.setDbName(dbName);
        tweetRepository.setTweetServices(tweetServices);
        tweetRepository.setRepositorioUsuario(repositorioUsuario);
    }

    @Test
    @Order(1)
    void addTweetTest() {
        tweetRepository.addTweet(tweet);
        
        try {
            tweetRepository.addTweet(invalidUserTweet);
            tweetRepository.addTweet(bigTweet);
            tweetRepository.addTweet(emptyTweet);
            fail();
        } catch(IMException ex) {

        }
    }

    @Test
    @Order(2)
    void getPerfilTweetsTest() {
        List<Tweet> tweets = tweetRepository.getPerfilTweets(perfil);

        assertTrue(tweets.contains(tweet));
        assertFalse(tweets.contains(invalidUserTweet));
        assertFalse(tweets.contains(bigTweet));
        assertFalse(tweets.contains(emptyTweet));
    }
}
