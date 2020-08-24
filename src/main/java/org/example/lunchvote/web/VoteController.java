package org.example.lunchvote.web;

import org.example.lunchvote.AuthorizedUser;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.RestaurantRepository;
import org.example.lunchvote.repository.VoteRepository;
import org.example.lunchvote.util.UserUtil;
import org.example.lunchvote.util.exception.VotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.example.lunchvote.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/votes";
    public static final LocalTime VOTING_DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(voteRepository.get(id), id);
    }

    @GetMapping("/by")
    public Vote getByDate(@RequestParam String date) {
        log.info("getByDate {}", date);
        return voteRepository.findByDateAndUserId(LocalDate.parse(date), SecurityUtil.authUserId());
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void vote(@PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        LocalDate today = LocalDate.now();
        int userId = authUser.getId();
        Vote oldVote = voteRepository.findByDateAndUserId(today, userId);
        if (oldVote == null) {
            log.info("User {} has voted for restaurant {}", userId, restaurantId);
            voteRepository.save(new Vote(UserUtil.of(authUser.getUserTo()), restaurant, today));
        } else if (LocalTime.now().isBefore(VOTING_DEADLINE)) {
            log.info("User {} has re-voted for restaurant {}", userId, restaurantId);
        } else {
            throw new VotingException("Vote change is not possible already for today");
        }
    }
}