package com.alencarfelipe.mytwitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
        String cpf1 = "016.891.690-87";
        String cpf2 = "845.346.369-70";

        PessoaFisica pessoaFisica = new PessoaFisica(username);
        pessoaFisica.setCpf(cpf1);

        repositorioUsuario.cadastrar(pessoaFisica);

        PessoaFisica perfil = (PessoaFisica) repositorioUsuario.buscar(username);

        assertNotNull(perfil);
        assertEquals(username, perfil.getUsername());
        assertEquals(cpf1, perfil.getCpf());

        perfil.setCpf(cpf2);

        repositorioUsuario.atualizar(perfil);

        assertEquals(cpf2, perfil.getCpf());
    }
}
