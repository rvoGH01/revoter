package com.revoter.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig {
	private SpringSwaggerConfig springSwaggerConfig;
	
	@Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
    }
	
	/**
	 * Custom implementation 
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
			.apiInfo(apiInfo())
			.includePatterns(swaggerPatterns());
		
		// includePatterns(".*pet.*");
		// .includePatterns(".*api.*"); // assuming the API lives at something like http://myapp/api
	}
	
	/**
	 * List of API endpoints to be included in Swagger UI.
	 */
	private String[] swaggerPatterns() {
		List<String> patterns = new ArrayList<String>();
	    patterns.add("/revoter");
//	    patterns.add("/category/.*");
//	    patterns.add("/cache");

	    return patterns.toArray(new String[patterns.size()]);
	}
	
	/**
	 * Api info. 
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"RESTAURANT VOTING SYSTEM API",                          // default Title
	            "This Spring REST API is for Restaurant voting system.", // API Description
	            "https://xxxx.com",                                      // API terms of service
	            "rv@gmail.com.com",                                      // Contact email
	            "No License",                                            // API License Type 
	            null                                                     // API License URL
	       );
	}
}
