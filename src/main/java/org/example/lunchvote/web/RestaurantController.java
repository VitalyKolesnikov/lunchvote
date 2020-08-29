package org.example.lunchvote.web;

import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.example.lunchvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/admin/restaurants";

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("Get all restaurants");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable int id) {
        log.info("Get restaurant by id {}", id);
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("/by")
    public Restaurant getByName(@RequestParam String name) {
        log.info("Get restaurant by name {}", name);
        return repository.findByName(name);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create new restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("Update restaurant with id={}", id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        repository.delete(id);
    }
}