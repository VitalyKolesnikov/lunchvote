package org.example.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class Restaurant extends AbstractNamedEntity {

}