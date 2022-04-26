package telran.b7a.cvbank.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.b7a.cvbank.accounting.employee.dao.EmployeeAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employer.dao.EmployerAccountingMongoRepository;
import telran.b7a.cvbank.accounting.models.EmployeeModel;
import telran.b7a.cvbank.accounting.models.EmployerModel;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	EmployeeAccountingMongoRepository employeeRepository;
	EmployerAccountingMongoRepository employerRepository;
	
	@Autowired
	public UserDetailsServiceImpl(EmployeeAccountingMongoRepository employeeRepository,
			EmployerAccountingMongoRepository employerRepository) {
		this.employeeRepository = employeeRepository;
		this.employerRepository = employerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		EmployeeModel employeeAccount = employeeRepository.findById(username).orElse(null);
		if (employeeAccount != null) {
			user = new User(username, employeeAccount.getPassword(), AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE"));
		} else {
			EmployerModel employerAccount = employerRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
			user = new User(username, employerAccount.getPassword(), AuthorityUtils.createAuthorityList("ROLE_EMPLOYER"));
		}
		System.out.println(user);
		return user;
	}

}
