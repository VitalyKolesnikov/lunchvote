package org.example.lunchvote.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.lunchvote.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MenuTo extends BaseTo {

    @NotNull
    private int restaurantId;

    @NotNull
    private LocalDate date;

    public MenuTo(Menu menu) {
        super(menu.getId());
        this.restaurantId = menu.getRestaurant().getId();
        this.date = menu.getDate();
    }
}