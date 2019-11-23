package social.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties
@EnableConfigurationProperties
public class YAMLConfig
{
    private String name;
    private String environment;
    private List<String> servers = new ArrayList<>();

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public void setServers(List<String> servers)
    {
        this.servers = servers;
    }

    public String getName()
    {
        return name;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public List<String> getServers()
    {
        return servers;
    }
}
