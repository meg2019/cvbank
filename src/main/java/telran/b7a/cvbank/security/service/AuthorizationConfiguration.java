package telran.b7a.cvbank.security.service;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/cvbank/employee/signup/**", "/cvbank/employer/signup/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);				
		http.authorizeRequests()
			//=================== employee ==============================
			.antMatchers(HttpMethod.POST, "/cvbank/employee/signin/**")
				.authenticated()
			.antMatchers(HttpMethod.PUT, "/cvbank/employee/login/**")
				.authenticated()
			.antMatchers(HttpMethod.PUT, "/cvbank/employee/pass/**")
				.authenticated()	
			.antMatchers("/cvbank/employee/{employeeId}/**")  //same matcher for Update (PUT); Delete (DELETE); Find (Get) by employeeId
				.access("#employeeId == authentication.name")
			//=================== employer ==============================
			.antMatchers(HttpMethod.POST, "/cvbank/employer/signin/**")
				.authenticated()
			.antMatchers(HttpMethod.PUT, "/cvbank/employer/login/**")
				.authenticated()
			.antMatchers(HttpMethod.PUT, "/cvbank/employer/pass/**")
				.authenticated()
			.antMatchers(HttpMethod.GET, "/cvbank/employer/**")
				.authenticated()
			.antMatchers("/cvbank/employer/{employerId}/**")  //same matcher for Update (PUT); Delete (DELETE); Find (Get) by employerId
				.access("#employerId == authentication.name")
			.antMatchers(HttpMethod.GET, "/cvbank/employer/company/**")			//Find Employer by company name
				.authenticated()
			//==================== cv ==================================
			.antMatchers(HttpMethod.POST, "/cvbank/cv/**")
				.hasRole("EMPLOYEE")
			.antMatchers(HttpMethod.PUT, "/cvbank/cv/anonymizer/{cvId}/**")
				.access("@customSecurity.checkCVAuthority(#cvId, authentication.name)")
			.antMatchers(HttpMethod.PUT, "/cvbank/cv/{cvId}/**")
				.access("@customSecurity.checkCVAuthority(#cvId, authentication.name)")
			.antMatchers(HttpMethod.DELETE, "/cvbank/cv/{cvId}/**")
				.access("@customSecurity.checkCVAuthority(#cvId, authentication.name)")
			
			//============ final matcher ===============================
			.anyRequest()
				.authenticated();
	}
	
}
	


