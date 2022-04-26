package telran.b7a.cvbank.accounting.employer.dto.exceptions;

public class EmployerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1754265926091088827L;
	
	public EmployerNotFoundException(String email) {
		super("Employer with id " + email + " not found");
	}
}
