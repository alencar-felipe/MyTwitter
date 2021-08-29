package com.alencarfelipe.mytwitter.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.PessoaFisica;
import com.alencarfelipe.mytwitter.pojos.PessoaJuridica;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;

import lombok.Data;

@Data
public class PerfilDTO {
    private String usuario;
    private List<String> seguidos;
    private List<String> seguidores;
    private boolean ativo; 
    private long cpf;
    private long cnpj;
    private String type;  
    
    public PerfilDTO(Perfil perfil) {
        usuario = perfil.getUsuario();
        seguidos = perfil.getSeguidos();
        seguidores = perfil.getSeguidores();
        ativo = perfil.isAtivo();

        if(perfil instanceof PessoaFisica) {
            type = "PessoaFisica";

            cpf = ((PessoaFisica) perfil).getCpf();
            
        } else if(perfil instanceof PessoaJuridica) {
            type = "PessoaJuridica";

            cnpj = ((PessoaJuridica) perfil).getCnpj();

        } else {
            throw new UnsupportedOperationException();
        }

    }

    public Perfil toPerfil(ITweetRepository tweetRepository) {
        Perfil perfil;

        switch(type) {
            case "PessoaFisica":
                PessoaFisica pessoaFisica = new PessoaFisica(usuario);
                pessoaFisica.setCpf(cpf);
                perfil = (Perfil) pessoaFisica;
                break;

            case "PessoaJuridica":
                PessoaJuridica pessoaJuridica = new PessoaJuridica(usuario);
                pessoaJuridica.setCnpj(cnpj);
                perfil = (Perfil) pessoaJuridica;
                break;

            default:
                throw new UnsupportedOperationException();
                break;
        }
        
        ArrayList<Perfil> seguidos = new ArrayList<>();

        for(String usuario : this.seguidos) {
            seguidos.add(new Perfil(usuario));
        }

        perfil.setSeguidos(seguidos);

        ArrayList<Perfil> seguidores = new ArrayList<>();

        for(String usuario : this.seguidores) {
            seguidores.add(new Perfil(usuario));
        }

        ArrayList<Tweet> timeline = new ArrayList<>();

        for(Perfil p : seguidos) {
            List<Tweet> tweets = tweetRepository.getPerfilTweets(p);
            timeline.addAll(tweets);
        }

        perfil.setTimeline(timeline);
        
        perfil.setAtivo(ativo);   
    }
}
