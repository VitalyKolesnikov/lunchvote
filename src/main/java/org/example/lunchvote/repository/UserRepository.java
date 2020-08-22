package org.example.lunchvote.repository;


import org.example.lunchvote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    // null if not found, when updated
    @Transactional
    User save(User user);

    // null if not found
    @Query("FROM User u WHERE u.id=:id")
    User get(@Param("id") int id);

    // false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    // null if not found
    User getByEmail(String email);

    @Query("FROM User u")
    List<User> getAll();
}