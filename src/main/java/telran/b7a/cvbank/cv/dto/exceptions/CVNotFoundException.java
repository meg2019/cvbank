package telran.b7a.cvbank.cv.dto.exceptions;

public class CVNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7219935359972312217L;

	public CVNotFoundException(String cvId) {
		super("CV with id: " + cvId + " not found");
	}
}
