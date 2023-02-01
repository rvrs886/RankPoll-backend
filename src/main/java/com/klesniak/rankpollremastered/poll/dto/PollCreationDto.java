package com.klesniak.rankpollremastered.poll.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.klesniak.rankpollremastered.poll.constants.AnswerType;

import java.util.List;

public class PollCreationDto {

    private String title;

    private List<String> answers;

    private AnswerType answerType;

    @JsonCreator
    public PollCreationDto(@JsonProperty(value = "title", required = true) String title,
                           @JsonProperty(value = "answers", required = true) List<String> answers,
                           @JsonProperty(value = "answerType", required = true) AnswerType answerType) {
        this.title = title;
        this.answers = answers;
        this.answerType = answerType;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    @Override
    public String toString() {
        return "PollCreationDto[" +
                "title='" + title + '\'' +
                ", answers=" + answers +
                ']';
    }
}
