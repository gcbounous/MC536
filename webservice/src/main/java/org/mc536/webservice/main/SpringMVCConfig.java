package org.mc536.webservice.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.mc536.webservice"})
class SpringMVCConfig extends WebMvcAutoConfiguration {

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @PostConstruct
    public void doAfter() {
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Configuration
    static class WebMvcConfigurerAdapterImpl extends WebMvcConfigurerAdapter {

        @Autowired
        ApplicationContext context;

        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.APPLICATION_JSON);
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

            final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

            final ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

            converters.add(jacksonConverter);
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }
    }

}
