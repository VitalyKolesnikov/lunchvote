package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString
public class Dish extends AbstractNamedEntity {

    public Dish() {
    }

    public Dish(Integer id, String name, int price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    @Column(name = "price", nullable = false)
    @PositiveOrZero
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @NotNull
    @JsonIgnore
    private Menu menu;
}