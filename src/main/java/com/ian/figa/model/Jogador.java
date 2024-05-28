package com.ian.figa.model;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private int pontuacao;
    private int idade;
    private String nacionalidade;
    private char peBatedor;
    private Double preco;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "posicao_id")
    private Posicao posicao;
}