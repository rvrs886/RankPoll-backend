package com.klesniak.rankpollremastered.poll.exception;

public class PollAlreadySubmittedException extends RuntimeException {

    public PollAlreadySubmittedException(String message) {
        super(message);
    }

}
