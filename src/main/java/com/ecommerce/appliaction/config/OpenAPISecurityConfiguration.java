package com.ecommerce.appliaction.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ecommerce API",
                contact = @Contact(
                        name = "Diatoz", email = "user-apis@diaatoz.com", url = "http://www.diatoz.com"),
                license = @License(
                        name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0")),
        servers = @Server(url = "${api.server.url}", description = "Local"))
public class OpenAPISecurityConfiguration {
}