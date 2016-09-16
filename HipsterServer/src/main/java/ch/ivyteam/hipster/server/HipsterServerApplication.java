package ch.ivyteam.hipster.server;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.ivyteam.hipster.Config;

@SpringBootApplication
public class HipsterServerApplication {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(Config.HOST_NAME);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(HipsterServerApplication.class, args);
	}
}
