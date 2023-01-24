package com.klesniak.rankpollremastered.poll.dto;

import java.util.List;

public class PollCreationDto {

    private String title;

    private List<String> answers;

    public PollCreationDto(String title, List<String> answers) {
        this.title = title;
        this.answers = answers;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "PollCreationDto[" +
                "title='" + title + '\'' +
                ", answers=" + answers +
                ']';
    }
}
