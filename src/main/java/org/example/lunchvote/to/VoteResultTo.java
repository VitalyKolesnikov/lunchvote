package org.example.lunchvote.to;

import lombok.Getter;
import org.example.lunchvote.model.Restaurant;

@Getter
public class VoteResultTo {
    private final Restaurant restaurant;
    private final long voteCount;

    public VoteResultTo(Restaurant restaurant, long voteNumber) {
        this.restaurant = restaurant;
        this.voteCount = voteNumber;
    }

}