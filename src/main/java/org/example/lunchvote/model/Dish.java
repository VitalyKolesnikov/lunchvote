package org.example.lunchvote.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString
public class Dish extends AbstractNamedEntity {

    @ManyToMany(mappedBy = "dishes")
    private List<Menu> menus;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;
}