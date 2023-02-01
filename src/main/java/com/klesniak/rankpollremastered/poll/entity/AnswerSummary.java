package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.Embeddable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Embeddable
public class AnswerSummary {

    private String answer;

    private long answerCount = 0;

    public AnswerSummary(String answer, long answerCount) {
        this.answer = answer;
        this.answerCount = answerCount;
    }

    private AnswerSummary() {
    }

    public static AnswerSummary from(String answer) {
        return new AnswerSummary(answer, 0L);
    }

    public static Set<AnswerSummary> from(List<String> answers) {
        return answers.stream()
                .map(AnswerSummary::from)
                .collect(toSet());
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }

    public void increaseCount() {
        answerCount++;
    }
}
