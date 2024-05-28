package com.ian.figa.model.dto;

import com.ian.figa.model.Equipe;
import com.ian.figa.model.Posicao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogadorDTO {

    private Integer id;
    private String nome;
    private int pontuacao;
    private int idade;
    private String nacionalidade;
    private char peBatedor;
    private Double preco;
    private Equipe equipe;
    private Posicao posicao;
}
