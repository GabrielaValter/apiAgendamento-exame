package br.csi.agendamento.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

record ApiError(int status, String error, String message, String path, Instant timestamp, List<String> details) {}

@RestControllerAdvice
public class ApiException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex, jakarta.servlet.http.HttpServletRequest req) {
        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> e.getField()+": "+ Optional.ofNullable(e.getDefaultMessage()).orElse("inválido")).toList();
        return new ApiError(400, "Bad Request", "Dados inválidos", req.getRequestURI(), Instant.now(), details);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(EntityNotFoundException ex, jakarta.servlet.http.HttpServletRequest req) {
        return new ApiError(404, "Not Found", ex.getMessage(), req.getRequestURI(), Instant.now(), List.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraint(DataIntegrityViolationException ex, jakarta.servlet.http.HttpServletRequest req) {
        return new ApiError(409, "Conflict", "Violação de integridade", req.getRequestURI(), Instant.now(), List.of(ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegal(IllegalArgumentException ex, jakarta.servlet.http.HttpServletRequest req) {
        return new ApiError(400, "Bad Request", ex.getMessage(), req.getRequestURI(), Instant.now(), List.of());
    }
}
