package ch.ivyteam.hipster.anki.message;

public class AnkiMessageWriter {

	private StringBuilder messageBody = new StringBuilder();

	public void appendUInt8(byte value) {
		if (messageBody.length() > 0)
		{
			messageBody.append(" ");
		}
		messageBody.append(toHexString(value));
	}

	public void appendBoolean(boolean value) {
		appendUInt8(value ? (byte) 1 : (byte) 0);
	}

	public void appendUInt16(int value) {
		appendUInt8((byte) (value & 0xFF));
		appendUInt8((byte) ((value >> 8) & 0xFF));
	}

    public void appendFloat(float floatValue)
    {
      int value = Float.floatToRawIntBits(floatValue);
      appendInt32(value);
    }

	public void appendInt32(int value)
	{
	  appendUInt8((byte)(value & 0xFF));
	  appendUInt8((byte)((value >> 8) & 0xFF));
	  appendUInt8((byte)((value >> 16) & 0xFF));
	  appendUInt8((byte)((value >> 24) & 0xFF));
	}

	private String toHexString(byte value) {
		int i = value & 0xFF; // ensure that 0x80 is not negative
		String hex = Integer.toHexString(i);
		if (hex.length() == 1) {
			return "0" + hex;
		}
		return hex;
	}

	public String getHexString() {
		return messageBody.toString();
	}


}
