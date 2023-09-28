package org.learning.java.springlamiapizzeriacrud.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pizze")
public class Pizza {
    //ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 60)
    @NotBlank(message = "Deve inserire un nome!")
    private String name;
    @NotBlank(message = "Inserisca una descrizione della pizza!")
    private String description;
    @Min(1)
    @NotNull
    private BigDecimal price;
    @NotBlank(message = "Inserisca un'immagine per la pizza!")
    private String foto;

    @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})
    private List<Offerte> offerte;

    @ManyToMany
    private List<Ingredienti> ingredienti;

    //GETTER E SETTER

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Offerte> getOfferte() {
        return offerte;
    }

    public void setOfferte(List<Offerte> offerte) {
        this.offerte = offerte;
    }

    public List<Ingredienti> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingredienti> ingredienti) {
        this.ingredienti = ingredienti;
    }
}
