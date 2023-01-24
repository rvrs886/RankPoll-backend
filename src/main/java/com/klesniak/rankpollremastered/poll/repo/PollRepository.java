package com.klesniak.rankpollremastered.poll.repo;

import com.klesniak.rankpollremastered.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, String> {
}
