package telran.b7a.cvbank.accounting.employer.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployerAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424419231089846421L;

	public EmployerAlreadyExistsException(String email) {
		super("Employer with " + email + " already exists");
	}
}
