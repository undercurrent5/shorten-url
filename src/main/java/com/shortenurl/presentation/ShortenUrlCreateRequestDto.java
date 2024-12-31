package com.shortenurl.presentation;

import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@ToString
public class ShortenUrlCreateRequestDto {
    @NotNull
    @URL(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String originalUrl;

    public ShortenUrlCreateRequestDto() {
    }

    public ShortenUrlCreateRequestDto(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
