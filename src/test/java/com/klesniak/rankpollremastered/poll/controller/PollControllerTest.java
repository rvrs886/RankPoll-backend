package com.klesniak.rankpollremastered.poll.controller;

import com.klesniak.rankpollremastered.IntegrationTestBase;
import com.klesniak.rankpollremastered.poll.constants.AnswerType;
import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.klesniak.rankpollremastered.poll.constants.AnswerType.SINGLE_CHOICE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PollControllerTest extends IntegrationTestBase {

    @Test
    public void shouldReturnAllPolls() {

    }

    @Test
    public void shouldCreatePoll() throws Exception {
        String json = "{\"title\":\"TestPoll\",\"answers\":[\"answer1\",\"answer2\"],\"answerType\":\"SINGLE_CHOICE\"}";

        mockMvc.perform(post("/api/polls")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TestPoll"))
                .andExpect(jsonPath("$.answers").isArray())
                .andExpect(jsonPath("$.answers[0]").value("answer1"))
                .andExpect(jsonPath("$.answers[1]").value("answer2"))
                .andExpect(jsonPath("$.answers[2]").doesNotExist())
                .andExpect(jsonPath("$.answerType").value(SINGLE_CHOICE.name()))
                .andExpect(jsonPath("$.closed").value(false));
    }



}