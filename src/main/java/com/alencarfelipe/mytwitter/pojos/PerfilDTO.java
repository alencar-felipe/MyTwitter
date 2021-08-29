package com.alencarfelipe.mytwitter.pojos;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

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
    
    public PerfilDTO() {

    }

    public PerfilDTO(Perfil perfil) {
        usuario = perfil.getUsuario();

        if(perfil.getSeguidos() != null) {
            seguidos = new ArrayList<String>();

            for(Perfil p : perfil.getSeguidos()) {
                seguidos.add(p.getUsuario());
            }
        }

        if(perfil.getSeguidores() != null) {
            seguidores = new ArrayList<String>();

            for(Perfil p : perfil.getSeguidores()) {
                seguidores.add(p.getUsuario());
            }
        }
        

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

    @Transient
    public Perfil toPerfil(ITweetRepository tweetRepository) {
        Perfil perfil;

        if(type == null) {
            throw new UnsupportedOperationException();
        }

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
        }
        
        if(this.seguidos != null) {
            ArrayList<Perfil> seguidos = new ArrayList<>();

            for(String usuario : this.seguidos) {
                seguidos.add(new Perfil(usuario));
            }

            perfil.setSeguidos(seguidos);

            ArrayList<Tweet> timeline = new ArrayList<>();

            for(Perfil p : seguidos) {
                List<Tweet> tweets = tweetRepository.getPerfilTweets(p);
                timeline.addAll(tweets);
            }

            perfil.setTimeline(timeline);
        }

        if(this.seguidores != null) {
            ArrayList<Perfil> seguidores = new ArrayList<>();

            for(String usuario : this.seguidores) {
                seguidores.add(new Perfil(usuario));
            }
        }
        
        perfil.setAtivo(ativo);

        return perfil;
    }
}
