package uz.pdp.exception;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found.")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with id - " + id);
    }
}
