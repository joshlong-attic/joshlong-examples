package org.springsource.examples.eventdrivenweb.atmosphere.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import org.springframework.dao.DataAccessException;

import org.springframework.integration.MessageChannel;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springsource.examples.eventdrivenweb.atmosphere.InboundStatusWritingEndpoint;
import org.springsource.examples.eventdrivenweb.atmosphere.util.MapBuilder;

import java.util.Map;
import java.util.Properties;


/**
 * configures all the components in the system except for the namespaces
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class WebConfiguration {

	@Bean public HttpRequestHandlerAdapter httpRequestHandlerAdapter (){
		return new HttpRequestHandlerAdapter();
	}
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("WEB-INF/i18n/messages,WEB-INF/i18n/application".split(","));
        reloadableResourceBundleMessageSource.setFallbackToSystemLocale(false);
        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        BeanNameUrlHandlerMapping mapping = new BeanNameUrlHandlerMapping();
	    mapping.setOrder(5);
        return mapping;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieName("locale");
        return resolver;
    }

	@Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("uncaughtException");

        Map<Class, String> classStringMap = new MapBuilder<Class, String>()
		        .put(DataAccessException.class, "dataAccessFailure")
		        .put(NoSuchRequestHandlingMethodException.class, "resourceNotFound")
		        .put(TypeMismatchException.class, "resourceNotFound")
		        .put(MissingServletRequestParameterException.class, "resourceNotFound")
		        .toMap();

        Properties props = new Properties();
        for (Class c : classStringMap.keySet())
            props.put(c.getName(), classStringMap.get(c));

	    resolver.setExceptionMappings(props);
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/views/layouts/layouts.xml,/WEB-INF/views/**views.xml".split(","));
        return tilesConfigurer;
    }
}
