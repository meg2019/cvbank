package telran.b7a.cvbank.accounting.employee.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.b7a.cvbank.accounting.models.EmployeeModel;

public interface EmployeeAccountingMongoRepository extends MongoRepository<EmployeeModel, String> {

}
