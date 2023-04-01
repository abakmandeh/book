package id.agit.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BookIsBorrowedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BookIsBorrowedException(String message){
    	super(message);
    }
}
