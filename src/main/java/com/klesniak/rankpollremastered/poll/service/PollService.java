package com.klesniak.rankpollremastered.poll.service;

import com.klesniak.rankpollremastered.poll.entity.AnswerSummary;
import com.klesniak.rankpollremastered.poll.exception.PollAlreadySubmittedException;
import com.klesniak.rankpollremastered.poll.exception.PollNotFoundException;
import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.dto.SubmitDto;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import com.klesniak.rankpollremastered.poll.entity.PollSummary;
import com.klesniak.rankpollremastered.poll.entity.SubmitEntry;
import com.klesniak.rankpollremastered.poll.exception.SummaryNotFoundException;
import com.klesniak.rankpollremastered.poll.repo.PollRepository;
import com.klesniak.rankpollremastered.poll.repo.PollSummaryRepository;
import com.klesniak.rankpollremastered.poll.repo.SubmitEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private static final Logger log = LoggerFactory.getLogger(PollService.class);

    private final PollRepository pollRepository;

    private final SubmitEntryRepository submitEntryRepository;

    private final PollSummaryRepository pollSummaryRepository;

    public PollService(PollRepository pollRepository, SubmitEntryRepository submitEntryRepository, PollSummaryRepository pollSummaryRepository) {
        this.pollRepository = pollRepository;
        this.submitEntryRepository = submitEntryRepository;
        this.pollSummaryRepository = pollSummaryRepository;
    }

    public Poll getPoll(String pollId) {
        log.info("Searching poll with id: {}", pollId);
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException("Could not find poll with id: " + pollId + "!"));
    }

    public PollSummary getPollSummary(String pollId) {
        log.info("Searching summary for poll with id: {}", pollId);
        return pollSummaryRepository.findByPoll_Id(pollId)
                .orElseThrow(() -> new SummaryNotFoundException("Summary for poll with id: " + pollId + " not found!"));
    }

    public Poll savePoll(PollCreationDto pollCreationDto) {
        log.info("Saving poll: {}", pollCreationDto);
        return pollRepository.save(
                new Poll(pollCreationDto.getTitle(), pollCreationDto.getAnswers(), false)
        );
    }

    public void submitAnswer(SubmitDto submitDto, String ipAddress) {
        Optional<Poll> poll = pollRepository.findById(submitDto.getPollId());

        if (poll.isPresent() && !hasAlreadyAnswered(submitDto.getPollId(), ipAddress)) {
            log.info("Submitting answer entry for poll: {} from ip address: {}", submitDto.getPollId(), ipAddress);
            SubmitEntry submitEntry = submitEntryRepository.save(new SubmitEntry(poll.get(), submitDto.getSubmittedAnswers(), ipAddress));
            updateSummary(submitEntry);
            return;
        }

        throw new PollAlreadySubmittedException("Poll with id: " + submitDto.getPollId() + " has been already submitted by ip address: [ " + ipAddress + "]!");
    }

    public boolean hasAlreadyAnswered(String pollId, String ipAddress) {
        List<SubmitEntry> entries = submitEntryRepository.findAllByPoll_Id(pollId);
        return entries.stream()
                .anyMatch(entry -> entry.getIpAddress().equals(ipAddress));
    }

    private PollSummary updateSummary(SubmitEntry submitEntry) {
        Poll poll = submitEntry.getPoll();

        List<AnswerSummary> pollAnswers = poll.getAnswers().stream()
                .map(AnswerSummary::from)
                .toList();
        PollSummary pollSummary = pollSummaryRepository.findByPoll_Id(poll.getId())
                .orElse(new PollSummary(poll, pollAnswers));

        pollSummary.getAnswerSummaries().stream()
                .filter(answerSummary -> submitEntry.getSubmittedAnswers().contains(answerSummary.getAnswer()))
                .forEach(AnswerSummary::increaseAnswerCount);

        return pollSummaryRepository.save(pollSummary);
    }
}
