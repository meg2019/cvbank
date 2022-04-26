package telran.b7a.cvbank.cv.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.bson.BsonDbPointer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import telran.b7a.cvbank.accounting.employee.dao.EmployeeAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employee.dto.exceptions.EmployeeNotFoundException;
import telran.b7a.cvbank.accounting.models.EmployeeModel;
import telran.b7a.cvbank.cv.dao.CVMongoRepository;
import telran.b7a.cvbank.cv.dto.AggregateCVDto;
import telran.b7a.cvbank.cv.dto.CVDto;
import telran.b7a.cvbank.cv.dto.NewCVDto;
import telran.b7a.cvbank.cv.dto.exceptions.CVNotFoundException;
import telran.b7a.cvbank.cv.models.CV;

@Service
public class CVServiceImpl implements CVService {

	CVMongoRepository cvRepository;
	EmployeeAccountingMongoRepository employeeRepository;
	ModelMapper modelMapper;
	MongoTemplate mongoTemplate;

	@Autowired
	public CVServiceImpl(CVMongoRepository cvRepository, ModelMapper modelMapper,
			EmployeeAccountingMongoRepository employeeRepository, MongoTemplate mongoTemplate) {
		this.cvRepository = cvRepository;
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public CVDto addCV(NewCVDto newCV, String login) {
		CV cv = modelMapper.map(newCV, CV.class);
		EmployeeModel employee = employeeRepository.findById(login)
				.orElseThrow(() -> new EmployeeNotFoundException(login));
		String cvId = cvRepository.save(cv).getCvId().toHexString();
		employee.getCv_id().add(cvId);
		employeeRepository.save(employee);
		return modelMapper.map(cv, CVDto.class);
	}

	@Override
	public CVDto updateCV(String cvId, NewCVDto changingDataCV) {
		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
		CV newCV = modelMapper.map(changingDataCV, CV.class);
		newCV.setCvId(cv.getCvId());
		cvRepository.save(newCV);
		return modelMapper.map(newCV, CVDto.class);
	}

	@Override
	public void removeCV(String cvId, String email) {
		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
		EmployeeModel employee = employeeRepository.findById(email)
				.orElseThrow(() -> new EmployeeNotFoundException(email));
		employee.getCv_id().remove(cvId);
		employeeRepository.save(employee);
		cvRepository.delete(cv);
	}

	@Override
	public CVDto anonymiseCV(String cvId, Set<String> fields2Hide) {
		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
		cv.setHiddenFiels(fields2Hide);
		CVDto returnedCV = modelMapper.map(cv, CVDto.class);
		returnedCV = blackOutFields(returnedCV, fields2Hide);
		return returnedCV;
	}

	private CVDto blackOutFields(CVDto returnedCV, Set<String> fields2Hide) {
		Class<? extends CVDto> myClass = returnedCV.getClass();
		fields2Hide.stream().forEach(FieldName -> {
			Field field = null;
			try {
				field = myClass.getDeclaredField(FieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			field.setAccessible(true);
			ReflectionUtils.setField(field, returnedCV, "******");
//			try {
//				field.set(returnedCV, "**********");
//			} catch (IllegalArgumentException | IllegalAccessException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		});
		return returnedCV;
	}

	@Override
	public List<CVDto> aggregateCVbyParam(AggregateCVDto param) {
		Query query = new Query();
		if (param.getSkills() != null) {
			query.addCriteria(Criteria.where("skills").all(param.getSkills()));
		}
		if (param.getMaxSalary() != 0) {
			query.addCriteria(Criteria.where("salary").gte(param.getMinSalary()).lt(param.getMaxSalary()));
		}
		if (param.isRelocated()) {
			query.addCriteria(Criteria.where("isRelocated").is(true));
		} else {
			query.addCriteria(Criteria.where("isRelocated").is(false));
		}
		if (param.getVerifiedLevel() != 0) {
			query.addCriteria(Criteria.where("verificationLevel").is(param.getVerifiedLevel()));
		}
		System.err.println(query);
		List<CV> cvs = mongoTemplate.find(query, CV.class);
		return cvs.stream().map(cv -> modelMapper.map(cv, CVDto.class)).toList();
	}

	@Override
	public List<CVDto> getCVs(Set<String> cvsId) {
		return cvRepository.findBycvIdIn(cvsId).map(cv -> modelMapper.map(cv, CVDto.class)).toList();
	}

	@Override
	public List<CVDto> getCVsBySalary(int minSalary, int maxSalary) {
		return cvRepository.findCVsBySalary(minSalary, maxSalary)
					.map(cv -> modelMapper.map(cv, CVDto.class)).toList();
	}

	

//	@Override
//	public CVDto anonymiseCV(String cvId, Set<String> hideFields) {
//		CV cv = cvRepository.findById(cvId).orElseThrow(() -> new CVNotFoundException(cvId));
//		CVDto completeCV = modelMapper.map(cv, CVDto.class);
//		hideFields.stream().forEach(field -> removeField(completeCV, field));
//		return completeCV;
//	}
//
//	private CVDto removeField(CVDto CV, String field) {
//		if (field.equalsIgnoreCase("firstName")) {
//			CV.setFirstName(null);
//		}
//		if (field.equalsIgnoreCase("lastName")) {
//			CV.setLastName(null);
//		}
//		if (field.equalsIgnoreCase("phone")) {
//			CV.setPhone(null);
//		}
//		if (field.equalsIgnoreCase("experience")) {
//			CV.setExperience(null);
//		}
//		if (field.equalsIgnoreCase("links")) {List
//			CV.setLinks(null);
//		}
//		return CV;
//
//	}

}
