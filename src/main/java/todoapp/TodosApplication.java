package todoapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import todoapp.commons.web.view.CsvView;
import todoapp.security.UserSessionHandlerMethodArgumentResolver;

@SpringBootApplication
public class TodosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodosApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // HTTP GET -> /assets/css/todos.css

    /*
    registry.addResourceHandler("/assets/**")
         .addResourceLocations("assets/", "file:./files/assets/", "classpath:assets/");
    */

                // aws s3 리소스를 획득에서 제공해줘
                // s3:bucketName/path/
            }

            @Override
            public void configureViewResolvers(ViewResolverRegistry registry) {
                // registry.viewResolver(new SimpleMappingViewResolver());

                // Spring MVC로 ContentNegotiation을 구성 할 때
                // registry.enableContentNegotiation(defaultViews);
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new UserSessionHandlerMethodArgumentResolver());
            }
        };
    }

    /*
        TIP : Spring Boot에 구성된 ContentNegotiation 전략을 바꾸고 싶을 때
    */
    @Autowired
    public void configContentNegotiation(ContentNegotiatingViewResolver viewResolver) {
        List<View> defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
        defaultViews.add(new CsvView());

        viewResolver.setDefaultViews(defaultViews);
    }

}