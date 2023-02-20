package pl.empik.recruitment.exception;

public class ApplicationTechnicalException extends RuntimeException
{
    public ApplicationTechnicalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}