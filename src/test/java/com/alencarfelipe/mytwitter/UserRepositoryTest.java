package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    IRepositorioUsuario repositorioUsuario;

    @Test
    void userRepositoryTest() {
        String username = "teste";
        long cpf1 = 1689169087L;
        long cpf2 = 84534636970L;

        PessoaFisica pessoaFisica = new PessoaFisica(username);
        pessoaFisica.setCpf(cpf1);

        repositorioUsuario.cadastrar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertNotNull(perfil);
        assertEquals(username, perfil.getUsuario());
        assertEquals(cpf1, perfil.getCpf());

        perfil.setCpf(cpf2);

        repositorioUsuario.atualizar(perfil);

        assertEquals(cpf2, perfil.getCpf());

        repositorioUsuario.delete(perfil);

        perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertNull(perfil);

        //TODO: add timeline tests
    }
}
