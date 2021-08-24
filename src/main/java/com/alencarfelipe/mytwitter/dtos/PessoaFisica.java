package com.alencarfelipe.mytwitter.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class PessoaFisica extends Perfil {
    @Setter
    @Getter
    private long cpf;

}
