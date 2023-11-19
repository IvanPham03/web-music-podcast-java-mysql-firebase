package com.ivanpham.musicapi.controller;

import org.springframework.ui.Model;
import com.ivanpham.musicapi.exception.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleExceptionController {
    @ExceptionHandler(UserException.class)
    public String handleUserException(UserException ex, Model model){
        model.addAttribute("error",ex);
        return "error";
    }
}
