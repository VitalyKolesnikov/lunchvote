package org.example.lunchvote.web;

import org.example.lunchvote.model.Dish;
import org.example.lunchvote.model.Menu;
import org.example.lunchvote.repository.DishRepository;
import org.example.lunchvote.repository.MenuRepository;
import org.example.lunchvote.to.DishTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.example.lunchvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/admin/dishes";

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishRestController(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable int id) {
        log.info("Get dish by id {}", id);
        return dishRepository.findById(id).orElseThrow();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo) {
        checkNew(dishTo);
        int menuId = dishTo.getMenuId();
        Menu menu = menuRepository.findById(menuId).orElseThrow();

        Dish created = dishRepository.save(new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menu));
        log.info("Create new dish {}", created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("Update dish with id {}", id);
        assureIdConsistent(dishTo, id);

        Dish oldDish = dishRepository.findById(id).orElseThrow();
        Menu menu = oldDish.getMenu();
        int menuId = menu.getId();

        int newMenuId = dishTo.getMenuId();
        if (newMenuId != menuId) {
            menu = menuRepository.findById(newMenuId).orElseThrow();
        }

        Dish newDish = new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menu);
        dishRepository.save(newDish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }
}