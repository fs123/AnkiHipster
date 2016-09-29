package ch.ivyteam.hipster;

import java.io.IOException;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import ch.ivyteam.hipster.anki.iot.message.RawAnkiMessage;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.AnkiMessageWriter;
import ch.ivyteam.hipster.anki.message.ChangeLane;
import ch.ivyteam.hipster.anki.message.SetSpeed;

@Component
public class SetCommandListener 
{
	private RabbitTemplate rabbitTemplate;

	public SetCommandListener(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate = rabbitTemplate;
	}
	
	//@RabbitListener(queues = "myQueue")
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.EXCHANGE_KEY_HIPSTER_MESSAGES, type="topic", ignoreDeclarationExceptions = "true", autoDelete = "true"),
	        key = SetSpeed.TOPIC
	        )
	  )
    public void receiveMessage(@Payload SetSpeed message) 
	{
		sendMessageToIotBridge(message);
    }
	
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.EXCHANGE_KEY_HIPSTER_MESSAGES, type="topic", ignoreDeclarationExceptions = "true", autoDelete = "true"),
	        key = ChangeLane.TOPIC
	        )
	  )
    public void receiveMessage(@Payload ChangeLane message) 
	{
		sendMessageToIotBridge(message);
    }

	private void sendMessageToIotBridge(AnkiMessage message) {
		AnkiMessageWriter writer = new AnkiMessageWriter();
		message.writeTo(writer);
		RawAnkiMessage rawAnkiMessage = new RawAnkiMessage(message.ankiCar.getAddress(), writer.getHexString());
		try {
			Message msg = new Message(rawAnkiMessage.toBytes(), new MessageProperties());
			rabbitTemplate.convertAndSend(Config.ANKI_RAW_TOPIC_NAME, "$EDC.anki.iot-bridge.anki", msg);
		} catch (AmqpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("receiveMessage"+ getClass().getSimpleName() + ": " + message);
    	System.out.println("sendMessage "+ rawAnkiMessage);
	}

}
