package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    // null if not found, when updated
    @Transactional
    Vote save(Vote vote);

    // null if not found
    @Query("FROM Vote v WHERE v.id=:id")
    Vote get(@Param("id") int id);

    Vote findByDateAndUserId(LocalDate date, int userId);
}