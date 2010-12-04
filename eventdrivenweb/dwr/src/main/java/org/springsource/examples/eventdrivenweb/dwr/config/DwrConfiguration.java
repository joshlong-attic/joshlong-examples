package org.springsource.examples.eventdrivenweb.dwr.config;

import org.directwebremoting.spring.DwrNamespaceHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.dwr.AsyncHttpRequestHandlingMessageAdapter;

import javax.annotation.PostConstruct;


/**
 * Sets up the beans specific to the Atmosphere-specific configuration.
 *
 * @author Josh Long
 * @since 1.0
 */
@Configuration
public class DwrConfiguration extends WebConfiguration implements BeanFactoryAware, BeanNameAware {

	@Value("#{out}") protected MessageChannel messageChannel;

    protected String beanName;

    protected BeanFactory beanFactory;


    @PostConstruct
    public void start() {
        System.out.println(DwrConfiguration.class.getName() + " is starting...");
    }



  /*  @Bean
    public Chat chat() {
        return new Chat();
    }
*/


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


	@Bean
	public AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter (){
		AsyncHttpRequestHandlingMessageAdapter inboundDwrAdapter = new AsyncHttpRequestHandlingMessageAdapter();
		inboundDwrAdapter.setRequestChannel(this.messageChannel);

		return inboundDwrAdapter ;
	}
}
