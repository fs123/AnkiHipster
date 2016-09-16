package ch.ivyteam.hipster.anki.message;

public class SetLight extends AnkiMessage {

	public static final String TOPIC = "anki.setlight";
	public byte light_mask;

	public SetLight() 
	{
	}
	
	public SetLight(AnkiCar ankiCar, Light light, boolean on) 
	{
		super(ankiCar, 2, 0x1d);
	    byte lightsMask = (byte)(0x01 << light.ordinal());
	    if (on)
	    {
	      lightsMask += 0x10 << light.ordinal();
	    }
		light_mask = lightsMask;
	}
	
	@Override
	public String getTopic() 
	{
		return TOPIC;
	}
	
	@Override
	public String toString() 
	{
		return "SetLight [size="+size+", msg_id="+msg_id+", light_mask="+light_mask+"]";
	}

	public void writeTo(AnkiMessageWriter writer) 
	{
		super.writeTo(writer);
		writer.appendUInt8(light_mask);
	}

}
