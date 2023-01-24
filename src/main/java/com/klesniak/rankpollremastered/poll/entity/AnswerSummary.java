package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AnswerSummary {

    private String answer;

    private int answerCount = 0;

    public AnswerSummary(String answer, int answerCount) {
        this.answer = answer;
        this.answerCount = answerCount;
    }

    public AnswerSummary() {
    }

    public static AnswerSummary from(String answer) {
        return new AnswerSummary(answer, 0);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void increaseAnswerCount() {
        this.answerCount++;
    }
}
