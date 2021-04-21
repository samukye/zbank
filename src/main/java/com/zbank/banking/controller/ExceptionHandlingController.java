package com.zbank.banking.controller;

import com.zbank.banking.exceptoin.AccountNotFoundException;
import com.zbank.banking.exceptoin.BadRequestException;
import com.zbank.banking.exceptoin.NotFoundException;
import com.zbank.banking.exceptoin.TransferValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleAccountNotFoundException(AccountNotFoundException accountNotFoundException) {
        return new ErrorInfo("ACCOUNT_NOT_FOUND", accountNotFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransferValidationException.class)
    @ResponseBody
    public ErrorInfo handleTransferValidationException(TransferValidationException transferValidationException) {
        return new ErrorInfo(transferValidationException.getErrorKey(), transferValidationException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorInfo handleAccountNotFoundException(BadRequestException badRequestException) {
        return new ErrorInfo(badRequestException.getErrorKey(), badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleNotFoundException(NotFoundException notFoundException) {
        return new ErrorInfo("NOT_FOUND", notFoundException.getMessage());
    }
}
