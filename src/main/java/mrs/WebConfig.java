package mrs;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class WebConfig {

//    @Bean
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
//        bean.setBasename("classpath:messages");
//        bean.setDefaultEncoding("UTF-8");
//        return bean;
//    }
//
//    @Bean
//    public LocalValidatorFactoryBean localValidatorFactoryBean() {
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.setValidationMessageSource(messageSource());
//        return localValidatorFactoryBean;
//    }

    @Bean
    public MapperFactory getMapperFactory(){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
