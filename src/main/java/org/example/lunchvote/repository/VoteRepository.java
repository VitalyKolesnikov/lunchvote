package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.example.lunchvote.to.VotingResultTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findAllByDate(@Param("date") LocalDate date);

    Vote findByDateAndUserId(LocalDate date, int userId);

    List<Vote> findAllByUserIdOrderByDateDesc(@Param("id") int userId);

    @Query("SELECT new org.example.lunchvote.to.VotingResultTo(v.restaurant, COUNT(v)) " +
            "FROM Vote v WHERE v.date=:date GROUP BY v.restaurant")
    List<VotingResultTo> getVoteResult(@Param("date") LocalDate date);
}