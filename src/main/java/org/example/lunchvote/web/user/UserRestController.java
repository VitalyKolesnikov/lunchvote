package org.example.lunchvote.web.user;

import lombok.extern.slf4j.Slf4j;
import org.example.lunchvote.model.User;
import org.example.lunchvote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.lunchvote.util.ValidationUtil.*;

@RestController
@Slf4j
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    static final String REST_URL = "/rest/users";

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.info("getAll");
        List<User> users = repository.getAll();
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        log.info("get {}", id);
        User user = repository.get(id);
        return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(user, HttpStatus.OK);
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        checkNotFoundWithId(repository.save(user), user.id());
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return repository.getByEmail(email);
    }

}