package ch.ivyteam.hipster;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutListenerA {

	//@RabbitListener(queues = "myQueue")
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.EXCHANGE_KEY_HIPSTER_MESSAGES, type="topic", ignoreDeclarationExceptions = "true", autoDelete = "true")
	        //,key = "orderRoutingKey"
	        )
	  )
    public void receiveMessage(Object message) {
    	System.out.println("receiveMessage"+ getClass().getSimpleName() + ": " + message);
    }
}
