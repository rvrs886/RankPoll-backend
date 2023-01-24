package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "submit_entries")
public class SubmitEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll;

    @ElementCollection
    private List<String> submittedAnswers;

    private String ipAddress;

    private SubmitEntry() {
    }

    public SubmitEntry(Poll poll, List<String> submittedAnswers, String ipAddress) {
        this.poll = poll;
        this.submittedAnswers = submittedAnswers;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public Poll getPoll() {
        return poll;
    }

    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
