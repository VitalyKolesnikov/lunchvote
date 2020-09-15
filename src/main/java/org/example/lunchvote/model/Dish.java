package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@ToString(exclude = "menu")
@NoArgsConstructor
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @PositiveOrZero
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @NotNull
    @JsonIgnore
    private Menu menu;

    public Dish(Integer id, String name, int price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

}