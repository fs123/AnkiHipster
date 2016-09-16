package ch.ivyteam.hipster.anki.message;

public class PositionUpdate extends AnkiMessage {
	public static final String TOPIC = "anki.position";
	public int location_id;
	public int road_piece_id;
	public float offset_from_road_center_mm;
	public int speed_mm_per_sec;
	public int parsing_flags;

	public PositionUpdate() {
		super();
	}

	/* ACK commands received */
	// public final int last_recv_lane_change_cmd_id = partInt(11);
	// public final int last_exec_lane_change_cmd_id = partInt(12);
	// public final int last_desired_horizontal_speed_mm_per_sec =
	// partInt16(13);
	// public final int last_desired_speed_mm_per_sec = partInt16(15);

	public PositionUpdate(AnkiCar ankiCar, AnkiMessageParser parser) {
		super(ankiCar, parser);
		location_id = parser.nextUInt8();
		road_piece_id = parser.nextUInt8();
		offset_from_road_center_mm = parser.nextFloat();
		speed_mm_per_sec = parser.nextUInt16();
		parsing_flags = parser.nextUInt8();
		parser.skipNextBytes(6);
		parser.checkEnd();
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

	@Override
	public String toString() {
		return "PositionUpdate [size=" + size + ", msg_id=" + msg_id + ", location_id=" + location_id
				+ ", road_piece_id=" + road_piece_id + ", offset_from_road_center_mm=" + offset_from_road_center_mm
				+ ", speed_mm_per_sec=" + speed_mm_per_sec + ", parsing_flags=" + parsing_flags + "]";
	}

}
