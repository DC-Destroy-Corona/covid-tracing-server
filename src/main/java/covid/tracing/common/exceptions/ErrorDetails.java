package covid.tracing.common.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorDetails {

    private HttpStatus status;
    private String message;
    private String details;
    private LocalDateTime datetime;

    public ErrorDetails(HttpStatus status, String message, String details, LocalDateTime datetime) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.datetime = datetime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}