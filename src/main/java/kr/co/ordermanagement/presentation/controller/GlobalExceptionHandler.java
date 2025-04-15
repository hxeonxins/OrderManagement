package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.domain.exception.CancelNotAllowedException;
import kr.co.ordermanagement.domain.exception.EntityNotFoundException;
import kr.co.ordermanagement.domain.exception.NotEnoughAmountException;
import kr.co.ordermanagement.presentation.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CancelNotAllowedException.class)
  public ResponseEntity<ErrorMessageDto> handleCancelNotAllowedException(
          //ErrorMessageDto로 메세지를 받음(JSON타입)
          CancelNotAllowedException ex
  ){
    ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());
    return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorMessageDto> handleEntityNotFoundException(
          EntityNotFoundException ex
  ){
    ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());
    return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotEnoughAmountException.class)
  public ResponseEntity<ErrorMessageDto> handleNotEnoughAmountException(
          NotEnoughAmountException ex
  ){
    ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());
    return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
  }
}
