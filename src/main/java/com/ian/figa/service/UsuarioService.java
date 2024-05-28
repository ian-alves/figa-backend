package com.ian.figa.service;

import java.util.List;

import com.ian.figa.model.Usuario;
import com.ian.figa.model.dto.UsuarioDTO;

public interface UsuarioService {

    Usuario findById(String id);

    List<Usuario> findAll();

    String create(UsuarioDTO obj);

    String update(String id, UsuarioDTO obj);

    void delete(String id);
}
