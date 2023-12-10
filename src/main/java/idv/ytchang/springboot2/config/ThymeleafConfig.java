package idv.ytchang.springboot2.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.messageresolver.SpringMessageResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * @author yuante
 *
 */
@Configuration
public class ThymeleafConfig {

	public ThymeleafConfig() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Creates the template engine for all message templates.
     * add comprehension to layout:*
     *
     * @param inTemplateResolvers Template resolver for different types of messages etc.
     * Note that any template resolvers defined elsewhere will also be included in this
     * collection.
     * @return Template engine.
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine(
        final Collection<SpringResourceTemplateResolver> inTemplateResolvers) {
        final SpringTemplateEngine theTemplateEngine = new SpringTemplateEngine();
        
        for (SpringResourceTemplateResolver theTemplateResolver : inTemplateResolvers) {
            theTemplateEngine.setTemplateResolver(theTemplateResolver);
        }
        
        theTemplateEngine.addDialect(new LayoutDialect());
        theTemplateEngine.addDialect(new SpringSecurityDialect());
        
        return theTemplateEngine;
    }

}
