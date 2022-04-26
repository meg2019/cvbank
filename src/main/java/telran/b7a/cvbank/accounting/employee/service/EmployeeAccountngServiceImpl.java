package telran.b7a.cvbank.accounting.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.b7a.cvbank.accounting.employee.dao.EmployeeAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employee.dto.EmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.RegisterEmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.UpdateEmployeeDto;
import telran.b7a.cvbank.accounting.employee.dto.exceptions.EmployeeAlreadyExistsException;
import telran.b7a.cvbank.accounting.employee.dto.exceptions.EmployeeNotFoundException;
import telran.b7a.cvbank.accounting.models.EmployeeModel;
import telran.b7a.cvbank.cv.dto.exceptions.CVNotFoundException;


@Service
public class EmployeeAccountngServiceImpl implements EmployeeAccountingService {
	
	EmployeeAccountingMongoRepository repository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;
	
	
	
	@Autowired
	public EmployeeAccountngServiceImpl(EmployeeAccountingMongoRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
		
	@Override
	public EmployeeDto registerEmployee(RegisterEmployeeDto newEmployee) {
		if (repository.existsById(newEmployee.getEmail())) {
			throw new EmployeeAlreadyExistsException(newEmployee.getEmail());
		}
		EmployeeModel employee = modelMapper.map(newEmployee, EmployeeModel.class);
		String password = passwordEncoder.encode(newEmployee.getPassword());
		employee.setPassword(password);
		repository.save(employee);
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto loginEmployee(String email) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto updateEmployee(UpdateEmployeeDto employeeForUpdate, String employeeId) {
		EmployeeModel employee = repository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		employee.setFirstName(employeeForUpdate.getFirstName());
		employee.setLastName(employeeForUpdate.getLastName());
		repository.save(employee);
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public void deleteEmployee(String email) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		repository.delete(employee);

	}

	@Override
	public EmployeeDto findEmployee(String email) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto AddCV(String email, String cvId) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		System.out.println(email);
		System.out.println(cvId);
		employee.getCv_id().add(cvId);
		repository.save(employee);
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto deleteCV(String email, String cvId) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		if (employee.getCv_id().contains(cvId)) {
			employee.getCv_id().remove(cvId);
		} else {
			throw new CVNotFoundException(cvId);
		}
		repository.save(employee);
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public EmployeeDto changeLogin(String email, String newLogin) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		employee.setEmail(newLogin);
		repository.deleteById(email);
		repository.save(employee);
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public void changePassword(String email, String newPassword) {
		EmployeeModel employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
		String password = passwordEncoder.encode(newPassword);
		employee.setPassword(password);
		repository.save(employee);
	}

}
