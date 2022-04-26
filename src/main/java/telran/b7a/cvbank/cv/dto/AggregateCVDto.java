package telran.b7a.cvbank.cv.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AggregateCVDto {
	String position;
	Set<String> skills;
	Integer minSalary;
	Integer maxSalary;
	String location;
	Integer distance;
	boolean relocated;
	int verifiedLevel;	
}
