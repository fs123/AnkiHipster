package ch.ivyteam.hipster.anki.message;

import java.util.Arrays;
import java.util.logging.Logger;

public class AnkiMessage {
	private static final Logger LOGGER = Logger.getLogger(AnkiMessage.class.getName());
	
	public final AnkiCar ankiCar;
	public final String[] messageBody;

	public AnkiMessage(AnkiCar ankiCar, String messageBody) {
		this.ankiCar = ankiCar;
		this.messageBody = messageBody.split(" ");
	}

	protected final int partInt(int position) {
		if (messageBody.length < position) {
			LOGGER.warning(() -> "Part of integer failed: "+ messageBody.length +" < "+ position);
			return 0;
		}
		return Integer.parseInt(messageBody[position], 16);
	}

	protected final int partInt16(int position) {
		if (messageBody.length < position) {
			LOGGER.warning(() -> "Part of integer failed: "+ messageBody.length +" < "+ position);
			return 0;
		}
		return Integer.parseInt(messageBody[position], 16);
	}

	protected final float parsFloat(int position) {
		if (messageBody.length < position+3) {
			LOGGER.warning(() -> "Part of float failed: "+ messageBody.length +" < "+ position+3);
			return 0;
		}
		int byte0 = Integer.parseInt(messageBody[position], 16);
		int byte1 = Integer.parseInt(messageBody[position+1], 16);		
		int byte2 = Integer.parseInt(messageBody[position+2], 16);
		int byte3 = Integer.parseInt(messageBody[position+3], 16);
		int bits = byte0 + (byte1>>8) + (byte2>>16) + (byte3>>24);
		float value = Float.intBitsToFloat(bits);
		return value;
	}

	@Override
	public String toString() {
		return "AnkiMessage [ankiCar=" + ankiCar + ", messageBody=" + Arrays.toString(messageBody) + "]";
	}
}
