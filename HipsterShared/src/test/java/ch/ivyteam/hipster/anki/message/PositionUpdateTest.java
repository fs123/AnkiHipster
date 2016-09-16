package ch.ivyteam.hipster.anki.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PositionUpdateTest
{

  @Test
  public void testPositionUpdate()
  {

    PositionUpdate positionUpdate = new PositionUpdate(AnkiCar.SKULL,
            new AnkiMessageParser("10 27 0b 22 10 bf 1f 49 f3 01 07 ff ff 00 00 f4 01"));
    
    assertThat(positionUpdate.size).isEqualTo(0x10);
    assertThat(positionUpdate.msg_id).isEqualTo(0x27);
    assertThat(positionUpdate.location_id).isEqualTo(0x0b);
    assertThat(positionUpdate.road_piece_id).isEqualTo(0x22);
    assertThat(positionUpdate.offset_from_road_center_mm).isEqualTo(654321.0f);
    assertThat(positionUpdate.speed_mm_per_sec).isEqualTo(0x01f3);
    assertThat(positionUpdate.parsing_flags).isEqualTo(0x07);
    
    PositionUpdate positionUpdate2 = new PositionUpdate(AnkiCar.SKULL,
            new AnkiMessageParser("10 27 05 21 10 bf 1f 49 e8 01 07 ff ff 00 00 f4 01"));
    PositionUpdate positionUpdate3 = new PositionUpdate(AnkiCar.SKULL,
            new AnkiMessageParser("10 27 16 17 10 bf 1f 49 10 02 47 ff ff 00 00 f4 01"));


  }
}
