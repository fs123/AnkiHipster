
package ch.ivyteam.hipster;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Example App: https://spring.io/guides/gs/messaging-rabbitmq/
 */
@SpringBootApplication
public class HipsterConverterApplication {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(Config.HOST_NAME);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(HipsterConverterApplication.class, args);
    }
}