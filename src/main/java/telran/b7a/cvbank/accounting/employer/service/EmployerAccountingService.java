package telran.b7a.cvbank.accounting.employer.service;

import java.util.List;

import telran.b7a.cvbank.accounting.employer.dto.AddCVDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerFindDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerRegisterDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerUpdateDto;

public interface EmployerAccountingService {
	EmployerDto registerEmployer(EmployerRegisterDto newEmployer);

	EmployerDto loginEmployer(String login);

	EmployerDto updateEmployer(String email, EmployerUpdateDto newData);

	void removeEmployer(String email);

	List<EmployerFindDto> getEmployerByName(String companyName);
	
	EmployerDto changeLogin(String email, String newLogin);
	
	EmployerDto changePassword(String email, String newPassword);
	
	AddCVDto addCVCollection(String email, String collectionName);
	
	AddCVDto addCV2Collection(String email, String collectionName, String cvId);
	
	void removeCvFromCollection(String email, String collectionName, String cvId);
	
}
