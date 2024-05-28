package com.ian.figa.resource;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ian.figa.model.Equipe;
import com.ian.figa.model.dto.EquipeDTO;
import com.ian.figa.service.EquipeService;

@RestController
@RequestMapping(value = "/api/equipe")
public class EquipeResource {
    @Autowired
    EquipeService equipeService;

    @GetMapping(value = "/{id}")
    public Equipe findById(@PathVariable String id) throws InterruptedException, ExecutionException {
        return equipeService.findById(id);
    }

    @PostMapping
    public String create(@RequestBody EquipeDTO equipeDto) throws InterruptedException, ExecutionException {
        return equipeService.create(equipeDto);
    }

    @PutMapping(value = "/{id}")
    public String update(@PathVariable String id, @RequestBody EquipeDTO equipeDto)
            throws InterruptedException, ExecutionException {
        return equipeService.update(id, equipeDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        equipeService.delete(id);
    }
}
