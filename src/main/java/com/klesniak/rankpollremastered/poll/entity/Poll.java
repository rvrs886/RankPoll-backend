package com.klesniak.rankpollremastered.poll.entity;

import com.klesniak.rankpollremastered.poll.constants.AnswerType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    private AnswerType answerType;

    private boolean closed;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private Poll() {
    }

    public Poll(String title, List<String> answers, AnswerType answerType, boolean closed) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.answers = answers;
        this.answerType = answerType;
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

    public AnswerType getAnswerType() {
        return answerType;
    }

    public boolean isClosed() {
        return closed;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }
}
