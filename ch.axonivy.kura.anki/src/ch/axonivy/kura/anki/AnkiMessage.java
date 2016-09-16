package ch.axonivy.kura.anki;

class AnkiMessage
{
  private static final byte ANKI_VEHICLE_MSG_C2V_SET_LIGHTS = 0x1d;
  private static final byte ANKI_VEHICLE_MSG_C2V_SET_SPEED = 0x24;
  private static final byte ANKI_VEHICLE_MSG_C2V_CHANGE_LANE = 0x25;
  private static final byte ANKI_VEHICLE_MSG_C2V_SDK_MODE = (byte) 0x90;
  private static final byte ANKI_VEHICLE_MSG_C2V_SET_OFFSET_FROM_ROAD_CENTER = 0x2c;
  
  private byte msgId;
  private StringBuilder body = new StringBuilder();

  static AnkiMessage createSetLightsMessage(Light light, boolean on)
  {    
    byte lightsMask = (byte)(0x01 << light.ordinal());
    if (on)
    {
      lightsMask += 0x10 << light.ordinal();
    }
    AnkiMessage msg = new AnkiMessage(ANKI_VEHICLE_MSG_C2V_SET_LIGHTS);
    msg.appendUInt8(lightsMask);
    return msg;
  }
  
  static AnkiMessage createSetSdkModeMessage(boolean on, byte flags)
  {
    AnkiMessage msg = new AnkiMessage(ANKI_VEHICLE_MSG_C2V_SDK_MODE);
    msg.appendBoolean(on);
    msg.appendUInt8(flags);
    return msg;
  }
  
  static AnkiMessage createSetSpeedMessage(int speedInMillimeterPerSecond, int accelarationInMillimeterPerSquareSecond, boolean respect_road_piece_speed_limit)
  {
    AnkiMessage msg = new AnkiMessage(ANKI_VEHICLE_MSG_C2V_SET_SPEED);
    msg.appendInt16(speedInMillimeterPerSecond);
    msg.appendInt16(accelarationInMillimeterPerSquareSecond);
    msg.appendBoolean(respect_road_piece_speed_limit);
    return msg;
  }
  
  public static AnkiMessage createSetOffsetFromRoadCenterMessage(float offset)
  {
    AnkiMessage msg = new AnkiMessage(ANKI_VEHICLE_MSG_C2V_SET_OFFSET_FROM_ROAD_CENTER);
    msg.appendfloat(offset);
    return msg;
  }
  
  public static AnkiMessage createChangeLaneMessage(int horizontalSpeed, int horizontalAccelaration, float offset)
  {
    AnkiMessage msg = new AnkiMessage(ANKI_VEHICLE_MSG_C2V_CHANGE_LANE);
    msg.appendInt16(horizontalSpeed);
    msg.appendInt16(horizontalAccelaration);
    msg.appendfloat(offset);
    msg.appendUInt8((byte)0);
    msg.appendUInt8((byte)0);
    return msg;
  }


  String toHexString()
  {
    StringBuilder builder = new StringBuilder();
    int length = 1+body.length()/2;
    builder.append(toHexString((byte)length));
    builder.append(toHexString(msgId));
    builder.append(body.toString());
    return builder.toString();
  }

  private AnkiMessage(byte msgId)
  {
    this.msgId = msgId;
  }
  
  public static void main(String[] args)
  {
    AnkiMessage message = new AnkiMessage((byte)0);
    message.appendfloat(0.0f);
    message.appendfloat(1.0f);
    message.appendfloat(2.0f);
    message.appendfloat(-1.0f);
    message.appendfloat(-2.0f);    
    System.out.println(message.toHexString());
  }

  private void appendfloat(float offset)
  {
    int value = Float.floatToRawIntBits(offset);
    appendInt32(value);
  }

  private void appendInt32(int value)
  {
    appendUInt8((byte)(value & 0xFF));
    appendUInt8((byte)((value >> 8) & 0xFF));
    appendUInt8((byte)((value >> 16) & 0xFF));
    appendUInt8((byte)((value >> 24) & 0xFF));
  }

  private void appendInt16(int value)
  {
    // change endian
    appendUInt8((byte)(value & 0xFF));
    appendUInt8((byte)(value >> 8));
  }

  private void appendUInt8(byte value)
  {
    body.append(toHexString(value));
  }
  
  private void appendBoolean(boolean value)
  {
    appendUInt8((byte)(value?1:0));
  }

  private String toHexString(byte value)
  {
    int i = value & 0xFF; // ensure that 0x80 is not negative
    String hex = Integer.toHexString(i);
    if (hex.length()==1)
    {
      return "0"+hex;
    }
    return hex;
  }
}
