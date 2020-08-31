package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class)
public class Restaurant extends AbstractNamedEntity{

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}