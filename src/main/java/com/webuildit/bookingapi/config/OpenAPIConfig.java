package com.webuildit.bookingapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${common.version}")
    private String version;

    @Bean
    public OpenAPI openAPIConfiguration() {
        return new OpenAPI()
            .info(new Info().title("WeBuild-IT Booking REST API")
                .description("API to check booking availability")
                .version(version)
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
