package com.alencarfelipe.mytwitter.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class Perfil {
    @Getter
    @Setter
    private String usuario;
    
    @Getter
    private List<Perfil> seguidos;
    
    @Getter
    private List<Perfil> seguidores;
    
    @Getter
    private List<Tweet> timeline;
    
    @Setter
    private boolean ativo;

    public Perfil(String usuario) {
        setUsuario(usuario);
    }

    public void addSeguido(Perfil usuario) {
        seguidos.add(usuario);
    }

    public void addSeguidor(Perfil usuario) {
        seguidores.add(usuario);
    }

    public void addTweet(Tweet tweet) {
        timeline.add(tweet);
    }

    public boolean isAtivo() {
        return ativo;
    }
}
