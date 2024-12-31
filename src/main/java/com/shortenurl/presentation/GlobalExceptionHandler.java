package com.shortenurl.presentation;


import com.shortenurl.domain.LackOfShortenUrlKeyException;
import com.shortenurl.domain.NotFoundShortenUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LackOfShortenUrlKeyException.class)
    public ResponseEntity<String> handleLackOfShortenUrlKeyException(LackOfShortenUrlKeyException ex) {
        log.error("자원 부족");
        return new ResponseEntity<>("자원 부족", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundShortenUrlException.class)
    public ResponseEntity<String> handleNotFoundShortenUrlException(NotFoundShortenUrlException ex) {
        log.info(ex.getMessage());
        return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("유효성 검증 실패: ");
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMessage.append(String.format("필드 %s : %s", error.getField(), error.getDefaultMessage())));

        log.debug("잘못된 요청 : {}", errorMessage);

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }


}
