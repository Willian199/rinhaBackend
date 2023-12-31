package br.com.will.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Cacheable
public class Pessoa extends PanacheEntityBase {

    @Id
    @Column(name = "publicID")
    public UUID id;

    public String apelido;

    public String nome;

    public LocalDate nascimento;

    public String stack;

    public Pessoa() {
    }

    public Pessoa(UUID id, String apelido, String nome, LocalDate nascimento, String stack) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    @Column(name = "BUSCA_TRGM")
    @Generated
    @JsonIgnore
    public String busca;

}
