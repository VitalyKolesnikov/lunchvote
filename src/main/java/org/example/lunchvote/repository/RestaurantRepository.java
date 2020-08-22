package org.example.lunchvote.repository;

import org.example.lunchvote.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    // null if not found, when updated
    @Transactional
    Restaurant save(Restaurant restaurant);

    // null if not found
    @Query("FROM Restaurant r WHERE r.id=:id")
    Restaurant get(@Param("id") int id);

    @Query("FROM Restaurant r")
    List<Restaurant> getAll();

    // false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    Restaurant getByName(String name);
}