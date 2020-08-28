package org.example.lunchvote.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MenuTo extends BaseTo {

    @NotNull
    private int restaurantId;

    @NotNull
    private LocalDate date;

}