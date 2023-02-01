package com.klesniak.rankpollremastered;

import com.klesniak.rankpollremastered.poll.constants.AnswerType;
import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import com.klesniak.rankpollremastered.poll.repo.PollRepository;
import com.klesniak.rankpollremastered.poll.repo.PollSummaryRepository;
import com.klesniak.rankpollremastered.poll.repo.SubmitEntryRepository;
import com.klesniak.rankpollremastered.poll.service.PollService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class IntegrationTestBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected PollRepository pollRepository;

    @Autowired
    protected SubmitEntryRepository submitEntryRepository;

    @Autowired
    protected PollSummaryRepository pollSummaryRepository;

    @Autowired
    protected PollService pollService;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected Poll createPoll(String title, List<String> answers, AnswerType answerType) {
        return createPoll(title, answers, answerType, false);
    }

    protected Poll createPoll(String title, List<String> answers, AnswerType answerType, boolean closed) {
        Poll poll = new Poll(title, answers, answerType, closed);
        return pollRepository.save(poll);
    }

}
