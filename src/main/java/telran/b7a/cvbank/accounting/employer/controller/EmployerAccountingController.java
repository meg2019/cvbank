package telran.b7a.cvbank.accounting.employer.controller;

import java.util.List;

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

import telran.b7a.cvbank.accounting.employer.dto.AddCVDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerFindDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerRegisterDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerUpdateDto;
import telran.b7a.cvbank.accounting.employer.service.EmployerAccountingService;

@RestController
@RequestMapping("/cvbank/employer")
@CrossOrigin(origins = "*",
methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT},
allowedHeaders = "*", exposedHeaders = "*")

public class EmployerAccountingController {
	EmployerAccountingService employerAccountingService;
	
	@Autowired
	public EmployerAccountingController(EmployerAccountingService employerAccountingService) {
		this.employerAccountingService = employerAccountingService;
	}
	
	@PostMapping("/signup")
	public EmployerDto registerEmployer(@RequestBody EmployerRegisterDto newEmployer ) {
		return employerAccountingService.registerEmployer(newEmployer);
	}
	
	@PostMapping("/signin")
	public EmployerDto loginEmployer(Authentication authentication) {
		return employerAccountingService.loginEmployer(authentication.getName());
	}
	
	@PutMapping("/{employerId}")
	public EmployerDto updateEmployer(Authentication authentication, @RequestBody EmployerUpdateDto newData) {
		return employerAccountingService.updateEmployer(authentication.getName(), newData);
		
	}
	
	@PutMapping("/login")
	public EmployerDto changeLogin(@RequestHeader("X-Login") String newLogin, Authentication authentication) {
		return employerAccountingService.changeLogin(authentication.getName(), newLogin);
		
	}
	
	@PutMapping("/collection/{collectionName}")
	public AddCVDto addCVCollection(@PathVariable String collectionName, Authentication authentication) {
		return employerAccountingService.addCVCollection(authentication.getName(), collectionName);
		
	}
	
	@PutMapping("/collection/{collectionName}/{cvId}") 
	public AddCVDto addCV2Collection(Authentication authentication, @PathVariable String collectionName, @PathVariable String cvId ) {
		return employerAccountingService.addCV2Collection(authentication.getName(), collectionName, cvId);
	}
	
	@PutMapping("/pass")
	public EmployerDto changePassword(@RequestHeader("X-Password") String newPassword, Authentication authentication) {
		return employerAccountingService.changePassword(authentication.getName(), newPassword);
	}
	
	@DeleteMapping("/{employerId}")
	public void deleteEmployer(@PathVariable String employerId) {
		employerAccountingService.removeEmployer(employerId);
	}

	@GetMapping("/company/{companyName}")
	public List<EmployerFindDto> findEmployerByName(@PathVariable String companyName) {
		return employerAccountingService.getEmployerByName(companyName);
		
	}
}
