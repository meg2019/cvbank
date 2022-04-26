package telran.b7a.cvbank.accounting.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Employers")
@ToString
public class EmployerModel {
	@Id
	String email;
	Applicant applicantInfo;
	Company companyInfo;
	String password;
	Map<String, Set<String>> cvCollections;
	
	public EmployerModel(Applicant applicantInfo, Company companyInfo, String password, String email) {
		this.email = email;
		this.applicantInfo = applicantInfo;
		this.companyInfo = companyInfo;
		this.password = password;
		this.cvCollections = new HashMap<>();
	}
	
	

}
