package org.example.lunchvote.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.lunchvote.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 75)
    private String name;

    @PositiveOrZero
    private int price;

    @NotNull
    private int menuId;

    public DishTo(Dish dish) {
        super(dish.getId());
        this.name = dish.getName();
        this.price = dish.getPrice();
        this.menuId = dish.getMenu().getId();
    }
}