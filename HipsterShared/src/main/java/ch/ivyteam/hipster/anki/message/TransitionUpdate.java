package ch.ivyteam.hipster.anki.message;

public class TransitionUpdate extends AnkiMessage {
	
	public final int size = Integer.parseInt(messageBody.substring(0, 2), 16);
	public final int msg_id = Integer.parseInt(messageBody.substring(2, 4), 16);
	public final int road_piece_idx = Integer.parseInt(messageBody.substring(4, 6), 16);
	public final int road_piece_idx_prev = Integer.parseInt(messageBody.substring(6, 8), 16);
	public final float offset_from_road_center_mm = parsFloat();

	private float parsFloat() {
		int byte0 = Integer.parseInt(messageBody.substring(8, 10), 16);
		int byte1 = Integer.parseInt(messageBody.substring(11, 13), 16);		
		int byte2 = Integer.parseInt(messageBody.substring(14, 16), 16);
		int byte3 = Integer.parseInt(messageBody.substring(17, 19), 16);
		int bits = byte0+byte1>>8+byte2>>16+byte3>>24;
		float value = Float.intBitsToFloat(bits);
		return value;
	}

	public final int driving_direction = Integer.parseInt(messageBody.substring(10, 12), 8);

//	/* ACK commands received */
//	public final int last_recv_lane_change_id = Integer.parseInt(messageBody.substring(12, 14), 8);
//	public final int last_exec_lane_change_id = Integer.parseInt(messageBody.substring(14, 16), 8);
//	public final int last_desired_horizontal_speed_mm_per_sec = Integer.parseInt(messageBody.substring(16, 20), 8);
//	public final int last_desired_speed_mm_per_sec = Integer.parseInt(messageBody.substring(20, 24), 8);

	/* track grade detection */
	public final int uphill_counter = Integer.parseInt(messageBody.substring(24, 26), 8);
	public final int downhill_counter = Integer.parseInt(messageBody.substring(26, 28), 8);

	/* wheel displacement (cm) since last transition bar */
	public final int left_wheel_dist_cm = Integer.parseInt(messageBody.substring(28, 30), 8);
	public final int right_wheel_dist_cm = Integer.parseInt(messageBody.substring(30, 32), 8);

	public TransitionUpdate(AnkiCar ankiCar, String messageBody) {
		super(ankiCar, messageBody);
	}

	@Override
	public String toString() {
		return "TransitionUpdate [size=" + size + ", msg_id=" + msg_id + ", road_piece_idx=" + road_piece_idx
				+ ", road_piece_idx_prev=" + road_piece_idx_prev + ", offset_from_road_center_mm="
				+ offset_from_road_center_mm + ", driving_direction=" + driving_direction
				+ ", last_recv_lane_change_id=" + last_recv_lane_change_id + ", last_exec_lane_change_id="
				+ last_exec_lane_change_id + ", last_desired_horizontal_speed_mm_per_sec="
				+ last_desired_horizontal_speed_mm_per_sec + ", last_desired_speed_mm_per_sec="
				+ last_desired_speed_mm_per_sec + ", uphill_counter=" + uphill_counter + ", downhill_counter="
				+ downhill_counter + ", left_wheel_dist_cm=" + left_wheel_dist_cm + ", right_wheel_dist_cm="
				+ right_wheel_dist_cm + ", ankiCar=" + ankiCar + ", messageBody=" + messageBody + "]";
	}
	
}
