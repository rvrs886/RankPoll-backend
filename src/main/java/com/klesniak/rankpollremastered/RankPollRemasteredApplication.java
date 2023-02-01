package com.klesniak.rankpollremastered;

import com.klesniak.rankpollremastered.poll.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(WebConfig.class)
@SpringBootApplication
public class RankPollRemasteredApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankPollRemasteredApplication.class, args);
    }

}
