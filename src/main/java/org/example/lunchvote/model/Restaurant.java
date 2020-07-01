package org.example.lunchvote.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    public Restaurant() {
    }

}