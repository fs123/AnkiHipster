package ch.ivyteam.hipster.server.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.ivyteam.hipster.Config;
import ch.ivyteam.hipster.anki.message.AnkiCar;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.ChangeLane;
import ch.ivyteam.hipster.anki.message.Light;
import ch.ivyteam.hipster.anki.message.SetLight;
import ch.ivyteam.hipster.anki.message.SetSpeed;

@Component
public class WebSocketHandler extends TextWebSocketHandler
{
	private List<WebSocketSession> sessions = new ArrayList<>();
	private RabbitTemplate rabbitTemplate;
	
	public WebSocketHandler(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception 
	{
		sessions.add(session);
		super.afterConnectionEstablished(session);
	}
	
	@Override
	public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception 
	{
		sessions.remove(session);
		super.afterConnectionClosed(session, status);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception 
	{
		String json = message.getPayload();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode command = mapper.readerFor(JsonNode.class).readTree(json);

		JsonNode data = command.get("data");
		AnkiCar car = AnkiCar.valueOf(data.get("carName").asText());
		setSpeed(data, car);
		changeLane(data, car);
		setLights(data, car);
		System.out.println("WebSocket command "+json);		
	}

	private void setSpeed(JsonNode data, AnkiCar car) {
		JsonNode speed = data.get("speed");
		if (speed != null)
		{
			SetSpeed speedCmd = new SetSpeed(car, speed.asInt(), 20000, false);
			rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, speedCmd.getTopic(), speedCmd);
		}
	}

	private void changeLane(JsonNode data, AnkiCar car) {
		JsonNode position = data.get("position");
		if (position != null)
		{
			ChangeLane offsetCmd = new ChangeLane(car, 100, 20000, position.floatValue());
			rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, offsetCmd.getTopic(), offsetCmd);
		}
	}

	private void setLights(JsonNode data, AnkiCar car) {
		JsonNode lights = data.get("lights");
		if (lights != null)
		{
			for (JsonNode light : lights)
			{
				Light name = Light.valueOf(light.get("name").asText());
				if (name != null)
				{
					SetLight setLight = new SetLight(car, name, light.get("on").asBoolean());
					rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, setLight.getTopic(), setLight);
				}
			}
		}
	}
	
	public synchronized void send(AnkiMessage message) 
	{
		sessions.stream().forEach(session -> send(session, message));
	}

	private void send(WebSocketSession session, AnkiMessage message) 
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(message);
			session.sendMessage(new TextMessage(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
