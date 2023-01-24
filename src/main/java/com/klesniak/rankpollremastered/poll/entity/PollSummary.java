package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "poll_summaries")
public class PollSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Poll poll;

    @ElementCollection
    List<AnswerSummary> answerSummaries;

    private PollSummary() {
    }

    public PollSummary(Poll poll, List<AnswerSummary> answerSummaries) {
        this.poll = poll;
        this.answerSummaries = answerSummaries;
    }

    public Long getId() {
        return id;
    }

    public Poll getPoll() {
        return poll;
    }

    public List<AnswerSummary> getAnswerSummaries() {
        return answerSummaries;
    }
}
