package com.alencarfelipe.mytwitter.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulate a user
 */

public class Perfil {
    @Getter
    @Setter
    private String usuario;
    
    @Getter
    @Setter
    private List<Perfil> seguidos;
    
    @Getter
    @Setter
    private List<Perfil> seguidores;
    
    @Getter
    @Setter
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
