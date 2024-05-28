package com.ian.figa.service;

import java.util.List;

import com.ian.figa.model.Posicao;
import com.ian.figa.model.dto.PosicaoDTO;

public interface PosicaoService {

    Posicao findById(String id);

    List<Posicao> findAll();

    String create(PosicaoDTO obj);

    String update(String id, PosicaoDTO obj);

    void delete(String id);
}
