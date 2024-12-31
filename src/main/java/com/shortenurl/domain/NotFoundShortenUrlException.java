package com.shortenurl.domain;

public class NotFoundShortenUrlException extends RuntimeException {

    public NotFoundShortenUrlException() {
    }

    public NotFoundShortenUrlException(String message) {
        super(message);
    }

    public NotFoundShortenUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
