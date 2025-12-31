package vlad.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager API",
                description = "REST API for managing tasks",
                version = "1.0.0",
                contact = @Contact(
                        name = "Vlad",
                        email = "vladyslav.lysenko.dev@gmail.com"
                )
        )
)
@Configuration
public class OpeApiConfig {
}
