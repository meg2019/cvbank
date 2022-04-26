package telran.b7a.cvbank.accounting.employee.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 546747835521098361L;

	public EmployeeAlreadyExistsException(String email) {
		super("Employee " + email + " already exists");
	}

}
