package org.springsource.examples.eventdrivenweb.dwr.config;

import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.extend.Configurator;

import org.directwebremoting.extend.Converter;
import org.directwebremoting.spring.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springsource.examples.eventdrivenweb.dwr.util.MapBuilder;
import org.springsource.examples.eventdrivenweb.service.Chat;
import org.springsource.examples.eventdrivenweb.service.Message;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.PostConstruct;


/**
 * Sets up the beans specific to the Atmosphere-specific configuration.
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class DwrConfiguration extends WebConfiguration implements BeanFactoryAware, BeanNameAware {
    // @Value("#{out}")	protected MessageChannel messageChannel;
    protected String beanName;
    protected BeanFactory beanFactory;

    @PostConstruct
    public void start() {
        System.out.println(DwrConfiguration.class.getName() + " is starting...");
    }

    @Bean
    public Chat chat() {
        return new Chat();
    }



 /*ConverterConfig converterConfig(){
		ConverterConfig converterConfig = new ConverterConfig();
	}

	protected Map<String,CreatorConfig> creators(){
		Map<String, CreatorConfig> creatorConfigMap = new MapBuilder<String, CreatorConfig>().put("chat", config(chat())).toMap();
		return creatorConfigMap ;
	}


    @Bean(name = DwrNamespaceHandlerVisibilityPromoter.SPRING_CONFIGURATOR_ID )
    public Configurator dwrConfiguration() {
        SpringConfigurator config = new SpringConfigurator();

        config.setCreators( creators() );
	    config.setConverters( converters());

        return config;
    }

    protected CreatorConfig config(Object o) {
        CreatorConfig cc = new CreatorConfig();
	    BeanCreator creator = new BeanCreator();
	    creator.setBean(o);
	    creator.afterPropertiesSet();
	    cc.setCreator(creator);
        return cc;
    }
*/

/*
	@Bean
    public DwrController dwrController() {
        DwrController dwrController = new DwrController();
        dwrController.setConfigurators(Arrays.asList(this.dwrConfiguration()));
        dwrController.setDebug(true);

        return dwrController;
    }
*/

    @Override
    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     *
     * hack to make the value of the static field visible to our configuration. the field's protected, so we can't reference it directly without visibility promotion.
     *
     */
    abstract static class DwrNamespaceHandlerVisibilityPromoter extends DwrNamespaceHandler {
        public static final String  SPRING_CONFIGURATOR_ID = DEFAULT_SPRING_CONFIGURATOR_ID;
    }
}
