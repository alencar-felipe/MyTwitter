package com.alencarfelipe.mytwitter.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Profile of a company
 * 
 * @author alencar-felipe
 */

public class PessoaJuridica extends Perfil {
    @Setter
    @Getter
    private long cnpj;

    public PessoaJuridica(String usuario) {
        super(usuario);
        //TODO Auto-generated constructor stub
    }

}
