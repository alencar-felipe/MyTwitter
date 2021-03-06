package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.alencarfelipe.mytwitter.api.MFPException;
import com.alencarfelipe.mytwitter.api.PDException;
import com.alencarfelipe.mytwitter.api.PEException;
import com.alencarfelipe.mytwitter.api.PIException;
import com.alencarfelipe.mytwitter.api.SIException;
import com.alencarfelipe.mytwitter.api.Twitter;
import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.pojos.PessoaJuridica;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IMException;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;
import com.alencarfelipe.mytwitter.repositorio.UJCException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class TwitterTest {
    private static IRepositorioUsuario repositorioUsuario;
    private static ITweetRepository tweetRepository;
    private static Twitter twitter;

    private static PessoaFisica pf;
    private static PessoaJuridica pj;
    private static PessoaFisica alreadyRegistered;
    private static PessoaFisica notRegistered;
    private static PessoaFisica inactive;

    private static Tweet tweetPf, tweetPj, tweetNotRegistered, tweetInvalid; 

    @BeforeAll
    static void setup() {
        pf = new PessoaFisica("fisica");
        pf.setCpf(1689169087L);

        pj = new PessoaJuridica("juridica");
        pj.setCnpj(49476643000103L);

        alreadyRegistered = new PessoaFisica("registered");
        alreadyRegistered.setCpf(1689169087L);

        notRegistered = new PessoaFisica("registered");
        notRegistered.setCpf(1689169087L);

        inactive = new PessoaFisica("inactive");
        inactive.setCpf(1689169087L);

        tweetPf = new Tweet();
        tweetPf.setUsuario(pf.getUsuario());
        tweetPf.setMensagem("sou pessoa fisica.");

        tweetPj = new Tweet();
        tweetPj.setUsuario(pj.getUsuario());
        tweetPj.setMensagem("sou pessoa juridica.");

        tweetNotRegistered = new Tweet();
        tweetNotRegistered.setUsuario(notRegistered.getUsuario());
        tweetNotRegistered.setMensagem("valid message");

        tweetInvalid = new Tweet();
        tweetInvalid.setUsuario(pf.getUsuario());
        tweetInvalid.setMensagem("");

        repositorioUsuario = mock(IRepositorioUsuario.class);
        
        doThrow(UJCException.class).when(repositorioUsuario).cadastrar(alreadyRegistered);
        when(repositorioUsuario.buscar(pf.getUsuario())).thenReturn(pf);
        when(repositorioUsuario.buscar(pj.getUsuario())).thenReturn(pj);
        when(repositorioUsuario.buscar(inactive.getUsuario())).thenReturn(inactive);

        tweetRepository = mock(ITweetRepository.class);

        when(tweetRepository.getPerfilTweets(pf)).thenReturn(Arrays.asList(tweetPf));
        when(tweetRepository.getPerfilTweets(pj)).thenReturn(Arrays.asList(tweetPj));
        doThrow(IMException.class).when(tweetRepository).addTweet(tweetInvalid);

        twitter = new Twitter();
        
        twitter.setRepositorioUsuario(repositorioUsuario);
        twitter.setTweetRepository(tweetRepository);
    }

    @Test
    @Order(1)
    void criarPerfilTest() {
        try {
            twitter.criarPerfil(pf);
            twitter.criarPerfil(pj);
        } catch(PEException ex) {
            fail();
        } 
        
        try {
            twitter.criarPerfil(alreadyRegistered);
            fail();
        } catch(PEException ex) {

        }
    }

    @Test
    @Order(2)
    void tweetarTest() {
        try {
            twitter.tweetar(pf.getUsuario(), tweetPf.getMensagem());
            twitter.tweetar(pj.getUsuario(), tweetPj.getMensagem());
        } catch(PIException | MFPException  ex) {
            fail();
        }

        try {
            twitter.tweetar(notRegistered.getUsuario(), tweetNotRegistered.getMensagem());
            fail();
        } catch(MFPException ex) {
            fail();
        } catch(PIException ex) {

        }

        try { 
            twitter.tweetar(pf.getUsuario(), tweetInvalid.getMensagem());
            fail();
        } catch(PIException ex) {
            fail();
        } catch(MFPException ex) {

        }
    }

    @Test
    @Order(3)
    void tweetsTest() {
        try {
            List<Tweet> pfTweets = twitter.tweets(pf.getUsuario());
            List<Tweet> pjTweets = twitter.tweets(pj.getUsuario());

            assertNotNull(pfTweets);
            assertNotNull(pjTweets);

        } catch(PIException ex) {
            fail();
        }

        try {
            twitter.tweets(notRegistered.getUsuario());
            fail();
        } catch(PIException ex) {

        }

        try {
            twitter.tweets(inactive.getUsuario());
            fail();
        } catch(PDException ex) {

        }
    }

    @Test
    @Order(4)
    void seguirTest() {
        try {
            twitter.seguir(pf.getUsuario(), pj.getUsuario());
            twitter.seguir(pj.getUsuario(), pf.getUsuario());
        } catch(PIException | PDException | SIException ex) {
            fail();
        }

        try {
            twitter.seguir(pf.getUsuario(), notRegistered.getUsuario());
            fail();
        } catch(PDException | SIException ex) {
            fail();
        } catch(PIException  ex) {

        }

        try {
            twitter.seguir(pf.getUsuario(), inactive.getUsuario());
            fail();
        } catch(PIException | SIException ex) {
            fail();
        } catch(PDException ex) {

        }
        
        try {
            twitter.seguir(notRegistered.getUsuario(), pf.getUsuario());
            fail();
        } catch(PDException | SIException ex) {
            fail();
        } catch(PIException ex) {

        }

        try {
            twitter.seguir(inactive.getUsuario(), pf.getUsuario());
            fail();
        } catch(PIException | SIException ex) {
            fail();
        } catch(PDException ex) {

        }
    }

    @Test
    @Order(5)
    void numeroSeguidoresTest() {
        try {
            assertEquals(1, twitter.numeroSeguidores(pf.getUsuario()));
            assertEquals(1, twitter.numeroSeguidores(pj.getUsuario()));
        } catch(PIException | PDException ex) {
            fail();     
        }

        try {
            twitter.numeroSeguidores(notRegistered.getUsuario());
            fail();
        } catch(PIException ex) {

        } catch(PDException ex) {
            fail();
        }

        try {
            twitter.numeroSeguidores(inactive.getUsuario());
            fail();
        } catch(PIException ex) {
            fail();  
        } catch(PDException ex) {

        }
    }

    @Test
    @Order(6)
    void seguidoresTest() {
        try {
            twitter.seguidores(pf.getUsuario()).contains(pj);
            twitter.seguidores(pj.getUsuario()).contains(pf);
        } catch(PIException | PDException ex) {
            fail();
        }

        try {
            twitter.seguidores(notRegistered.getUsuario());
            fail();
        } catch(PIException ex) {

        } catch(PDException ex) {
            fail();
        }

        try {
            twitter.seguidores(inactive.getUsuario());
            fail();
        } catch(PIException ex) {
            fail();  
        } catch(PDException ex) {

        }
    }

    @Test
    @Order(7)
    void seguidosTest() {
        try {
            twitter.seguidos(pf.getUsuario()).contains(pj);
            twitter.seguidos(pj.getUsuario()).contains(pf);
        } catch(PIException | PDException ex) {
            fail();
        }

        try {
            twitter.seguidos(notRegistered.getUsuario());
            fail();
        } catch(PIException ex) {

        } catch(PDException ex) {
            fail();
        }

        try {
            twitter.seguidos(inactive.getUsuario());
            fail();
        } catch(PIException ex) {
            fail();  
        } catch(PDException ex) {

        }
    }

    @Test
    @Order(8)
    void cancelarPerfilTest() {
        try {
            twitter.cancelarPerfil(pf.getUsuario());
        } catch(PIException | PDException ex) {
            fail();
        }

        try {
            twitter.cancelarPerfil(notRegistered.getUsuario());
            fail();
        } catch(PIException ex) {

        } catch(PDException ex) {
            fail();
        }

        try {
            twitter.cancelarPerfil(inactive.getUsuario());
            fail();
        } catch(PIException ex) {
            fail();
        } catch(PDException ex) {

        }
    }
}
