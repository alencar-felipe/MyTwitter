package com.alencarfelipe.mytwitter.api;

import java.util.List;

import com.alencarfelipe.mytwitter.dtos.Perfil;
import com.alencarfelipe.mytwitter.dtos.Tweet;

public interface ITwitter {
    public void criarPerfil(Perfil usuario);
    public void cancelarPerfil(String usuario);
    public void tweetar(String usuario, String mensagem);
    public List<Tweet> timeline(String usuario);
    public List<Tweet> tweets(String usuario);
    public void seguir(String seguidor, String seguido);
    public int numeroSeguidores(String usuario);
    public List<Tweet> seguidores(String usuario);
    public List<Perfil> seguidos(String usuario);
}
