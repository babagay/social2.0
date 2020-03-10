package social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import social.config.YAMLConfig;
import social.config.YamlPropertySourceFactory;

//@Configuration
//@EnableAutoConfiguration
//@EnableRabbit
@SpringBootApplication
@PropertySources({
        @PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:i18n/messages.yml"),
        @PropertySource(value = "classpath:application.properties")
})
@EntityScan(basePackages = "social.model") // JPA entities
//@EnableJpaRepositories("social.repository") // for DB config
@ComponentScan(basePackages = {
        "social.dao",
        "social.components",
        "social.controller",
        "social.service",
        "social.config",
        "social.validator",
        "social.repository"
})
public class Application implements CommandLineRunner
{
    @Autowired
    private YAMLConfig appConfig;

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(Application.class);
        app.run();
    }

    public void run(String... args) throws Exception
    {
        System.out.println("using environment: " + appConfig.getEnvironment());
    }

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
