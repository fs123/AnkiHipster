package ch.ivyteam.hipster.anki.message;

public class MessageFactory {

	public static AnkiMessage createMessage(String carKey, String message) {
//		message = message.substring(3);
		switch (message.substring(3, 5)) {
		case "4d":
			break;
		case "27":
			return new PositionUpdate(AnkiCar.get(carKey), message);
		case "29":
			return new TransitionUpdate(AnkiCar.get(carKey), message);
		case "41":
			break;
		default:
			break;
		}
/**
 * 1 - length
 * 2 - type
 * 3 - 
 *   4d - 
 *   27 - position update
 *   29 - transition update / position
 *      1 -  road peace id NOW
 *      2 -  road peace id PREVIOUS
 * Example:
 * 
 *   AnkiMessage [ankiCar=CarCesar, messageBody=[0e, 41, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, f4, 01, 80]]
 */
		return new AnkiMessage(AnkiCar.get(carKey), message);
	}
}
