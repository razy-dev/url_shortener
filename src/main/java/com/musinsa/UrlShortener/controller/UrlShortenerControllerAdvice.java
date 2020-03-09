package com.musinsa.UrlShortener.controller;

import com.musinsa.UrlShortener.exception.ShortUrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UrlShortenerControllerAdvice {

    /**
     * ShortUrlNotFoundException 발생 시 ShortUrlNotFound 페이지로 이동
     *
     * @param e
     * @param request
     * @param model
     * @return error template name
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShortUrlNotFoundException.class)
    public String ShortUrlNotFoundHandler(ShortUrlNotFoundException e, HttpServletRequest request, Model model) {
        return "error/ShortUrlNotFound";
    }

}
