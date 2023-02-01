package com.klesniak.rankpollremastered.poll.controller;

import com.klesniak.rankpollremastered.poll.exception.PollAlreadySubmittedException;
import com.klesniak.rankpollremastered.poll.exception.PollNotFoundException;
import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.dto.SubmitDto;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import com.klesniak.rankpollremastered.poll.entity.PollSummary;
import com.klesniak.rankpollremastered.poll.repo.PollRepository;
import com.klesniak.rankpollremastered.poll.service.PollService;
import com.klesniak.rankpollremastered.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/polls")
@RestController
public class PollController {

    private final PollService pollService;

    private final PollRepository pollRepository;

    public PollController(PollService pollService, PollRepository pollRepository) {
        this.pollService = pollService;
        this.pollRepository = pollRepository;
    }

    @GetMapping
    public List<Poll> getPolls() {
        return pollRepository.findAll();
    }

    //TODO: test this endpoint
    @GetMapping("/external/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable("pollId") String pollId) {
        return ResponseEntity.ok(pollService.getPoll(pollId));
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable("pollId") String pollId, User user) {
        return ResponseEntity.ok(pollService.getPoll(pollId));
    }

    //TODO: test this endpoint
    @GetMapping("/{pollId}/summary")
    public PollSummary getPollSummary(@PathVariable("pollId") String pollId) {
        return pollService.getPollSummary(pollId);
    }

    @PostMapping
    public Poll createPoll(@RequestBody PollCreationDto pollCreationDto) {
        return pollService.savePoll(pollCreationDto);
    }

    //TODO: test this endpoint
    @PostMapping("/external/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody SubmitDto submitDto, HttpServletRequest request) {
        pollService.submitAnswer(submitDto, request.getRemoteAddr());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody SubmitDto submitDto, HttpServletRequest request, User user) {
        pollService.submitAnswer(submitDto, request.getRemoteAddr());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/external/submit/{pollId}/isSubmitted")
    public boolean hasAlreadySubmitted(@PathVariable("pollId") String pollId, HttpServletRequest request) {
        return pollService.isAlreadySubmitted(pollId, request.getRemoteAddr());
    }

    @GetMapping("/submit/{pollId}/isSubmitted")
    public boolean hasAlreadySubmitted(@PathVariable("pollId") String pollId, HttpServletRequest request, User user) {
        return pollService.isAlreadySubmitted(pollId, request.getRemoteAddr());
    }

    //TODO: test this exception handler
    @ExceptionHandler(PollNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePollNotFoundException() {}

    @ExceptionHandler(PollAlreadySubmittedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlePollAlreadySubmittedException() {}



}
