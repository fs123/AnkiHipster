package ch.ivyteam.hipster.anki.message;

public class AnkiMessageParser
{
  private int position = 0;
  private final String[] messageBody;
  private final String rawMessageBody;
  
  public AnkiMessageParser(String rawMessageBody)
  {
    this.messageBody = rawMessageBody.split(" ");
    this.rawMessageBody = rawMessageBody;
  }
  
  final int nextUInt8()
  {
    if (messageBody.length < position)
    {
      throw new IllegalStateException("End of message was reached. No more data to read.");
    }
    int value = Integer.parseInt(messageBody[position], 16);
    position++;
    return value;
  }

  final int nextUInt16()
  {
    if (messageBody.length < position)
    {
      throw new IllegalStateException("End of message was reached. No more data to read.");
    }
    int byte0 = nextUInt8();
    int byte1 = nextUInt8();
    int value = byte0 + (byte1 << 8);
    return value;
  }

  final float nextFloat()
  {
    if (messageBody.length < position + 3)
    {
      throw new IllegalStateException("End of message was reached. No more data to read.");
    }
    int byte0 = nextUInt8();
    int byte1 = nextUInt8();    
    int byte2 = nextUInt8();
    int byte3 = nextUInt8();
    int bits = byte0 + (byte1 << 8) + (byte2 << 16) + (byte3 << 24);
    float value = Float.intBitsToFloat(bits);
    return value;
  }
  
  final void skipNextBytes(int bytesToSkip)
  {    
    position = position+bytesToSkip;
    if (position > messageBody.length)
    {
      throw new IllegalStateException("End of message was reached. No more data to read.");
    }
  }

  public String getMessageBody()
  {
    return rawMessageBody;
  }

  public void checkEnd()
  {
    if (position < messageBody.length)
    {
      throw new IllegalStateException("End of message was not reached. There are still data to read.");
    }
  }
}
