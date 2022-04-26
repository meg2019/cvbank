package telran.b7a.cvbank.accounting.employer.dto;

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
public class EmployerUpdateDto {
	ApplicantDto applicantInfo;
	CompanyDto companyInfo;
}
