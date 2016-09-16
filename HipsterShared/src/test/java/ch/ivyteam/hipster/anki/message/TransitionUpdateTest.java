package ch.ivyteam.hipster.anki.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TransitionUpdateTest
{

  @Test
  public void testTransitionUpdate()
  {

    TransitionUpdate transitionUpdate = new TransitionUpdate(AnkiCar.SKULL,
            new AnkiMessageParser("12 29 00 00 10 bf 1f 49 00 ff ff 00 00 f4 01 00 00 33 2a"));

    assertThat(transitionUpdate.size).isEqualTo(0x12);
    assertThat(transitionUpdate.msg_id).isEqualTo(0x29);
    assertThat(transitionUpdate.road_piece_idx).isEqualTo(0x00);
    assertThat(transitionUpdate.road_piece_idx_prev).isEqualTo(0x00);
    assertThat(transitionUpdate.offset_from_road_center_mm).isEqualTo(654321.0f);
    assertThat(transitionUpdate.driving_direction).isEqualTo(0x00);
    assertThat(transitionUpdate.uphill_counter).isEqualTo(0x00);
    assertThat(transitionUpdate.downhill_counter).isEqualTo(0x00);
    assertThat(transitionUpdate.left_wheel_dist_cm).isEqualTo(0x33);
    assertThat(transitionUpdate.right_wheel_dist_cm).isEqualTo(0x2a);
  }
}
