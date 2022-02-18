package qadr.springsecurity.jwt.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<CustomException> customHandler(ApiRequestException exception){
        HttpStatus status = exception.getStatus();
        CustomException customException = new CustomException(
                exception.getMessage(),
                status.toString(),
                LocalDateTime.now());
        return new ResponseEntity<CustomException>(customException, status);
    }

}

@AllArgsConstructor
class CustomException {
    public final String message;
    public final String status;
    public final LocalDateTime timestamp;

}

