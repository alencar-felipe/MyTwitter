package com.alencarfelipe.mytwitter.dtos;

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
