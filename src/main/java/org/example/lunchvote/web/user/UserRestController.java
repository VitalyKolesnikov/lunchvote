package org.example.lunchvote.web.user;

import lombok.extern.slf4j.Slf4j;
import org.example.lunchvote.model.User;
import org.example.lunchvote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.example.lunchvote.util.ValidationUtil.*;

@Controller
@Slf4j
@RequestMapping("/api/users/")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAll() {
        log.info("getAll");
        List<User> users = repository.getAll();

        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(users, HttpStatus.OK);
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }
}