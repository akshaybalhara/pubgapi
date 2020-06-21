package com.pubg;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * AllergenBuilderConfiguration contains all necessary configurations
 * required to smoothly run the Micro Service.
 * 
 * @author Prolifics.
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@ComponentScan("com.pubg")
@PropertySource("classpath:application.properties")
@EnableCaching
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	/**
	 * 
	 * Constant to hold the resource bundle file.
	 */
	private static final String PROPERTY_NAME_MESSAGE_SOURCE_BASENAME = "i18n/messages";

	@Autowired
	private Environment env;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	
	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }

	/**
	 * ResourceBundleMessageSource bean configuration.
	 * 
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(PROPERTY_NAME_MESSAGE_SOURCE_BASENAME);
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	/**
	 * DataSource bean configuration.
	 * For local profile use - username /password to connect to DB
	 * For other profiles such as dev/qa/prod use JNDI name.
	 */
	@Bean
	public DataSource dataSource() throws NamingException { 
		String profile = env.getProperty("spring.profiles.active");
		if (profile != null && profile.equals("local")) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
			dataSource.setUrl(env.getProperty("spring.datasource.url"));
			dataSource.setUsername(env.getProperty("spring.datasource.username"));
			dataSource.setPassword(env.getProperty("spring.datasource.password"));
			return dataSource;
		}

		return (DataSource) new JndiTemplate().lookup(env.getProperty("spring.datasource.jndiName"));

	}

	/**
	 * OpenAPI Swagger is used for the documentation purpose
	 * and it also makes life easy when comes to testing of REST API.
	 */
	@Bean
	public OpenAPI swaggerOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(
			            new Components()
			                .addSecuritySchemes(securitySchemeName,
			                    new SecurityScheme()
			                        .name(securitySchemeName)
			                        .type(SecurityScheme.Type.HTTP)
			                        .scheme("bearer")
			                        .bearerFormat("JWT")
			                )
			        )
				.info(new Info().title("PUBG ROOMS REST API's").description(
						"Summary of REST API's used by <strong>PUBG ROOMS</strong> system."));
	}

}
