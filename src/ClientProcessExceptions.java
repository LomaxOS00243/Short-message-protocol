
public class ClientProcessExceptions extends Exception{
    public ClientProcessExceptions(String message) {
        super(message);
    }

    public ClientProcessExceptions(String message, Throwable cause) {
        super(message, cause);

    }

    public ClientProcessExceptions(Throwable cause) {
        super(cause);
    }

}
