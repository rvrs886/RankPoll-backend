package com.klesniak.rankpollremastered.poll.repo;

import com.klesniak.rankpollremastered.poll.entity.SubmitEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmitEntryRepository extends JpaRepository<SubmitEntry, Long> {

    Optional<SubmitEntry> findByIpAddress(String ipAddress);

    List<SubmitEntry> findAllByPollId(String pollId);

}
