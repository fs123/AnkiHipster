package ch.ivyteam.hipster;

public final class Config {
	public static final String HOST_NAME = getEnvOrDefault("RABBIT_MQ_MSG_SERVICE_HOST", "192.168.3.72");
	public static final String ANKI_RAW_TOPIC_NAME = "amq.topic";

	public static final String QUEUE_KEY_RAW_MESSAGES = "#";
	public static final String EXCHANGE_KEY_HIPSTER_MESSAGES = "anki-hipster-message";

	private static String getEnvOrDefault(String envVariableName, String defaultValue) 
	{
		String value = System.getenv(envVariableName);
		value = value != null ? value : defaultValue;
		System.out.println(value);
		return value;
	}

}