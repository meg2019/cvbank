package telran.b7a.cvbank.accounting.employee.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098737142266154089L;
		public EmployeeNotFoundException (String email) {
			super("Employee "+ email + " not found");
		}
}
