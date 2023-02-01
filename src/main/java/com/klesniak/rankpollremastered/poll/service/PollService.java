package com.klesniak.rankpollremastered.poll.service;

import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.dto.SubmitDto;
import com.klesniak.rankpollremastered.poll.entity.AnswerSummary;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import com.klesniak.rankpollremastered.poll.entity.PollSummary;
import com.klesniak.rankpollremastered.poll.entity.SubmitEntry;
import com.klesniak.rankpollremastered.poll.exception.AnswerNotFoundException;
import com.klesniak.rankpollremastered.poll.exception.PollAlreadySubmittedException;
import com.klesniak.rankpollremastered.poll.exception.PollNotFoundException;
import com.klesniak.rankpollremastered.poll.exception.SummaryNotFoundException;
import com.klesniak.rankpollremastered.poll.repo.PollRepository;
import com.klesniak.rankpollremastered.poll.repo.PollSummaryRepository;
import com.klesniak.rankpollremastered.poll.repo.SubmitEntryRepository;
import com.klesniak.rankpollremastered.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    private static final Logger log = LoggerFactory.getLogger(PollService.class);

    private final PollRepository pollRepository;
    private final SubmitEntryRepository submitEntryRepository;
    private final PollSummaryRepository pollSummaryRepository;

    public PollService(PollRepository pollRepository,
                       SubmitEntryRepository submitEntryRepository,
                       PollSummaryRepository pollSummaryRepository) {
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
        return pollSummaryRepository.findByPollId(pollId)
                .orElseThrow(() -> new SummaryNotFoundException("Summary for poll with id: " + pollId + " not found!"));
    }

    public Poll savePoll(PollCreationDto pollCreationDto) {
        log.info("Saving poll: {}", pollCreationDto);
        return pollRepository.save(
                new Poll(pollCreationDto.getTitle(), pollCreationDto.getAnswers(), pollCreationDto.getAnswerType(), false)
        );
    }

    public void submitAnswer(SubmitDto submitDto, String ipAddress) {

        if (!isAlreadySubmitted(submitDto.getPollId(), ipAddress)) {
            log.info("Submitting answer entry for poll: {} from ip address: {}", submitDto.getPollId(), ipAddress);
            SubmitEntry submitEntry = submitEntryRepository.save(new SubmitEntry(submitDto.getPollId(), submitDto.getSubmittedAnswers(), ipAddress, null));
            updateSummary(submitEntry);
            return;
        }

        throw new PollAlreadySubmittedException("Poll with id: " + submitDto.getPollId() + " has been already submitted by ip address: [" + ipAddress + "]!");
    }

    public void submitAnswer(SubmitDto submitDto, User user) {

        if (!isAlreadySubmitted(submitDto.getPollId(), user)) {
            log.info("Submitting answer entry for poll: {} from user with id: {}", submitDto.getPollId(), user.getId());
            SubmitEntry submitEntry = submitEntryRepository.save(new SubmitEntry(submitDto.getPollId(), submitDto.getSubmittedAnswers(), null, user.getId()));
            updateSummary(submitEntry);
            return;
        }

        throw new PollAlreadySubmittedException("Poll with id: " + submitDto.getPollId() + " has been already submitted by user with id: [" + user.getId() + "]!");
    }

    public boolean isAlreadySubmitted(String pollId, String ipAddress) {
        List<SubmitEntry> entries = submitEntryRepository.findAllByPollId(pollId);
        return entries.stream()
                .anyMatch(entry -> entry.getIpAddress().equals(ipAddress));
    }

    public boolean isAlreadySubmitted(String pollId, User user) {
        List<SubmitEntry> entries = submitEntryRepository.findAllByPollId(pollId);
        return entries.stream()
                .anyMatch(entry -> entry.getUserId().equals(user.getId()));
    }

    private void updateSummary(SubmitEntry submitEntry) {
        PollSummary pollSummary = pollSummaryRepository.findByPollId(submitEntry.getPollId())
                .orElse(
                        new PollSummary(
                                submitEntry.getPollId(),
                                AnswerSummary.from(submitEntry.getSubmittedAnswers())
                        )
                );

        updateAnswers(pollSummary, submitEntry);
    }

    private void updateAnswers(PollSummary pollSummary, SubmitEntry submitEntry) {
        try {
            submitEntry.getSubmittedAnswers()
                    .forEach(answer -> {
                        AnswerSummary answerSummary = pollSummary.getAnswerSummaryByAnswer(answer);
                        answerSummary.increaseCount();
                    });
        } catch (IllegalStateException e) {
            throw new AnswerNotFoundException(e.getMessage());
        }
        pollSummaryRepository.save(pollSummary);
    }
}
