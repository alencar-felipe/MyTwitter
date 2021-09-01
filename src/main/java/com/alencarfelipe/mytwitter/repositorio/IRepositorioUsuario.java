package com.alencarfelipe.mytwitter.repositorio;

import com.alencarfelipe.mytwitter.pojos.Perfil;

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

    /**
     * Delete all user data (FOR INTERNAL USE ONLY)
     * Useful for integration tests
     * 
     * @param usuario
     * @throws UNCException
     */
    public void delete(Perfil usuario) throws UNCException;

    /**
     * Returns true if user exist
     * 
     * @param usuario
     */
    public boolean exists(String usuario);
}
