package com.alencarfelipe.mytwitter.pojos;

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
    }
}
