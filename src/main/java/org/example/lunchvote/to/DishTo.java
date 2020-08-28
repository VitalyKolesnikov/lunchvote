package org.example.lunchvote.to;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 3, max = 75)
    private String name;

    private int price;

    private int menuId;

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", menuId=" + menuId +
                '}';
    }
}