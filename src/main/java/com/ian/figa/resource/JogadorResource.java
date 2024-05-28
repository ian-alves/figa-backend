package com.ian.figa.resource;

import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ian.figa.model.Jogador;
import com.ian.figa.model.dto.JogadorDTO;
import com.ian.figa.service.JogadorService;

@RestController
@RequestMapping(value = "/api/jogador")
public class JogadorResource {
    @Autowired
    JogadorService jogadorService;

    @GetMapping(value = "/{id}")
    public Jogador findById(@PathVariable @Nonnull String id) throws InterruptedException, ExecutionException {
        return jogadorService.findById(id);
    }

    @PostMapping
    public String create(@RequestBody JogadorDTO jogadorDto) throws InterruptedException, ExecutionException {
        return jogadorService.create(jogadorDto);
    }

    @PutMapping(value = "/{id}")
    public String update(@PathVariable @Nonnull String id, @RequestBody @Nonnull JogadorDTO jogadorDto)
            throws InterruptedException, ExecutionException {
        return jogadorService.update(id, jogadorDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@RequestParam @Nonnull String id) {
        jogadorService.delete(id);
    }
}
