package telran.b7a.cvbank.cv.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CVDto {
	String cvId;
	String firstName;
	String lastName;
	String email;
	String phone;
	int verificationLevel;
	boolean isRelevant;
	String isRelocated;
	Integer salary;
	String address;
	String position;
	String preambule;
	Set<String> skills;
	List<ExperienceDto> experience;
	List<EducationDto> educations;
	OtherDto other;
	Set<String> links;
	Integer template;

}
