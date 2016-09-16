package ch.ivyteam.hipster.anki.message;

public class AnkiMessage
{
  public int size;
  public int msg_id;
  
  public AnkiCar ankiCar;

  public AnkiMessage(AnkiCar ankiCar, AnkiMessageParser parser)
  {
    this.ankiCar = ankiCar;
    this.size = parser.nextUInt8();
    this.msg_id = parser.nextUInt8();
  }

  public AnkiMessage(AnkiCar ankiCar, int size, int msg_id)
  {
    this.ankiCar = ankiCar;
	this.size = size;
	this.msg_id = msg_id;
  }

  public AnkiMessage() 
  {
  }

  @Override
  public String toString()
  {
    return "AnkiMessage [ankiCar=" + ankiCar + ", size="+size+", msg_id="+msg_id + "]";
  }

  public String getTopic() {
	  return "";
  }

  public void writeTo(AnkiMessageWriter writer) 
  {
	writer.appendUInt8((byte)size);
	writer.appendUInt8((byte)msg_id);
  }
}
