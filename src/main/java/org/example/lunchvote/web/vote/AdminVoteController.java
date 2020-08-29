package org.example.lunchvote.web.vote;

import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.VoteRepository;
import org.example.lunchvote.to.VotingResultTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/admin/votes";

    private final VoteRepository voteRepository;

    public AdminVoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("filter")
    public List<Vote> getByDate(@RequestParam LocalDate date) {
        log.info("get votes by date {}", date);
        return voteRepository.findAllByDate(date);
    }

    @GetMapping("result")
    public List<VotingResultTo> getResult(@RequestParam LocalDate date) {
        log.info("get vote result ");
        return voteRepository.getVoteResult(date)
                .stream()
                .sorted(Comparator
                        .comparingLong(VotingResultTo::getVoteCount)
                        .reversed())
                .collect(Collectors.toList());
    }
}