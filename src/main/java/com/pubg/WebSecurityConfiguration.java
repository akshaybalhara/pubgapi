package com.pubg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pubg.config.JwtAuthenticationEntryPoint;
import com.pubg.config.JwtRequestFilter;
import com.pubg.util.PUBGCustomPasswordEncoder;

/**
 * This class extends the WebSecurityConfigurerAdapter is a convenience class 
 * that allows customization to both WebSecurity and HttpSecurity.
 * 
 * @author Prolifics
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * jwtAuthenticationEntryPoint will be injected by Spring Framework.
	 */
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	/**
	 * jwtUserDetailsService will be injected by Spring Framework.
	 */
	@Autowired
	private UserDetailsService jwtUserDetailsService;

	/**
	 * jwtRequestFilter will be injected by Spring Framework.
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * configureGlobal configures the Authentication Manager for
	 * the Security propagation.
	 * 
	 * @param auth
	 * 	<p> AuthenticationManagerBuilder instance.</p>
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * Custom Password Encoder setup as a Spring Bean.
	 * 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PUBGCustomPasswordEncoder();
	}

	/**
	 * AuthenticationManager setup as a spring bean
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * The core method which contains all plumbing logic
	 * for endpoints to authenticate, Session Management Policy,
	 * Filter provisioning etc.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF 
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().
				antMatchers("/user-auth/checkUpdate","/user-auth/login","/user-auth/forgotPassword/**","/user-auth/resetPassword/**","/user-auth",
						"/user-reg","/user-reg/**","/v3/api-docs/**","/configuration/**","/swagger-ui.html","/swagger-ui/**","/webjars/**").permitAll().
				antMatchers(HttpMethod.OPTIONS, "/**").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
