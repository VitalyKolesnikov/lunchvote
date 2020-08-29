package org.example.lunchvote.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.lunchvote.HasId;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseTo implements HasId {
    protected Integer id;

    public BaseTo(Integer id) {
        this.id = id;
    }

}