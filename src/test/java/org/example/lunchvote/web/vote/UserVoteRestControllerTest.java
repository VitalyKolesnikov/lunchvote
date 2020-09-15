package org.example.lunchvote.web.vote;

import org.example.lunchvote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.testdata.RestaurantTestData.KFC_ID;
import static org.example.lunchvote.testdata.UserTestData.USER1;
import static org.example.lunchvote.testdata.UserTestData.USER2;
import static org.example.lunchvote.testdata.VoteTestData.USER1_VOTES;
import static org.example.lunchvote.testdata.VoteTestData.VOTE_MATCHER;
import static org.example.lunchvote.web.vote.UserVoteRestController.VOTING_DEADLINE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteRestController.REST_URL + '/';

    @Test
    void getHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(USER1_VOTES));
    }

    @Test
    void getHistoryUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + KFC_ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk());
    }

    @Test
    void revote() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + KFC_ID)
                .with(userHttpBasic(USER1)));
        if (LocalTime.now().isAfter(VOTING_DEADLINE)) {
            resultActions
                    .andExpect(status().isConflict());
        } else {
            resultActions.andExpect(status().isOk());
        }
    }

    @Test
    void voteUnauth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + KFC_ID))
                .andExpect(status().isUnauthorized());
    }
}