package todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TodosApplication {

    public static void main(String[] args) {

        SpringApplication.run(TodosApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            // GET /assets/css/todos.css

            /* @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/assets/**")
                        .addResourceLocations("assets");
            } */
        };
    }

}

