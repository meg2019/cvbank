package telran.b7a.cvbank.accounting.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeDto {
	String firstName;
	String lastName;
	
}
