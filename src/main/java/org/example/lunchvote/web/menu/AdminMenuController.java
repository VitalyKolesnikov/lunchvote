package org.example.lunchvote.web.menu;

import org.example.lunchvote.model.Menu;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.repository.MenuRepository;
import org.example.lunchvote.repository.RestaurantRepository;
import org.example.lunchvote.to.MenuTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/admin/menus";

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminMenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        log.info("Get menu by id {}", id);
        return checkNotFoundWithId(menuRepository.findByIdEager(id), id);
    }

    @GetMapping("/filter")
    public List<Menu> getByDate(@RequestParam @NotNull LocalDate date) {
        log.info("Get menus by date {}", date);
        return menuRepository.findByDate(date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo menuTo) {
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        Menu created = menuRepository.save(new Menu(menuTo.getId(), menuTo.getDate(), restaurant));
        log.info("Create new menu {}", created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("Update menu with id {}", id);
        assureIdConsistent(menuTo, id);

        Menu oldMenu = menuRepository.findById(id).orElseThrow();
        Restaurant restaurant = oldMenu.getRestaurant();
        int restaurantId = restaurant.getId();

        int newRestaurantId = menuTo.getRestaurantId();
        if (newRestaurantId != restaurantId) {
            restaurant = restaurantRepository.findById(newRestaurantId).orElseThrow();
        }

        Menu newMenu = new Menu(menuTo.getId(), menuTo.getDate(), restaurant);
        menuRepository.save(newMenu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menus", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        menuRepository.delete(id);
    }
}