package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "poll_summaries")
public class PollSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "poll_id")
    private String pollId;

    @ElementCollection
    private Set<AnswerSummary> answerSummaries;

    private PollSummary() {
    }

    public PollSummary(String pollId, Set<AnswerSummary> answerSummaries) {
        this.pollId = pollId;
        this.answerSummaries = answerSummaries;
    }

    public Long getId() {
        return id;
    }

    public void setAnswerSummaries(Set<AnswerSummary> answerSummaries) {
        this.answerSummaries = answerSummaries;
    }

    public Set<AnswerSummary> getAnswerSummaries() {
        return answerSummaries;
    }

    public AnswerSummary getAnswerSummaryByAnswer(String answer) {
        return answerSummaries.stream()
                .filter(answerSummary -> answerSummary.getAnswer().equals(answer))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Answer: [" + answer + "] not found!"));
    }
}
