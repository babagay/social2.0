package social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import social.config.YAMLConfig;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@EnableRabbit
//@EntityScan("persistence.domain")
//@EnableJpaRepositories("persistence.repository")
//@ComponentScan(basePackages = {"common","service"})
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
