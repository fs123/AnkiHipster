package ch.ivyteam.hipster;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import ch.ivyteam.hipster.anki.iot.message.RawAnkiMessage;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.MessageFactory;

@Component
public class RawMessageToHipsterMessageConerterWithAnnotation {

    private RabbitTemplate rabbitTemplate;

	public RawMessageToHipsterMessageConerterWithAnnotation(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
    
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.ANKI_RAW_TOPIC_NAME, type="topic", durable="true"), 
	        key = "#"
	        )
	  )
    public void receiveMessage(Message message) {
		try {
			RawAnkiMessage ankiMessage = RawAnkiMessage.fromBytes(message.getBody());
			System.out.println(ankiMessage);
			
			AnkiMessage createMessage = MessageFactory.createMessage(ankiMessage.getAddress(), ankiMessage.getRawMessage());
			
			System.out.println(createMessage);
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
//    	AnkiHipsterMessage ankiHipsterMessage = new AnkiHipsterMessage(message);
//    	
//    	rabbitTemplate.convertAndSend(Config.QueueNames.ANKY_HIPSTER_MESSAGES, ankiHipsterMessage);
    }

}
