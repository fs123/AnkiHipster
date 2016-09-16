package ch.ivyteam.hipster.anki.message;

public enum AnkiCar { // GroundShock
	
	NUKE("C7:E8:D2:22:BB:DE"),
	GROUP_SHOOK("D6:3C:A3:24:A0:F9"),
	SKULL("D1:53:02:F5:C6:DD"),
    GURROIAN("DC:FE:9C:BA:9E:84"),
	UNKNOWN("");
	
	private final String address;

	AnkiCar(String address) {
		this.address = address;
	}
	
	public static AnkiCar get(String car) {
		AnkiCar[] values = AnkiCar.values();
		for (AnkiCar ankiCar : values) {
			if (ankiCar.address.equals(car)) {
				return ankiCar;
			}
		}
		return UNKNOWN;
	}

	public String getAddress() {
		return address;
	}
}
