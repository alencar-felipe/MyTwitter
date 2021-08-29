package com.alencarfelipe.mytwitter;

import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.pojos.PessoaJuridica;

@TestMethodOrder(OrderAnnotation.class)
public class TwitterTest {
    public void tweetTest() {
        PessoaFisica pf = new PessoaFisica("fisica");
        pf.setCpf(1689169087L);

        PessoaJuridica pj = new PessoaJuridica("juridica");
        pj.setCnpj(49476643000103L);
        

    }
}
