package ch.ivyteam.hipster.anki.message;

public class PositionUpdate extends AnkiMessage {
    public final int size = partInt(0);
    public final int msg_id = partInt(1);
    public final int location_id = partInt(2);
    public final int road_piece_id = partInt(3);
    public final float offset_from_road_center_mm = parsFloat(4);
    public final int speed_mm_per_sec = partInt16(8);
    public final int parsing_flags = partInt(10);

    /* ACK commands received */
//    public final int last_recv_lane_change_cmd_id = partInt(11);
//    public final int last_exec_lane_change_cmd_id = partInt(12);
//    public final int last_desired_horizontal_speed_mm_per_sec = partInt16(13);
//    public final int last_desired_speed_mm_per_sec = partInt16(15);
    
	public PositionUpdate(AnkiCar ankiCar, String messageBody) {
		super(ankiCar, messageBody);
	}

	@Override
	public String toString() {
		return "PositionUpdate [size=" + size + ", msg_id=" + msg_id + ", location_id=" + location_id
				+ ", road_piece_id=" + road_piece_id + ", offset_from_road_center_mm=" + offset_from_road_center_mm
				+ ", speed_mm_per_sec=" + speed_mm_per_sec + ", parsing_flags=" + parsing_flags + "]";
	}

}
