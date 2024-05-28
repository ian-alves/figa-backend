package com.ian.figa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PosicaoDTO {

    private Integer id;
    private String descricao;
    private String sigla;
}
