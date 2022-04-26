package telran.b7a.cvbank.cv.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.Query;

import telran.b7a.cvbank.cv.dto.AggregateCVDto;
import telran.b7a.cvbank.cv.dto.CVDto;
import telran.b7a.cvbank.cv.dto.FieldsForHideDto;
import telran.b7a.cvbank.cv.dto.NewCVDto;

public interface CVService {
	CVDto addCV(NewCVDto newCV, String login);

	CVDto updateCV(String cvId, NewCVDto changingDataCV);

	void removeCV(String cvId, String login);

	CVDto anonymiseCV(String cvId, Set<String> fieldsForHide);

	List<CVDto> aggregateCVbyParam(AggregateCVDto param);

	List<CVDto> getCVs(Set<String> cvsId);

	List<CVDto> getCVsBySalary(int minSalary, int maxSalary);
	
	
}
