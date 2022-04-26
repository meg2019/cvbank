package telran.b7a.cvbank.accounting.employee.service;

import telran.b7a.cvbank.accounting.employee.dto.EmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.RegisterEmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.UpdateEmployeeDto;

public interface EmployeeAccountingService {
	EmployeeDto registerEmployee(RegisterEmployeeDto registerEmployeeDto);

	EmployeeDto loginEmployee(String email);

	EmployeeDto updateEmployee(UpdateEmployeeDto updateEmployeeDto, String employeeId); // employeeId -> email

	void deleteEmployee(String employeeId); // employeeId -> email

	EmployeeDto findEmployee(String employeeId); // employeeId -> email

	EmployeeDto AddCV(String employeeId, String cvId); // employeeId -> email

	EmployeeDto deleteCV(String employeeId, String cvId); // employeeId -> email

	EmployeeDto changeLogin(String email, String newLogin); // newLogin -> email
	
	void changePassword(String email, String newPassword);
}