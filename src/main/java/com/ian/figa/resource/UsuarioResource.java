package com.ian.figa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ian.figa.model.Usuario;
import com.ian.figa.model.dto.UsuarioDTO;
import com.ian.figa.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioResource {

    private static final String ID = "/{id}";

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = ID)
    public Usuario findById(@PathVariable String id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public String create(@RequestBody UsuarioDTO usuarioDto) {
        return usuarioService.create(usuarioDto);
    }

    @PutMapping(value = ID)
    public String update(@PathVariable String id, @RequestBody UsuarioDTO usuarioDto) {
        return usuarioService.update(id, usuarioDto);
    }

    @DeleteMapping(value = ID)
    public void delete(@PathVariable String id) {
        usuarioService.delete(id);
    }
}
