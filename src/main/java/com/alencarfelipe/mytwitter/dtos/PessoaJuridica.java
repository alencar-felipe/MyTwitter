package com.alencarfelipe.mytwitter.dtos;

import lombok.Getter;
import lombok.Setter;

public class PessoaJuridica extends Perfil {
    @Setter
    @Getter
    private long cnpj;
}
