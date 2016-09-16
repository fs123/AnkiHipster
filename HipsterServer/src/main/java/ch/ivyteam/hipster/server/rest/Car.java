package ch.ivyteam.hipster.server.rest;

import ch.ivyteam.hipster.anki.message.AnkiCar;
import ch.ivyteam.hipster.anki.message.PositionUpdate;
import ch.ivyteam.hipster.anki.message.TransitionUpdate;

public class Car {

	private AnkiCar ankiCar;
	private int road_piece_id;
	private float offset_from_road_center_mm;
	private int location_id;
	private int speed_mm_per_sec;
	private int downhill_counter;
	private int driving_direction;
	private int left_wheel_dist_cm;
	private int right_wheel_dist_cm;
	private int uphill_counter;
	
	public Car()
	{
		
	}

	public Car(AnkiCar ankiCar) {
		this.ankiCar = ankiCar;
	}

	public void handle(PositionUpdate message) 
	{
		location_id = message.location_id;
		road_piece_id = message.road_piece_id;
		offset_from_road_center_mm = message.offset_from_road_center_mm;
		speed_mm_per_sec = message.speed_mm_per_sec;
	}
	
	public void handle(TransitionUpdate message) 
	{
		downhill_counter = message.downhill_counter;
		driving_direction = message.driving_direction;
		left_wheel_dist_cm = message.left_wheel_dist_cm;
		offset_from_road_center_mm = message.offset_from_road_center_mm;
		right_wheel_dist_cm = message.right_wheel_dist_cm;
		road_piece_id = message.road_piece_idx;
		uphill_counter = message.uphill_counter;
	}

	public AnkiCar getAnkiCar() {
		return ankiCar;
	}
	
	public int getRoad_piece_id() {
		return road_piece_id;
	}
	
	public int getLocation_id() {
		return location_id;
	}
	
	public float getOffset_from_road_center_mm() {
		return offset_from_road_center_mm;
	}
	
	public int getSpeed_mm_per_sec() {
		return speed_mm_per_sec;
	}
	
	public int getDownhill_counter() {
		return downhill_counter;
	}
	
	public int getDriving_direction() {
		return driving_direction;
	}
	
	public int getLeft_wheel_dist_cm() {
		return left_wheel_dist_cm;
	}
	
	public int getRight_wheel_dist_cm() {
		return right_wheel_dist_cm;
	}
	
	public int getUphill_counter() {
		return uphill_counter;
	}
}
