package ch.ivyteam.hipster.anki.message;

public class AnkiMessage {
	public final AnkiCar ankiCar;
	public final String messageBody;

	public AnkiMessage(AnkiCar ankiCar, String messageBody) {
		this.ankiCar = ankiCar;
		this.messageBody = messageBody;
	}
}
