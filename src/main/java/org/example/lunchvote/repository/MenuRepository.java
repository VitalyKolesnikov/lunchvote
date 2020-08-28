package org.example.lunchvote.repository;

import org.example.lunchvote.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dishes WHERE m.id=:id")
    Menu findByIdEager(@Param("id") int id);

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> findByDate(@Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);
}