package com.shortenurl.application;

import com.shortenurl.domain.LackOfShortenUrlKeyException;
import com.shortenurl.domain.NotFoundShortenUrlException;
import com.shortenurl.domain.ShortenUrl;
import com.shortenurl.domain.ShortenUrlRepository;
import com.shortenurl.presentation.ShortenUrlCreateRequestDto;
import com.shortenurl.presentation.ShortenUrlCreateResponseDto;
import com.shortenurl.presentation.ShortenUrlInformationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SimpleShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;


    public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResponseDto generateShortenUrl(ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {
        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.saveShortenUrl(shortenUrl);
        log.info("shortenUrl 생성: {}", shortenUrl);

        return new ShortenUrlCreateResponseDto(shortenUrl);
    }

    public String getOriginalUrlShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (shortenUrl == null) {
           throw new NotFoundShortenUrlException("단축 URL을 생성하지 못했습니다." + shortenUrlKey);
        }

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        return shortenUrl.getOriginalUrl();
    }

    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (shortenUrl == null) {
            throw new NotFoundShortenUrlException("단축 URL을 생성하지 못했습니다." + shortenUrlKey);
        }

        return new ShortenUrlInformationDto(shortenUrl);
    }

    public List<ShortenUrlInformationDto> getAllShortenUrlInformationDto() {
        List<ShortenUrl> shortenUrls = shortenUrlRepository.findAll();

        return shortenUrls
                .stream()
                .map(shortenUrl -> new ShortenUrlInformationDto(shortenUrl))
                .toList();
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;

        while(count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if (null == shortenUrl) {
                return shortenUrlKey;
            }
            log.warn("단축 URL 생성 재시도 횟수: {}", count + 1);
        }

        throw new LackOfShortenUrlKeyException();
    }

}
