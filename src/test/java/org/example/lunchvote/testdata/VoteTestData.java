package org.example.lunchvote.testdata;

import org.example.lunchvote.TestMatcher;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.to.VotingResultTo;

import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.model.AbstractBaseEntity.START_SEQ;
import static org.example.lunchvote.testdata.MenuTestData.TODAY;
import static org.example.lunchvote.testdata.RestaurantTestData.*;
import static org.example.lunchvote.testdata.UserTestData.*;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class, "user");
    public static TestMatcher<VotingResultTo> VR_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(VotingResultTo.class);

    public static final int VOTE1_ID = START_SEQ + 22;
    public static final int VOTE2_ID = START_SEQ + 23;
    public static final int VOTE3_ID = START_SEQ + 24;
    public static final int VOTE4_ID = START_SEQ + 25;
    public static final int VOTE5_ID = START_SEQ + 26;
    public static final int VOTE6_ID = START_SEQ + 27;
    public static final int VOTE7_ID = START_SEQ + 28;
    public static final int VOTE8_ID = START_SEQ + 29;
    public static final int VOTE9_ID = START_SEQ + 30;

    public static Vote VOTE1 = new Vote(VOTE1_ID, USER1, MCD, LocalDate.of(2020, 8, 27));
    public static Vote VOTE2 = new Vote(VOTE2_ID, USER1, BK, LocalDate.of(2020, 8, 28));
    public static Vote VOTE3 = new Vote(VOTE3_ID, USER1, KFC, LocalDate.of(2020, 8, 29));
    public static Vote VOTE4 = new Vote(VOTE4_ID, USER1, BK, LocalDate.of(2020, 8, 30));
    public static Vote VOTE5 = new Vote(VOTE5_ID, USER1, KFC, TODAY);
    public static Vote VOTE6 = new Vote(VOTE6_ID, USER3, BK, TODAY);
    public static Vote VOTE7 = new Vote(VOTE7_ID, USER4, MCD, TODAY);
    public static Vote VOTE8 = new Vote(VOTE8_ID, USER5, MCD, TODAY);
    public static Vote VOTE9 = new Vote(VOTE9_ID, USER6, MCD, TODAY);

    public static List<Vote> USER1_VOTES = List.of(VOTE5, VOTE4, VOTE3, VOTE2, VOTE1);
    public static List<Vote> TODAY_VOTES = List.of(VOTE5, VOTE6, VOTE7, VOTE8, VOTE9);

    public static VotingResultTo vr1 = new VotingResultTo(MCD, 3);
    public static VotingResultTo vr2 = new VotingResultTo(KFC, 1);
    public static VotingResultTo vr3 = new VotingResultTo(BK, 1);

    public static List<VotingResultTo> VOTING_RESULT = List.of(vr1, vr2, vr3);

}
