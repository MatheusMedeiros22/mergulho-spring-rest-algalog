package com.algaworks.algalog.domain.model;

import com.algaworks.algalog.domain.ValidationGroups;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne //muitas entregas possuem um cliente
    private Cliente cliente;

    @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    @Embedded
    private Destinatario destinatario;

    private BigDecimal taxa;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEntrega statusEntrega;


    private OffsetDateTime dataPedido;


    private OffsetDateTime dataFinalizacao;

    public Ocorrencia adicionarOcorrencia(String descricao) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setDescricao(descricao);
        ocorrencia.setDateRegistro(OffsetDateTime.now());
        ocorrencia.setEntrega(this);

        this.getOcorrencias().add(ocorrencia);
        return ocorrencia;
    }

    public void finalizar() {
        if(naoPodeSerFinalizada()){
            throw new NegocioException("Entrega nao pode ser finalizada");
        }

        setStatusEntrega(StatusEntrega.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
    public boolean podeSerFinalizada(){
        return StatusEntrega.PENDENTE.equals((getStatusEntrega()));
    }
    public boolean naoPodeSerFinalizada(){
        return !podeSerFinalizada();
    }
}
