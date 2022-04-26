package telran.b7a.cvbank.accounting.employer.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@ToString
public class EmployerDto {
	
	String email;
	ApplicantDto applicantInfo;
	CompanyDto companyInfo;
	Map<String, List<String>> cvCollections;
	
}
