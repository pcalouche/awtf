package cucumber.runtime.java.spring;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlueCodeScopeConfig {

    @Bean
    public static CustomScopeConfigurer glueScopeConfigurer() {
        CustomScopeConfigurer toReturn = new CustomScopeConfigurer();
        toReturn.addScope("cucumber-glue", new GlueCodeScope());
        return toReturn;
    }
}
