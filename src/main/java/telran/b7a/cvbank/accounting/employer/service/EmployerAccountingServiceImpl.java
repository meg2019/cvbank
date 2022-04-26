package telran.b7a.cvbank.accounting.employer.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.b7a.cvbank.accounting.employer.dao.EmployerAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employer.dto.AddCVDto;
import telran.b7a.cvbank.accounting.employer.dto.ApplicantDto;
import telran.b7a.cvbank.accounting.employer.dto.CompanyDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerFindDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerRegisterDto;
import telran.b7a.cvbank.accounting.employer.dto.EmployerUpdateDto;
import telran.b7a.cvbank.accounting.employer.dto.exceptions.EmployerAlreadyExistsException;
import telran.b7a.cvbank.accounting.employer.dto.exceptions.EmployerNotFoundException;
import telran.b7a.cvbank.accounting.models.Applicant;
import telran.b7a.cvbank.accounting.models.Company;
import telran.b7a.cvbank.accounting.models.EmployerModel;
import telran.b7a.cvbank.cv.dao.CVMongoRepository;
import telran.b7a.cvbank.cv.dto.exceptions.CVNotFoundException;
import telran.b7a.cvbank.cv.models.CV;

@Service
public class EmployerAccountingServiceImpl implements EmployerAccountingService {

	EmployerAccountingMongoRepository employersRepository;
	CVMongoRepository cvRepository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public EmployerAccountingServiceImpl(EmployerAccountingMongoRepository employersRepository,
			CVMongoRepository cvRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.employersRepository = employersRepository;
		this.cvRepository = cvRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	
	@Override
	public EmployerDto loginEmployer(String email) {
		System.out.println(email);
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		if (employer.equals(null)) {
			throw new EmployerNotFoundException(email);
		}
		return modelMapper.map(employer, EmployerDto.class);

	}

	@Override
	public EmployerDto updateEmployer(String email, EmployerUpdateDto newData) {
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		Applicant applicantInfo = modelMapper.map(newData.getApplicantInfo(), Applicant.class);
		Company companyInfo = modelMapper.map(newData.getCompanyInfo(), Company.class);
		employer.setApplicantInfo(applicantInfo);
		employer.setCompanyInfo(companyInfo);
		employersRepository.save(employer);
		return modelMapper.map(employer, EmployerDto.class);
	}

	@Override
	public void removeEmployer(String email) {
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		employersRepository.delete(employer);

	}

	@Override
	public EmployerDto registerEmployer(EmployerRegisterDto newEmployer) {
		if (employersRepository.existsById(newEmployer.getEmail())) {
			throw new EmployerAlreadyExistsException(newEmployer.getEmail());
		}
		Applicant newApplicant = modelMapper.map(newEmployer.getApplicantInfo(), Applicant.class);
		Company newCompany = modelMapper.map(newEmployer.getCompanyInfo(), Company.class);
		EmployerModel employer = new EmployerModel(newApplicant, newCompany,
				passwordEncoder.encode(newEmployer.getPassword()), newEmployer.getEmail());
		employersRepository.save(employer);
		return modelMapper.map(employer, EmployerDto.class);
	}

	@Override
	public List<EmployerFindDto> getEmployerByName(String companyName) {
		Stream<EmployerModel> employers = employersRepository.findByCompanyInfoNameIgnoringCase(companyName);
		if (employers == null) {
			throw new EmployerNotFoundException(companyName);
		}
		return employers.map(empl -> modelMapper.map(empl, EmployerFindDto.class)).collect(Collectors.toList());
	}

	@Override
	public EmployerDto changeLogin(String email, String newLogin) {
		if (employersRepository.existsById(newLogin)) {
			throw new EmployerAlreadyExistsException(email);
		}
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		employersRepository.delete(employer);
		employer.setEmail(newLogin);
		employersRepository.save(employer);
		return modelMapper.map(employer, EmployerDto.class);
	}

	@Override
	public EmployerDto changePassword(String email, String newPassword) {
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		employer.setPassword(passwordEncoder.encode(newPassword));
		employersRepository.save(employer);
		return modelMapper.map(employer, EmployerDto.class);
	}

	@Override
	public AddCVDto addCVCollection(String email, String collectionName) {
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		employer.getCvCollections().put(collectionName, new HashSet<>());
		employersRepository.save(employer);
		return modelMapper.map(employer, AddCVDto.class);
	}

	@Override
	public AddCVDto addCV2Collection(String email, String collectionName, String cvId) {
		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		if (employer.getCvCollections().get(collectionName) == null) {
			employer.getCvCollections().put(collectionName, new HashSet<>());
		}
		employer.getCvCollections().get(collectionName).add(cv.getCvId().toHexString());
		employersRepository.save(employer);
		return modelMapper.map(employer, AddCVDto.class);
	}

	@Override
	public void removeCvFromCollection(String email, String collectionName, String cvId) {
		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
		EmployerModel employer = employersRepository.findById(email).orElseThrow(() -> new EmployerNotFoundException(email));
		employer.getCvCollections().get(collectionName).remove(cv.getCvId().toHexString());
	}
	
	private String parseTokenForLogin(String token) {
		String credentials = token.split(" ")[1];
		byte[] login = Base64.getDecoder().decode(credentials);
		return new String(login).split(":")[0];
	}

}
