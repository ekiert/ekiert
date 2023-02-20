package pl.empik.recruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IncorrectDataException extends RuntimeException
{
    public IncorrectDataException(String message)
    {
        super(message);
    }
}