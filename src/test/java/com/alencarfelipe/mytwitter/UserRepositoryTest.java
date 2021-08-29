package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.UNCException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    IRepositorioUsuario repositorioUsuario;

    private static String username = "teste";
    private static long cpf1 = 1689169087L;
    private static long cpf2 = 84534636970L;
    private static PessoaFisica pessoaFisica;

    @Test
    @Order(1)
    void cadastrarTest() {
        pessoaFisica = new PessoaFisica(username);
        pessoaFisica.setCpf(cpf1);

        try {
            repositorioUsuario.delete(pessoaFisica);
        } catch(UNCException ex) {

        }

        repositorioUsuario.cadastrar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertNotNull(perfil);
        assertEquals(username, perfil.getUsuario());
        assertEquals(cpf1, perfil.getCpf());
    }

    @Test
    @Order(2)
    void atualizarTest() {
        pessoaFisica.setCpf(cpf2);

        repositorioUsuario.atualizar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertEquals(cpf2, perfil.getCpf());
    }

    @Test
    @Order(3)
    void deleteTest() {
        repositorioUsuario.delete(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertNull(perfil);
    }

    //TODO: add timeline tests
}
