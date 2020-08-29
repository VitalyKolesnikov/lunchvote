package org.example.lunchvote.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.lunchvote.model.Restaurant;

@Getter
@AllArgsConstructor
public class VotingResultTo {

    private final Restaurant restaurant;
    private final long voteCount;

}