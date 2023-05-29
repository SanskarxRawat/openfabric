package ai.openfabric.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WorkerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Customize the error response based on the exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid request: " + ex.getMessage());
    }

    @ExceptionHandler(WorkerNotFoundException.class)
    public ResponseEntity<String> handleWorkerNotFoundException(WorkerNotFoundException ex) {
        // Customize the error response based on the exception
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Worker not found: " + ex.getMessage());
    }

    // Global exception handler for any unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Log the error or perform any necessary error handling actions
        // Customize the error response based on the exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}
