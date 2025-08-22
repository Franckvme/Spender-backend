package Spender.Spender_backend.shared.exception.handler;


import Spender.Spender_backend.shared.dto.response.ErrorResponse;
import Spender.Spender_backend.shared.exception.DuplicateResourceException;
import Spender.Spender_backend.shared.exception.ResourceNotFoundException;
import Spender.Spender_backend.shared.exception.base.SpenderException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @NonNull
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
    @NonNull MethodArgumentNotValidException ex,
    @NonNull HttpHeaders headers,
    @NonNull HttpStatusCode status,
    @NonNull WebRequest request
  ) {
    var errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(field -> new ErrorResponse.ErrorDetails(field.getField(), Optional.of(field.getDefaultMessage()).orElse("")))
      .toList();
    return logErrorAndCreateResponseEntity(ex, HttpStatus.BAD_REQUEST, "Not valid arguments", request, errors);
  }

  @Override
  public ResponseEntity<Object> handleMissingPathVariable(
    @NonNull MissingPathVariableException ex,
    @NonNull HttpHeaders headers,
    @NonNull HttpStatusCode status,
    @NonNull WebRequest request
  ) {
    var errors = List.of(
      new ErrorResponse.ErrorDetails(ex.getVariableName(), ex.getMessage())
    );
    return logErrorAndCreateResponseEntity(ex, HttpStatus.BAD_REQUEST, "Not valid parameters", request, errors);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
    @NonNull MethodArgumentTypeMismatchException ex,
    @NonNull WebRequest request
  ) {
    var errors = List.of(
      new ErrorResponse.ErrorDetails(ex.getName(), String.valueOf(ex.getValue()))
    );
    return logErrorAndCreateResponseEntity(ex, HttpStatus.BAD_REQUEST, "Invalid parameter value", request, errors);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
    var validationErrors = ex.getConstraintViolations().stream()
      .map(ApiExceptionHandler::buildErrorDetail)
      .toList();
    return logErrorAndCreateResponseEntity(ex, HttpStatus.BAD_REQUEST, "Some fields not match expected values", request, validationErrors);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
    var errors = List.of(new ErrorResponse.ErrorDetails("message", ex.getLocalizedMessage()));
    return logErrorAndCreateResponseEntity(ex, HttpStatus.FORBIDDEN, ex.getMessage(), request, errors);
  }

  @ExceptionHandler({DuplicateResourceException.class})
  public ResponseEntity<Object> handleDuplicateResource(DuplicateResourceException ex, WebRequest request) {
    var errors = List.of(new ErrorResponse.ErrorDetails(ex.getField(), ex.getValue()));
    return logErrorAndCreateResponseEntity(ex, ex.getHttpStatus(), ex.getMessage(), request, errors);
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
    var errors = List.of(new ErrorResponse.ErrorDetails(ex.getField(), ex.getValue()));
    return logErrorAndCreateResponseEntity(ex, ex.getHttpStatus(), ex.getMessage(), request, errors);
  }

  @ExceptionHandler({SpenderException.class})
  public ResponseEntity<Object> handleBusinessAndTechnicalException(SpenderException ex, WebRequest request) {
    return logErrorAndCreateResponseEntity(ex, ex.getHttpStatus(), ex.getMessage(), request, null);
  }

  /**
   * Handle all other exceptions - 500 Internal Server Error
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
    ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
    final HttpStatus status = responseStatus != null
      ? responseStatus.value()
      : HttpStatus.INTERNAL_SERVER_ERROR;

    return logErrorAndCreateResponseEntity(ex, status, "An unexpected error occurred while processing request", request, null);
  }

  private ResponseEntity<Object> logErrorAndCreateResponseEntity(
    Exception ex,
    HttpStatus status,
    String message,
    WebRequest request,
    List<ErrorResponse.ErrorDetails> errors
  ) {
    int statusCode = status.value();
    String path = request.getDescription(true);

    var response = new ErrorResponse(statusCode, message, errors);

    logger.error("Error: {}, Path: {}", message, path, ex);

    return ResponseEntity.status(status).body(response);
  }

  private static ErrorResponse.ErrorDetails buildErrorDetail(ConstraintViolation<?> violation) {
    return new ErrorResponse.ErrorDetails(violation.getPropertyPath().toString(), violation.getMessage());
  }
}
