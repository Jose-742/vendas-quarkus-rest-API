package com.vendas.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Entity
public class Produto extends PanacheEntity{
    @NotNull
    @NotBlank 
    @Column(unique = true)
    private String descricao;
    
    @NotNull
    @Positive
    private double preco;
    
    @ManyToOne
    private Categoria categoria;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return getDescricao();
    }

    public void setField(String field) {
        this.descricao = field;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return the preco
     */
    public double getPreco() {
        return preco;
    }

    /**
     * @return the categora
     */
    public Categoria getCategoria() {
        return categoria;
    }
}
