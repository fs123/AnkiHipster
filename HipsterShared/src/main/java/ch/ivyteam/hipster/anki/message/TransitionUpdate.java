package ch.ivyteam.hipster.anki.message;

public class TransitionUpdate extends AnkiMessage {
	
	public final int size = partInt(0);
	public final int msg_id = partInt(1);
	public final int road_piece_idx = partInt(2);
	public final int road_piece_idx_prev = partInt(3);
	public final float offset_from_road_center_mm = parsFloat(4);

	public final int driving_direction = partInt(8);

	/* ACK commands received */
//	public final int last_recv_lane_change_id = partInt(9);
//	public final int last_exec_lane_change_id = partInt(10);
//	public final int last_desired_horizontal_speed_mm_per_sec = partInt(11);
//	public final int last_desired_speed_mm_per_sec = partInt(12);

	/* track grade detection */
	public final int uphill_counter = partInt(13);
	public final int downhill_counter = partInt(14);

	/* wheel displacement (cm) since last transition bar */
	public final int left_wheel_dist_cm = partInt(15);
	public final int right_wheel_dist_cm = partInt(16);

	public TransitionUpdate(AnkiCar ankiCar, String messageBody) {
		super(ankiCar, messageBody);
	}

	@Override
	public String toString() {
		return "TransitionUpdate [size=" + size + ", msg_id=" + msg_id + ", road_piece_idx=" + road_piece_idx
				+ ", road_piece_idx_prev=" + road_piece_idx_prev + ", offset_from_road_center_mm="
				+ offset_from_road_center_mm + ", driving_direction=" + driving_direction + ", uphill_counter="
				+ uphill_counter + ", downhill_counter=" + downhill_counter + ", left_wheel_dist_cm="
				+ left_wheel_dist_cm + ", right_wheel_dist_cm=" + right_wheel_dist_cm + "]";
	}
	
}
