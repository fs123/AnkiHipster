package ch.ivyteam.hipster.server.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.PathParam;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.ivyteam.hipster.Config;
import ch.ivyteam.hipster.anki.message.AnkiCar;
import ch.ivyteam.hipster.anki.message.AnkiMessage;
import ch.ivyteam.hipster.anki.message.PositionUpdate;
import ch.ivyteam.hipster.anki.message.SetLight;
import ch.ivyteam.hipster.anki.message.SetSpeed;
import ch.ivyteam.hipster.anki.message.TransitionUpdate;

@RestController
@RequestMapping("/anki")
public class AnkiController 
{
	private Map<AnkiCar, Car> cars = new HashMap<>();
	private RabbitTemplate rabbitTemplate;
	
	public AnkiController(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@RequestMapping("/cars")
	public Collection<Car> cars()
	{
		return cars.values();
	}
	
	@GetMapping("/cars/{car}")
	public Car car(@PathVariable("car") String car)
	{
		Car auto = cars.get(AnkiCar.valueOf(car));
		return auto;		
	}
	
	@PostMapping("/cars/{car}")
	public void update(@PathVariable("car") String carName, @RequestBody Car car)
	{
		AnkiCar ankiCar = getAnkiCar(carName);
		if (ankiCar != null && car.getSpeed_mm_per_sec() != 0)
		{
			SetSpeed setSpeed = new SetSpeed(ankiCar, car.getSpeed_mm_per_sec(), 20000, false);
			rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, setSpeed.getTopic(), setSpeed);
		}
		System.out.println(""+carName+" speed -> "+car.getSpeed_mm_per_sec());
		System.out.println(""+carName+" offset -> "+car.getOffset_from_road_center_mm());
	}

	private AnkiCar getAnkiCar(String carName) {
		AnkiCar ankiCar = AnkiCar.valueOf(carName);
		return ankiCar;
	}
	
	@PostMapping("/cars/{car}/light")
	public void light(@PathVariable("car") String carName, @RequestBody LightInfo light)
	{
		AnkiCar ankiCar = getAnkiCar(carName);
		if (ankiCar !=null)
		{
			SetLight setLight = new SetLight(ankiCar, light.name, light.on);
			rabbitTemplate.convertAndSend(Config.EXCHANGE_KEY_HIPSTER_MESSAGES, setLight.getTopic(), setLight);
		}
		System.out.println(""+carName+" light "+light);
	}

	public void handle(PositionUpdate message) 
	{
		Car car = ensureCar(message);
		car.handle(message);
	}

	private Car ensureCar(AnkiMessage message) {
		Car car = cars.get(message.ankiCar);
		if (car == null)
		{
			car = new Car(message.ankiCar);
			cars.put(message.ankiCar, car);
		}
		return car;
	}
	
	public void handle(TransitionUpdate message)
	{
		Car car = ensureCar(message);
		car.handle(message);
	}
}
