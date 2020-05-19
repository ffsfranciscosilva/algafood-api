package com.ffs.algafood.api.exception;

import com.ffs.algafood.domain.exception.base.BusinessException;
import com.ffs.algafood.domain.exception.base.EntityInUseException;
import com.ffs.algafood.domain.exception.base.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ffs.algafood.api.exception.ApiExceptionType.*;
import static org.springframework.http.HttpStatus.*;

/**
 *
 * @author francisco
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerNotFound(EntityNotFoundException ex, WebRequest request) {
        var apiException = new ApiException(ex.getMessage(), ENTITY_NOT_FOUND, NOT_FOUND, request);

        return this.handleExceptionInternal(ex, apiException, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handlerEntityInUseException(EntityInUseException ex, WebRequest request) {
        var apiException = new ApiException(ex.getMessage(), ENTITY_IN_USE, CONFLICT, request);

        return this.handleExceptionInternal(ex, apiException, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handlerBusinessException(BusinessException ex, WebRequest request) {
        var apiException = new ApiException(ex.getMessage(), ERROR_BUSINESS, BAD_REQUEST, request);

        return this.handleExceptionInternal(ex, apiException, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var detail = "The request body is invalid. Verify syntax errors.";
        var apiException = new ApiException(detail, INCOMPREHENSIBLE_MESSAGE, BAD_REQUEST, request);

        return this.handleExceptionInternal(ex, apiException, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
