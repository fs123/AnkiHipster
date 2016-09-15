package ch.ivyteam.hipster.anki.message;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class PositionUpdateTest {

	@Test
	public void testPositionUpdate() {
	
		PositionUpdate positionUpdate = new PositionUpdate(AnkiCar.CarAnna, 
				"10 27 0b 22 10 bf 1f 49 f3 01 07 ff ff 00 00 f4 01");
		PositionUpdate positionUpdate2 = new PositionUpdate(AnkiCar.CarAnna, 
				"10 27 05 21 10 bf 1f 49 e8 01 07 ff ff 00 00 f4 01");
		PositionUpdate positionUpdate3 = new PositionUpdate(AnkiCar.CarAnna, 
				"10 27 16 17 10 bf 1f 49 10 02 47 ff ff 00 00 f4 01");
		
		assertThat(positionUpdate.road_piece_id).isEqualTo(123);
		
	}
}
