package telran.b7a.cvbank.cv.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.cvbank.cv.dto.AggregateCVDto;
import telran.b7a.cvbank.cv.dto.CVDto;
import telran.b7a.cvbank.cv.dto.FieldsForHideDto;
import telran.b7a.cvbank.cv.dto.NewCVDto;
import telran.b7a.cvbank.cv.service.CVService;

@RestController
@RequestMapping("/cvbank/cv")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
		RequestMethod.POST, RequestMethod.PUT }, allowedHeaders = "*", exposedHeaders = "*")
public class CVController {

	CVService cvService;

	@Autowired
	public CVController(CVService cvService) {
		this.cvService = cvService;
	}

	@PostMapping
	public CVDto addCV(@RequestBody NewCVDto newCV, Authentication authentication) {
		return cvService.addCV(newCV, authentication.getName());
	}

	@PutMapping("/{cvId}")
	public CVDto updateCV(@PathVariable String cvId, NewCVDto updatedCV) {
		return cvService.updateCV(cvId, updatedCV);
	}

	@DeleteMapping("/{cvId}")
	public void removeCV(@PathVariable String cvId, Authentication authentication) {
		cvService.removeCV(cvId, authentication.getName());
	}

	@PutMapping("/anonymizer/{cvId}")
	public CVDto anonymiseCV(@PathVariable String cvId, @RequestBody Set<String> fieldsForHide) {
		return cvService.anonymiseCV(cvId, fieldsForHide);
	}

	@PostMapping("/cvs")
	public List<CVDto> getArrCVsbyIDs(@RequestBody Set<String> IDs) {
		return cvService.getCVs(IDs);
	}

	@GetMapping("/cvs/aggregate")
	public List<CVDto> findAggregateCVs(@RequestBody AggregateCVDto aggregateParam) {
		return cvService.aggregateCVbyParam(aggregateParam);

	}

	@GetMapping("/cvs/salary/{minSalary}/{maxSalary}")
	public List<CVDto> findCVsBySalary(@PathVariable int minSalary, @PathVariable int maxSalary) {
		return cvService.getCVsBySalary(minSalary, maxSalary);
		
	}

}
