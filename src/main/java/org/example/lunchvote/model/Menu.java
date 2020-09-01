package org.example.lunchvote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes;

    public Menu(Integer id, Restaurant restaurant, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
    }

}