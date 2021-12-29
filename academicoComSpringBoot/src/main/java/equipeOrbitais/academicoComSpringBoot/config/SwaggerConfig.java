package equipeOrbitais.academicoComSpringBoot.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration //anotação que define que essa é uma classe de configuração

public class SwaggerConfig {
	
	@Bean
    public Docket academicoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("equipeOrbitais.academicoComSpringBoot"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Academico com Spring Boot",
                "Sistema academico usando API REST com Spring Boot .",
                "1.0",
                "Terms of Service",
                new Contact("Ithaelly Medeiros", "https://github.com/Ithaelly/academicoComSpringBoot",
                        "ithaelly_mykarlla@hotmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}
