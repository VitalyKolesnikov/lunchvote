package org.example.lunchvote.web.menu;

import org.example.lunchvote.model.Menu;
import org.example.lunchvote.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/menus";

    private final MenuRepository menuRepository;

    public UserMenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public List<Menu> getTodays() {
        log.info("Get todays menu");
        return menuRepository.findByDate(LocalDate.now());
    }
}