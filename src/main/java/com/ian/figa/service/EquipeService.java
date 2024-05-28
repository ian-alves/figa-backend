package com.ian.figa.service;

import java.util.List;

import com.ian.figa.model.Equipe;
import com.ian.figa.model.dto.EquipeDTO;

public interface EquipeService {

    Equipe findById(String id);

    List<Equipe> findAll();

    String create(EquipeDTO obj);

    String update(String id, EquipeDTO obj);

    void delete(String id);
}
