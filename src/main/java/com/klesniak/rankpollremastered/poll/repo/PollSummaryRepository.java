package com.klesniak.rankpollremastered.poll.repo;

import com.klesniak.rankpollremastered.poll.entity.PollSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollSummaryRepository extends JpaRepository<PollSummary, Long> {

    Optional<PollSummary> findByPoll_Id(String pollId);

}
