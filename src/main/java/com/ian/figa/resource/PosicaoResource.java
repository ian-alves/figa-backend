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

import com.ian.figa.model.Posicao;
import com.ian.figa.model.dto.PosicaoDTO;
import com.ian.figa.service.PosicaoService;

@RestController
@RequestMapping(value = "/api/posicao")
public class PosicaoResource {
    @Autowired
    PosicaoService posicaoService;

    @GetMapping(value = "/{id}")
    public Posicao findById(@PathVariable @Nonnull String id) throws InterruptedException, ExecutionException {
        return posicaoService.findById(id);
    }

    @PostMapping
    public String create(@RequestBody PosicaoDTO posicaoDto) throws InterruptedException, ExecutionException {
        return posicaoService.create(posicaoDto);
    }

    @PutMapping(value = "/{id}")
    public String update(@PathVariable @Nonnull String id, @RequestBody @Nonnull PosicaoDTO posicaoDto)
            throws InterruptedException, ExecutionException {
        return posicaoService.update(id, posicaoDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@RequestParam @Nonnull String id) {
        posicaoService.delete(id);
    }
}
