package ch.ivyteam.hipster.anki.message;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class TransitionUpdateTest {

	@Test
	public void testTransitionUpdate() {
	
		TransitionUpdate transitionUpdate = new TransitionUpdate(AnkiCar.CarAnna, 
				"12 29 00 00 10 bf 1f 49 00 ff ff 00 00 f4 01 00 00 33 2a");
		
		assertThat(transitionUpdate.road_piece_idx).isEqualTo(123);
		assertThat(transitionUpdate.road_piece_idx_prev).isEqualTo(123);
		
	}
}
