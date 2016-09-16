package ch.ivyteam.hipster.anki.message;

public class TransitionUpdate extends AnkiMessage
{
  public static final String TOPIC = "anki.transition";
public  int road_piece_idx;
  public  int road_piece_idx_prev;
  public  float offset_from_road_center_mm;

  public  int driving_direction;

  /* ACK commands received */
  // public  int last_recv_lane_change_id = partInt(9);
  // public  int last_exec_lane_change_id = partInt(10);
  // public  int last_desired_horizontal_speed_mm_per_sec = partInt(11);
  // public  int last_desired_speed_mm_per_sec = partInt(12);

  /* track grade detection */
  public  int uphill_counter;
  public  int downhill_counter;

  /* wheel displacement (cm) since last transition bar */
  public  int left_wheel_dist_cm;
  public  int right_wheel_dist_cm;

  public TransitionUpdate() {
  }
  public TransitionUpdate(AnkiCar ankiCar, AnkiMessageParser parser)
  {
    super(ankiCar, parser);
    road_piece_idx = parser.nextUInt8();
    road_piece_idx_prev = parser.nextUInt8();
    offset_from_road_center_mm = parser.nextFloat();
    driving_direction = parser.nextUInt8();
    parser.skipNextBytes(6);
    uphill_counter = parser.nextUInt8();
    downhill_counter = parser.nextUInt8();

    left_wheel_dist_cm = parser.nextUInt8();
    right_wheel_dist_cm = parser.nextUInt8();
    parser.checkEnd();
  }
  
  @Override
	public String getTopic() {
		return TOPIC;
	}

  @Override
  public String toString()
  {
    return "TransitionUpdate [size=" + size + ", msg_id=" + msg_id + ", road_piece_idx=" + road_piece_idx
            + ", road_piece_idx_prev=" + road_piece_idx_prev + ", offset_from_road_center_mm="
            + offset_from_road_center_mm + ", driving_direction=" + driving_direction + ", uphill_counter="
            + uphill_counter + ", downhill_counter=" + downhill_counter + ", left_wheel_dist_cm="
            + left_wheel_dist_cm + ", right_wheel_dist_cm=" + right_wheel_dist_cm + "]";
  }

}
