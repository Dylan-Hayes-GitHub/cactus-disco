package com.cactus.disco.controller.advice;

import com.cactus.disco.controller.SensorController;
import com.cactus.disco.dto.outbound.ErrorDTO;
import com.cactus.disco.exception.CactusDiscoClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {SensorController.class})
public class SensorControllerAdvice {

    @ExceptionHandler(exception = {CactusDiscoClientException.class})
    public ResponseEntity<ErrorDTO> handleClientError(CactusDiscoClientException cactusDiscoClientException) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorMessage(cactusDiscoClientException.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity
                .badRequest()
                .body(errorDTO);

    }

    @ExceptionHandler( exception = {Exception.class})
    public ResponseEntity<ErrorDTO> handleServerError(Exception exception) {
//        ErrorDTO errorDTO = ErrorDTO.builder()
//                .errorMessage("Internal server error occurred")
//                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .build();

        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorMessage(exception.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return ResponseEntity
                .badRequest()
                .body(errorDTO);
    }
}
