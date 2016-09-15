package ch.ivyteam.hipster.anki.message;

public enum AnkiCar {
	CarAnna ("D1:53:02:F5:C6:DD"),
	CarBerta("D6:3C:A3:24:A0:F9"),
	CarCesar("C7:E8:D2:22:BB:DE"), // Nuke
	CarDoris(""),
	Unknown ("");
	
	private final String key;

	AnkiCar(String key) {
		this.key = key;
	}
	
	public static AnkiCar get(String car) {
		AnkiCar[] values = AnkiCar.values();
		for (AnkiCar ankiCar : values) {
			if (ankiCar.key.equals(car)) {
				return ankiCar;
			}
		}
		return Unknown;
	}
}
