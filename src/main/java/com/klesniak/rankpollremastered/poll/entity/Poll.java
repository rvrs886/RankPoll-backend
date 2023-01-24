package com.klesniak.rankpollremastered.poll.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "polls")
public class Poll {

    @Id
    private String id;

    private String title;

    @ElementCollection
    private List<String> answers;

    private boolean closed;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private Poll() {
    }

    public Poll(String title, List<String> answers, boolean closed) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.answers = answers;
        this.closed = closed;
    }

    void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isClosed() {
        return closed;
    }

    LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }
}
