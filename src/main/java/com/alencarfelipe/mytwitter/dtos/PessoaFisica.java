package com.alencarfelipe.mytwitter.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Profile of a person
 * 
 * @author alencar-felipe
 */
public class PessoaFisica extends Perfil {
    @Setter
    @Getter
    private long cpf;

    public PessoaFisica(String usuario) {
        super(usuario);
        //TODO Auto-generated constructor stub
    }
}
