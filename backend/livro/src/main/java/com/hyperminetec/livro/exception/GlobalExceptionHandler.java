/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kayqu
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroPadrao> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidacoes(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
