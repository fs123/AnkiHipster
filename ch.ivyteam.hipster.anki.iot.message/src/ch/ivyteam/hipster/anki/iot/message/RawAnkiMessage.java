package ch.ivyteam.hipster.anki.iot.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RawAnkiMessage
{
  private final String address;
  private final String rawMessage;
  
  public RawAnkiMessage(String address, String rawMessage)
  {
    this.address = address;
    this.rawMessage = rawMessage;
  }
  
  public String getAddress()
  {
    return address;
  }

  public String getRawMessage()
  {
    return rawMessage;
  }
  
  @Override
  public String toString()
  {
    return "RawAnkiMessage [address="+address+", rawMessage="+rawMessage+"]";
  }
  
  public byte[] toBytes() throws IOException
  {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos))
    {
      oos.writeUTF(address);
      oos.writeUTF(rawMessage);
      oos.flush();
      return baos.toByteArray();
    }
  }
  
  public static RawAnkiMessage fromBytes(byte[] message) throws IOException
  {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(message); ObjectInputStream ois = new ObjectInputStream(bais))
    {
      return new RawAnkiMessage(ois.readUTF(), ois.readUTF());
    }
  }
}
