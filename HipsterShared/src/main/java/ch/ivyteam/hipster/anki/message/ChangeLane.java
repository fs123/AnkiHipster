package ch.ivyteam.hipster.anki.message;

public class ChangeLane extends AnkiMessage 
{
	public static final String TOPIC = "anki.changelane";
	public int horizontal_speed_mm_per_sec;
	public int horizontal_accel_mm_per_sec2;
	public float offset_from_road_center_mm;
	
	public ChangeLane(AnkiCar car, int horizontal_speed_mm_per_sec, int horizontal_accel_mm_per_sec2, float offset_from_road_center_mm)
	{
		super(car, 11, 0x25);
		this.horizontal_speed_mm_per_sec = horizontal_speed_mm_per_sec;
		this.horizontal_accel_mm_per_sec2 = horizontal_accel_mm_per_sec2;
		this.offset_from_road_center_mm = offset_from_road_center_mm;
	}
	
	public ChangeLane() {
	}
	
	@Override
	public String getTopic() 
	{
		return TOPIC;
	}
	
	@Override
	public String toString() 
	{
		return "ChangeLane [size="+size+", msg_id="+msg_id+", horizontal_speed_mm_per_sec="+horizontal_speed_mm_per_sec+", horizontal_accel_mm_per_sec2="+horizontal_accel_mm_per_sec2+", offset_from_road_center_mm="+offset_from_road_center_mm+"]";
	}

	public void writeTo(AnkiMessageWriter writer) 
	{
		super.writeTo(writer);
		writer.appendUInt16(horizontal_speed_mm_per_sec);
		writer.appendUInt16(horizontal_accel_mm_per_sec2);
		writer.appendFloat(offset_from_road_center_mm);
		writer.appendUInt8((byte)0);
		writer.appendUInt8((byte)0);
	}

}
