package org.example.lunchvote.repository;

import org.example.lunchvote.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    // null if not found, when updated
    @Transactional
    Dish save(Dish dish);

    // null if not found
    @Query("FROM Dish d WHERE d.id=:id")
    Dish get(@Param("id") int id);

    // false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);
}