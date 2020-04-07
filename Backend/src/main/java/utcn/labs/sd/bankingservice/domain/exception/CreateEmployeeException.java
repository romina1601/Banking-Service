package utcn.labs.sd.bankingservice.domain.exception;

public class CreateEmployeeException extends Exception{

    private static final long serialVersionUID = -9054652251973166043L;

    public CreateEmployeeException(String message) {
        super(message);
    }
}
