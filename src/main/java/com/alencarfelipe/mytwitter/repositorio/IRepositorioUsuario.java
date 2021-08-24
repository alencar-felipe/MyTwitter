package com.alencarfelipe.mytwitter.repositorio;

import com.alencarfelipe.mytwitter.dtos.Perfil;

public interface IRepositorioUsuario {
    public void cadastrar(Perfil usuario) throws UJCException;
    public Perfil buscar(String usuario);
    public void atualizar(Perfil usuario) throws UNCException;
}
