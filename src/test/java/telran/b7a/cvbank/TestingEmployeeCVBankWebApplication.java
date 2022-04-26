package telran.b7a.cvbank;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.qos.logback.core.net.ObjectWriter;
import telran.b7a.cvbank.accounting.employee.dao.EmployeeAccountingMongoRepository;
import telran.b7a.cvbank.accounting.employee.dto.RegisterEmployeeDto;
import telran.b7a.cvbank.accounting.employee.service.EmployeeAccountingService;
import telran.b7a.cvbank.accounting.models.EmployeeModel;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TestingEmployeeCVBankWebApplication {
	
	private static final String BASE_URL_EMPLOYEE = "/cvbank/employee";
	
	EmployeeAccountingMongoRepository employeeAccountingMongoRepository;
	EmployeeAccountingService employeeAccountngService;
	ModelMapper modelMapper;
	MockMvc mockMvc;
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Autowired
	public TestingEmployeeCVBankWebApplication(EmployeeAccountingMongoRepository employeeAccountingMongoRepository,
			EmployeeAccountingService employeeAccountngService, ModelMapper modelMapper, MockMvc mockMvc) {
		this.employeeAccountingMongoRepository = employeeAccountingMongoRepository;
		this.employeeAccountngService = employeeAccountngService;
		this.modelMapper = modelMapper;
		this.mockMvc = mockMvc;
	}
	
	RegisterEmployeeDto registerEmployeeDto = RegisterEmployeeDto.builder()
			.email("test@springtest.com")
			.firstName("John")
			.lastName("Dow")
			.password("000000")
			.build();
	
	@Test
	@Order(1)
	public void testingAddEmployee() throws Exception {
		String json = convertToJson(registerEmployeeDto);
		mockMvc.perform(post(BASE_URL_EMPLOYEE + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isOk());
		
		
	}

	private String convertToJson(Object convertingObject) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(convertingObject);
	}

}
