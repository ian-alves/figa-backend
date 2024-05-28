package com.ian.figa.service;

import java.util.List;

import com.ian.figa.model.Jogador;
import com.ian.figa.model.dto.JogadorDTO;

public interface JogadorService {

    Jogador findById(String id);

    List<Jogador> findAll();

    String create(JogadorDTO obj);

    String update(String id, JogadorDTO obj);

    void delete(String id);
}
