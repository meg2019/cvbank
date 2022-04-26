package telran.b7a.cvbank.cv.dao;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.b7a.cvbank.cv.models.CV;

public interface CVMongoRepository extends MongoRepository<CV, String> {
	Stream<CV> findBycvIdIn(Set<String> cvsId);
	
	@Query("{'salary': {$gte: ?0, $lt: $1}}")
	Stream<CV> findCVsBySalary(int minSalary, int maxSalary);
}
