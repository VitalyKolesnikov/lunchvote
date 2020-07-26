package org.example.lunchvote.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString
public class Dish extends AbstractNamedEntity {

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    public Dish() {
    }
}
