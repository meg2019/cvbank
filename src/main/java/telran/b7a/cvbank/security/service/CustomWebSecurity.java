package telran.b7a.cvbank.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.cvbank.accounting.employee.dao.EmployeeAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employee.dto.exceptions.EmployeeNotFoundException;
import telran.b7a.cvbank.accounting.models.EmployeeModel;

@Service("customSecurity")
public class CustomWebSecurity {
	EmployeeAccountingMongoRepository employeeAccountingMongoRepository;
	
	@Autowired
	public CustomWebSecurity(EmployeeAccountingMongoRepository employeeAccountingMongoRepository) {
		this.employeeAccountingMongoRepository = employeeAccountingMongoRepository;
	}
	
	public boolean checkCVAuthority(String cvId, String email) {
		EmployeeModel employee = employeeAccountingMongoRepository.findById(email)
				.orElseThrow(() -> new EmployeeNotFoundException(email));
		return employee.getCv_id().contains(cvId);
		
	}
	
}
