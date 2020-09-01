package org.example.lunchvote.web.vote;

import org.example.lunchvote.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.testdata.MenuTestData.TODAY;
import static org.example.lunchvote.testdata.UserTestData.ADMIN;
import static org.example.lunchvote.testdata.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteRestController.REST_URL + '/';

    @Test
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?date=" + TODAY)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(TODAY_VOTES));
    }

    @Test
    void getByDateUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?date=" + TODAY))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getResult() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "result?date=" + TODAY)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VR_MATCHER.contentJson(VOTING_RESULT));
    }

    @Test
    void getResultUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "result?date=" + TODAY))
                .andExpect(status().isUnauthorized());
    }
}