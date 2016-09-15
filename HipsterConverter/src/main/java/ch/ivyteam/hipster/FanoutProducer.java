package ch.ivyteam.hipster;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FanoutProducer implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;

	public FanoutProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

    @Override
    public void run(String... args) throws Exception {
//    	while (true) {
//    		String publishingValue = ""+ (System.currentTimeMillis()/1000);
//    		System.out.println("produced value " + publishingValue);
//    		rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, "", publishingValue);
//    		Thread.sleep(2000 + new Random().nextInt(200));
//    	}
    }
}
