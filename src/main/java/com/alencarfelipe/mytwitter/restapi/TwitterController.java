package com.alencarfelipe.mytwitter.restapi;

import java.util.List;

import com.alencarfelipe.mytwitter.api.ITwitter;
import com.alencarfelipe.mytwitter.api.MFPException;
import com.alencarfelipe.mytwitter.api.PDException;
import com.alencarfelipe.mytwitter.api.PEException;
import com.alencarfelipe.mytwitter.api.PIException;
import com.alencarfelipe.mytwitter.api.SIException;
import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.PerfilDTO;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class TwitterController {
    @Autowired
    ITwitter twitter;
    
    @Autowired
    ITweetRepository tweetRepository;

    @PostMapping(
        path = "/createProfile",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}    
    )
    public void createProfile(PerfilDTO perfil) {
        try {
            twitter.criarPerfil(perfil.toPerfil(tweetRepository));

        } catch(UnsupportedOperationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    "Invalid data.");

        } catch(PEException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                ex.getClass().getSimpleName());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");
        }
    }

    @PostMapping(
        path = "/cancelProfile",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}    
    )
    public void cancelProfile(String username) {
        try {
            twitter.cancelarPerfil(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");
        }
    }

    @PostMapping(
        path = "/tweet",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public void tweet(String username, String message) {
        try {
            twitter.tweetar(username, message);

        } catch(PIException | MFPException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                ex.getClass().getSimpleName());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error.");

        }
    }

    @GetMapping(
        path = "/timeline"
    )
    public List<Tweet> timeline(String username) {
        try {
            return twitter.timeline(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");
        } 
    }

    @GetMapping(
        path = "/tweets"
    )
    public List<Tweet> tweets(String username) {
        try {
            return twitter.tweets(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName());

        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");

        } 
    }

    @PostMapping(
        path = "/follow",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public void follow(String follower, String followed) {
        try {
            twitter.seguir(follower, followed);

        } catch(PIException | PDException | SIException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName()); 

        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");

        } 
    }

    @GetMapping(
        path = "/numberOfFollowers"
    )
    public int numberOfFollowers(String username) {
        try {
            return twitter.numeroSeguidores(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName()); 

        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");

        } 
    }

    @GetMapping(
        path = "/followers"
    )
    public List<Perfil> followers(String username) {
        try {
            return twitter.seguidores(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName()); 

        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");

        }
    }

    @GetMapping(
        path = "/followed"
    )
    public List<Perfil> followed(String username) {
        try {
            return twitter.seguidos(username);

        } catch(PIException | PDException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			    ex.getClass().getSimpleName()); 

        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
			    "Internal server error.");

        }
    }
}
