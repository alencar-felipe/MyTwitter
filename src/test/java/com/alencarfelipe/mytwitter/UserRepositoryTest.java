package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.alencarfelipe.mytwitter.mongodb.UserRepository;
import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;
import com.alencarfelipe.mytwitter.repositorio.UNCException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest {
    private static String uri = "mongodb://127.0.0.1:27017";
    private static String dbName = "MyTwitter";

    private static ITweetRepository tweetRepository;

    private static UserRepository userRepository;

    private static String username = "teste";
    private static long cpf1 = 1689169087L;
    private static long cpf2 = 84534636970L;
    private static PessoaFisica pessoaFisica;

    @BeforeAll
    private static void setup() {
        pessoaFisica = new PessoaFisica(username);
        pessoaFisica.setCpf(cpf1);
        pessoaFisica.setSeguidores(new ArrayList<Perfil>());
        pessoaFisica.setSeguidos(new ArrayList<Perfil>());

        tweetRepository = mock(ITweetRepository.class);
        when(tweetRepository.getPerfilTweets(pessoaFisica)).thenReturn(new ArrayList<Tweet>());

        userRepository = new UserRepository();
        userRepository.setUri(uri);
        userRepository.setDbName(dbName);
        userRepository.setTweetRepository(tweetRepository);


    }

    @Test
    @Order(1)
    void cadastrarTest() {
        

        try {
            userRepository.delete(pessoaFisica);
        } catch(UNCException ex) {

        }

        userRepository.cadastrar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) userRepository.buscar(username);

        assertNotNull(perfil);
        assertEquals(username, perfil.getUsuario());
        assertEquals(cpf1, perfil.getCpf());
    }

    @Test
    @Order(2)
    void atualizarTest() {
        pessoaFisica.setCpf(cpf2);

        userRepository.atualizar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) userRepository.buscar(username);

        assertEquals(cpf2, perfil.getCpf());
    }

    @Test
    @Order(3)
    void deleteTest() {
        userRepository.delete(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) userRepository.buscar(username);

        assertNull(perfil);
    }

    //TODO: add timeline tests
}
