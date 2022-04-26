package telran.b7a.cvbank.cv.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CVs")
@EqualsAndHashCode(of = "cvId")
public class CV {
	@Id
	ObjectId cvId;
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
	List<Experience> experience;
	List<Education> educations;
	Other other;
	Set<String> links;
	int template;
	
	LocalDate dateOfCreation = LocalDate.now();
	Set<String> hiddenFiels = new HashSet<>();
	
}
