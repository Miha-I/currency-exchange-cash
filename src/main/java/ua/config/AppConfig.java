package ua.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.parser.CurrencyParser;
import ua.parser.impl.CurrencyParserNBU;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScans(value = { @ComponentScan("ua.repository"), @ComponentScan("ua.service")})
public class AppConfig {

    private final Environment environment;

    // Временно сделал так
    public static String PB_URL;

    public AppConfig(Environment environment){
        this.environment = environment;
        PB_URL = environment.getProperty("urlPB_json");
    }

    @Bean
    public CurrencyParser getCurrencyParser(){
        return new CurrencyParserNBU(environment.getRequiredProperty("urlNBU_json"));
    }
}
