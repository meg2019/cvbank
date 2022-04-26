package telran.b7a.cvbank.accounting.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.cvbank.accounting.employee.dto.EmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.RegisterEmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.UpdateEmployeeDto;
import telran.b7a.cvbank.accounting.employee.service.EmployeeAccountingService;

@RestController
@RequestMapping("/cvbank/employee")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
		RequestMethod.POST, RequestMethod.PUT }, allowedHeaders = "*", exposedHeaders = "*")
public class EmployeeAccountingController {
	EmployeeAccountingService employeeAccountingService;

	@Autowired
	public EmployeeAccountingController(EmployeeAccountingService employeeAccountingService) {
		this.employeeAccountingService = employeeAccountingService;
	}

	@PostMapping("/signup")
	public EmployeeDto registerEmployee(@RequestBody RegisterEmployeeDto newEmployee) {
		return employeeAccountingService.registerEmployee(newEmployee);
	}

	@PostMapping("/signin")
	public EmployeeDto loginEmployee(Authentication authentication) {
		return employeeAccountingService.loginEmployee(authentication.getName());
	}

	@PutMapping("/{email}")
	public EmployeeDto updateEmployee(@RequestBody UpdateEmployeeDto employeeDate, @PathVariable String email) {
		return employeeAccountingService.updateEmployee(employeeDate, email);
	}

	@DeleteMapping("/{email}")
	public void deleteEmployee(@PathVariable String email) {
		employeeAccountingService.deleteEmployee(email);
	}

	@GetMapping("/{email}")
	public EmployeeDto findEmployee(@PathVariable String email) {
		return employeeAccountingService.loginEmployee(email);

	}

	@PutMapping("/{email}/{cvId}")
	public EmployeeDto addCV(@PathVariable String email, @PathVariable String cvId) {
		return employeeAccountingService.AddCV(email, cvId);

	}

	@DeleteMapping("/{email}/{cvId}")
	public EmployeeDto deleteCV(@PathVariable String email, @PathVariable String cvId) {
		return employeeAccountingService.deleteCV(email, cvId);
	}

	@PutMapping("/login")
	public EmployeeDto changeLogin(@RequestHeader("X-Login") String newLogin, Authentication authentication) {
		return employeeAccountingService.changeLogin(authentication.getName(), newLogin);
	}

	@PutMapping("/pass")
	public void changePassword(@RequestHeader("X-Password") String newPassword, Authentication authentication) {
		employeeAccountingService.changePassword(authentication.getName(), newPassword);
		
	}
	
//	private String parseTokenForLogin(String token) {
//		String credentials = token.split(" ")[1];
//		byte[] login = Base64.getDecoder().decode(credentials);
//		return new String(login).split(":")[0];
//	}

}
