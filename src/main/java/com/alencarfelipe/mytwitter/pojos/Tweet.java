package com.alencarfelipe.mytwitter.pojos;

import lombok.Data;

/**
 * Encapsulate a message
 * 
 * @author alencar-felipe
 */
@Data
public class Tweet {
    private String usuario;
    private String mensagem;
}
