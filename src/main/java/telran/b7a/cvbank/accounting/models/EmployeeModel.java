package telran.b7a.cvbank.accounting.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@EqualsAndHashCode(of = { "email" })
@Document(collection = "Employees")
public class EmployeeModel {
	@Id
	String email;
	String password;
	String firstName;
	String lastName;
	List<String> cv_id = new ArrayList<>();
}
