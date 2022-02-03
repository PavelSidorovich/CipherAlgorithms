package com.sidorovich.tatarinov.cpl.controller;

import com.sidorovich.tatarinov.cpl.exception.CorruptedMessageException;
import com.sidorovich.tatarinov.cpl.exception.InvalidGrilleKeyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class CommonExceptionHandler {

    private static final String INVALID_GRILLE = "invalid.grille";
    private static final String CORRUPTED_MESSAGE = "corrupted.message";
    private static final String MISSING_REQUEST_PARAMETER = "missing.request.parameter";
    private static final String ERROR_NOT_DEFINED = "error.not.defined";
    private static final String METHOD_NOT_ALLOWED = "method.not.allowed";
    private static final String TYPE_MISMATCH = "type.mismatch";
    private static final String MESSAGE_NOT_READABLE = "message.not.readable";
    private static final String MESSAGE_FIELD_NAME = "message";

    private final MessageSource clientErrorMsgSource;
    private final MessageSource serverErrorMsgSource;

    @ExceptionHandler(InvalidGrilleKeyException.class)
    public ResponseEntity<Object> handleInvalidGrille(InvalidGrilleKeyException ex, Locale locale) {
        final String message = clientErrorMsgSource.getMessage(
                INVALID_GRILLE, new Object[] { ex.getFieldName() }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CorruptedMessageException.class)
    public ResponseEntity<Object> handleCorruptedMessage(CorruptedMessageException ex, Locale locale) {
        final String message = clientErrorMsgSource.getMessage(
                CORRUPTED_MESSAGE, new Object[] { ex.getFieldLength() }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingRequestParameter(MissingServletRequestParameterException ex,
                                                         Locale locale) {
        final String message = clientErrorMsgSource.getMessage(
                MISSING_REQUEST_PARAMETER, new Object[] { ex.getParameterName(), ex.getParameterType() }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValid(MethodArgumentNotValidException ex,
                                                         Locale locale) {
        BindingResult bindingResult = ex.getBindingResult();
        String message = bindingResult.getAllErrors().stream()
                                      .map(objectError -> clientErrorMsgSource.getMessage(objectError, locale))
                                      .collect(Collectors.joining("; ", "", "."));
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                           Locale locale) {
        final String message = clientErrorMsgSource.getMessage(
                METHOD_NOT_ALLOWED, new Object[] {
                        ex.getMethod(), ex.getSupportedHttpMethods()
                }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, Locale locale) {
        final Class<?> requiredType = Objects.requireNonNull(ex.getRequiredType());
        final Object providedValue = ex.getValue();
        final String message = clientErrorMsgSource.getMessage(
                TYPE_MISMATCH, new Object[] { providedValue, requiredType }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableMessage(HttpMessageNotReadableException ex, Locale locale) {
        final String message = clientErrorMsgSource.getMessage(
                MESSAGE_NOT_READABLE, null, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonErrors(Exception ex, Locale locale) {
        log.error("catch generic error", ex);
        final String message = serverErrorMsgSource.getMessage(
                ERROR_NOT_DEFINED, new Object[] { ex.getMessage() }, locale
        );
        Map<String, Object> body = new HashMap<>();
        body.put(MESSAGE_FIELD_NAME, message);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
