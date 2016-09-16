package ch.ivyteam.hipster;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import ch.ivyteam.hipster.anki.iot.message.RawAnkiMessage;
import ch.ivyteam.hipster.anki.message.AnkiCar;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.AnkiMessageParser;
import ch.ivyteam.hipster.anki.message.MessageFactory;
import ch.ivyteam.hipster.anki.message.PositionUpdate;
import ch.ivyteam.hipster.anki.message.TransitionUpdate;

@Component
public class RawMessageToHipsterMessageConverter {

    private RabbitTemplate rabbitTemplate;

	public RawMessageToHipsterMessageConverter(RabbitTemplate rabbitTemplate) {
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
			rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, createMessage.getTopic(), createMessage);
			System.out.println(createMessage);
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
