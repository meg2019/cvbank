package telran.b7a.cvbank.cv.models;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
	String date;
	String company;
	String website;
	String address;
	Set<Project> projects;
	
}
