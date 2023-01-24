package com.klesniak.rankpollremastered;

import com.klesniak.rankpollremastered.poll.repo.PollRepository;
import com.klesniak.rankpollremastered.poll.service.PollService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class IntegrationTestBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected PollRepository pollRepository;

    @Autowired
    protected PollService pollService;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

}
