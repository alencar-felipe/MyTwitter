package com.alencarfelipe.mytwitter.repositorio;

import com.alencarfelipe.mytwitter.dtos.Perfil;

/**
 * Interface for MyTwitter repository
 * 
 * @author alencar-felipe
 */
public interface IRepositorioUsuario {
    /**
     * Register profile
     * 
     * @param usuario
     * @throws UJCException
     */
    public void cadastrar(Perfil usuario) throws UJCException;

    /**
     * Find profile by name
     * 
     * @param usuario
     * @return
     */
    public Perfil buscar(String usuario);

    /**
     * Update profile data
     * 
     * @param usuario
     * @throws UNCException
     */
    public void atualizar(Perfil usuario) throws UNCException;
}
