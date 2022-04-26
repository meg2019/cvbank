package telran.b7a.cvbank.accounting.employer.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.b7a.cvbank.accounting.models.EmployerModel;

public interface EmployerAccountingMongoRepository extends MongoRepository<EmployerModel, String> {
	boolean existsByCompanyInfoNameIgnoringCase(String companyName);

	boolean existsByApplicantInfoEmailIgnoringCase(String email);

	EmployerModel findByApplicantInfoEmailIgnoringCase(String email);

	Stream<EmployerModel> findByCompanyInfoNameIgnoringCase(String company);
	
	
}
