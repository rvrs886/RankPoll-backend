package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "submit_entries")
public class SubmitEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "poll_id")
    private String pollId;

    @ElementCollection
    private List<String> submittedAnswers;

    private String ipAddress;

    private Long userId;

    private SubmitEntry() {
    }

    public SubmitEntry(String pollId, List<String> submittedAnswers, String ipAddress, Long userId) {
        this.pollId = pollId;
        this.submittedAnswers = submittedAnswers;
        this.ipAddress = ipAddress;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getPollId() {
        return pollId;
    }

    public Long getUserId() {
        return userId;
    }

    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
