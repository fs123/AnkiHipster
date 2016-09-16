package ch.ivyteam.hipster.server;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import ch.ivyteam.hipster.Config;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.PositionUpdate;
import ch.ivyteam.hipster.anki.message.TransitionUpdate;
import ch.ivyteam.hipster.server.rest.AnkiController;
import ch.ivyteam.hipster.server.websocket.WebSocketHandler;

@Component
public class HipsterMessageListener {

    private RabbitTemplate rabbitTemplate;
	private WebSocketHandler wsHandler;
	private AnkiController ankiController;

	public HipsterMessageListener(RabbitTemplate rabbitTemplate, WebSocketHandler wsHandler, AnkiController ankiController) {
		this.rabbitTemplate = rabbitTemplate;
		this.wsHandler = wsHandler;
		this.ankiController = ankiController;
	}
    
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.EXCHANGE_KEY_HIPSTER_MESSAGES, type="topic", ignoreDeclarationExceptions = "true", autoDelete="true"), 
	        key = PositionUpdate.TOPIC
	        )
	  )		
    public void receiveMessage(@Payload PositionUpdate message) {
		try {
			this.wsHandler.send(message);
			this.ankiController.handle(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
//    	AnkiHipsterMessage ankiHipsterMessage = new AnkiHipsterMessage(message);
//    	
//    	rabbitTemplate.convertAndSend(Config.QueueNames.ANKY_HIPSTER_MESSAGES, ankiHipsterMessage);
    }
	
	@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(durable = "false", autoDelete="true"), // no name, because the queue is only temporary
	        exchange = @Exchange(value = Config.EXCHANGE_KEY_HIPSTER_MESSAGES, type="topic", ignoreDeclarationExceptions = "true", autoDelete="true"), 
	        key = TransitionUpdate.TOPIC
	        )
	  )		
    public void receiveMessage(@Payload TransitionUpdate message) {
		try {
			this.wsHandler.send(message);
			this.ankiController.handle(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }


}
