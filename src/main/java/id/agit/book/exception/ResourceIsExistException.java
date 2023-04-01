package id.agit.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceIsExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceIsExistException(String message){
    	super(message);
    }
}
