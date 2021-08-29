package com.alencarfelipe.mytwitter.api;

import java.util.ArrayList;
import java.util.List;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.IMException;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;
import com.alencarfelipe.mytwitter.repositorio.UJCException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Primary
@Component
@RequiredArgsConstructor
public class Twitter implements ITwitter {
    @Setter
    @Autowired
    private IRepositorioUsuario repositorioUsuario;
    
    @Setter
    @Autowired
    private ITweetRepository tweetRepository;

    @Override
    public void criarPerfil(Perfil usuario) throws PEException {
        try{
            usuario.setAtivo(true);
            usuario.setSeguidores(new ArrayList<Perfil>());
            usuario.setSeguidos(new ArrayList<Perfil>());
            repositorioUsuario.cadastrar(usuario);
        } catch (UJCException ex) {
            throw new PEException();
        }
    }

    @Override
    public void cancelarPerfil(String usuario) throws PIException {
        Perfil perfil = repositorioUsuario.buscar(usuario);

        if(perfil == null) {
            throw new PIException();
        }
        
        perfil.setAtivo(false);
    }

    @Override
    public void tweetar(String usuario, String mensagem) throws PIException, MFPException {
        if(repositorioUsuario.buscar(usuario) == null) {
            throw new PIException();
        }

        Tweet tweet = new Tweet();
        tweet.setUsuario(usuario);
        tweet.setMensagem(mensagem);

        try {
            tweetRepository.addTweet(tweet);
        } catch(IMException ex) {
            throw new MFPException();
        }
    }

    @Override
    public List<Tweet> timeline(String usuario) throws PIException, PDException {
        Perfil perfil = repositorioUsuario.buscar(usuario);

        if(perfil == null) {
            throw new PIException();
        }

        if(!perfil.isAtivo()) {
            throw new PDException();
        }

        return perfil.getTimeline();
    }

    @Override
    public List<Tweet> tweets(String usuario) throws PIException, PDException {
        Perfil perfil = repositorioUsuario.buscar(usuario);

        if(perfil == null) {
            throw new PIException();
        }

        if(!perfil.isAtivo()) {
            throw new PDException();
        }

        return tweetRepository.getPerfilTweets(perfil);
    }

    @Override
    public void seguir(String seguidor, String seguido) throws PIException, PDException, SIException {
        if(seguidor.equals(seguido)) {
            throw new SIException();
        }

        Perfil perfilSeguidor = repositorioUsuario.buscar(seguidor);
        Perfil perfilSeguido = repositorioUsuario.buscar(seguido);

        if(perfilSeguidor == null || perfilSeguido == null) {
            throw new PIException();
        }

        if(!perfilSeguidor.isAtivo() || !perfilSeguido.isAtivo()) {
            throw new PDException();
        }

        perfilSeguido.addSeguidor(perfilSeguidor);
        perfilSeguidor.addSeguido(perfilSeguido);
    }

    @Override
    public int numeroSeguidores(String usuario) throws PIException, PDException {
        return seguidores(usuario).size();
    }

    @Override
    public List<Perfil> seguidores(String usuario)  throws PIException, PDException {
        Perfil perfil = repositorioUsuario.buscar(usuario);
        
        if(perfil == null) {
            throw new PIException();
        }

        if(!perfil.isAtivo()) {
            throw new PDException();
        }

        return perfil.getSeguidores();
    }

    @Override
    public List<Perfil> seguidos(String usuario) {
        Perfil perfil = repositorioUsuario.buscar(usuario);
        
        if(perfil == null) {
            throw new PIException();
        }

        if(!perfil.isAtivo()) {
            throw new PDException();
        }

        return perfil.getSeguidos();
    }
    
}
