package com.alencarfelipe.mytwitter.pojos;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;

import lombok.Data;

@Data
public class PerfilDTO {
    private String username;
    private List<String> followed;
    private List<String> followers;
    private boolean active; 
    private long cpf;
    private long cnpj;
    private String type;  
    
    public PerfilDTO() {

    }

    public PerfilDTO(Perfil perfil) {
        username = perfil.getUsuario();

        if(perfil.getSeguidos() != null) {
            followed = new ArrayList<String>();

            for(Perfil p : perfil.getSeguidos()) {
                followed.add(p.getUsuario());
            }
        }

        if(perfil.getSeguidores() != null) {
            followers = new ArrayList<String>();

            for(Perfil p : perfil.getSeguidores()) {
                followers.add(p.getUsuario());
            }
        }
        
        active = perfil.isAtivo();

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
                PessoaFisica pessoaFisica = new PessoaFisica(username);
                pessoaFisica.setCpf(cpf);
                perfil = (Perfil) pessoaFisica;
                break;

            case "PessoaJuridica":
                PessoaJuridica pessoaJuridica = new PessoaJuridica(username);
                pessoaJuridica.setCnpj(cnpj);
                perfil = (Perfil) pessoaJuridica;
                break;

            default:
                throw new UnsupportedOperationException();
        }
        
        if(this.followed != null) {
            ArrayList<Perfil> seguidos = new ArrayList<>();

            for(String usuario : this.followed) {
                seguidos.add(new Perfil(usuario));
            }

            perfil.setSeguidos(seguidos);

            ArrayList<Tweet> timeline = new ArrayList<>();

            timeline.addAll(tweetRepository.getPerfilTweets(perfil));

            for(Perfil p : seguidos) {
                List<Tweet> tweets = tweetRepository.getPerfilTweets(p);
                timeline.addAll(tweets);
            }

            perfil.setTimeline(timeline);
        }

        if(this.followers != null) {
            ArrayList<Perfil> seguidores = new ArrayList<>();

            for(String usuario : this.followers) {
                seguidores.add(new Perfil(usuario));
            }

            perfil.setSeguidores(seguidores);;
        }
        
        perfil.setAtivo(active);

        return perfil;
    }
}
