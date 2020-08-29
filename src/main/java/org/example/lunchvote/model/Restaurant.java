package org.example.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class)
public class Restaurant extends AbstractNamedEntity{

}