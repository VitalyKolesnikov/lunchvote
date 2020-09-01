package org.example.lunchvote.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.lunchvote.model.Restaurant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VotingResultTo {

    private Restaurant restaurant;
    private long voteCount;

}