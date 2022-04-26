package telran.b7a.cvbank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import telran.b7a.cvbank.accounting.employee.controller.EmployeeAccountingController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CvbankApplicationSmokeTests {

	@LocalServerPort
	private int port;

	EmployeeAccountingController employeeAccountingController;
	TestRestTemplate restTemplate;

	@Autowired
	public CvbankApplicationSmokeTests(EmployeeAccountingController employeeAccountingController,
			TestRestTemplate restTemplate) {
		this.employeeAccountingController = employeeAccountingController;
		this.restTemplate = restTemplate;
	}

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(employeeAccountingController).isNotNull();
	}

	@Test
	@Order(2)
	void shouldAuthorizationByDefaultTest() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}

}
