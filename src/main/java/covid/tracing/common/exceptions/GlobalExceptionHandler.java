package covid.tracing.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ErrorDetails createErrorDetail(HttpStatus status, String exceptionMessage, WebRequest request) {
        return new ErrorDetails(status, exceptionMessage, request.getDescription(false), LocalDateTime.now());
    }

    // handle notFound UserAuthentication exception
    @ExceptionHandler(NotFoundUserAuthenticationException.class)
    public ResponseEntity<?> notFoundAuthenticationHandling(NotFoundUserAuthenticationException exception, WebRequest request) {
        exception.printStackTrace();
        ErrorDetails errorDetails = this.createErrorDetail(HttpStatus.NOT_FOUND, exception.getMessage(), request);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handle nested email exception
    @ExceptionHandler(NestedEmailException.class)
    public ResponseEntity<?> nestedEmailHandling(NestedEmailException exception, WebRequest request) {
        exception.printStackTrace();
        ErrorDetails errorDetails = this.createErrorDetail(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        exception.printStackTrace();
        ErrorDetails errorDetails = this.createErrorDetail(HttpStatus.NOT_FOUND, exception.getMessage(), request);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
