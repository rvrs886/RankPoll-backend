package com.klesniak.rankpollremastered.poll.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SubmitDto {

    private String pollId;

    private List<String> submittedAnswers;

    @JsonCreator
    public SubmitDto(@JsonProperty("pollId") String pollId,
                     @JsonProperty("submittedAnswers") List<String> submittedAnswers) {
        this.pollId = pollId;
        this.submittedAnswers = submittedAnswers;
    }

    public String getPollId() {
        return pollId;
    }

    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }
}
