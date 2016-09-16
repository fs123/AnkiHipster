package ch.ivyteam.hipster.anki.message;

public class SetSpeed extends AnkiMessage {

	public static final String TOPIC = "anki.setspeed";
	public int speed_mm_per_sec;
	public int accel_mm_per_sec2;
	public boolean respect_road_piece_speed_limit;

	public SetSpeed() 
	{
	}
	
	public SetSpeed(AnkiCar ankiCar, int speed_mm_per_sec, int accel_mm_per_sec2, boolean respect_road_piece_speed_limit) 
	{
		super(ankiCar, 6, 0x24);
		this.speed_mm_per_sec = speed_mm_per_sec;
		this.accel_mm_per_sec2 = accel_mm_per_sec2;
		this.respect_road_piece_speed_limit = respect_road_piece_speed_limit;
	}
	
	@Override
	public String getTopic() 
	{
		return TOPIC;
	}
	
	@Override
	public String toString() 
	{
		return "SetSpeed [size="+size+", msg_id="+msg_id+", speed_mm_per_sec="+speed_mm_per_sec+", accel_mm_per_sec2="+accel_mm_per_sec2+", respect_road_piece_speed_limit="+respect_road_piece_speed_limit+"]";
	}

	public void writeTo(AnkiMessageWriter writer) 
	{
		super.writeTo(writer);
		writer.appendUInt16(speed_mm_per_sec);
		writer.appendUInt16(accel_mm_per_sec2);
		writer.appendBoolean(respect_road_piece_speed_limit);
	}

}
