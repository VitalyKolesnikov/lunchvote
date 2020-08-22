package org.example.lunchvote.repository;

import org.example.lunchvote.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // null if not found, when updated
    @Transactional
    Menu save(Menu menu);

    // null if not found
    @Query("FROM Menu m WHERE m.id=:id")
    Menu get(@Param("id") int id);

    // false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);
}