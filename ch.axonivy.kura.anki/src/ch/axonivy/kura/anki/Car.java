package ch.axonivy.kura.anki;

import java.util.UUID;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.bluetooth.BluetoothDevice;
import org.eclipse.kura.bluetooth.BluetoothGatt;
import org.eclipse.kura.bluetooth.BluetoothGattCharacteristic;
import org.eclipse.kura.bluetooth.BluetoothGattService;
import org.eclipse.kura.bluetooth.BluetoothLeNotificationListener;
import org.eclipse.kura.data.DataTransportService;
import org.eclipse.kura.data.DataTransportToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ivyteam.hipster.anki.iot.message.RawAnkiMessage;

class Car
{
  private BluetoothDevice device;
  private BluetoothGatt bluetoothGatt;
  private String writeCharacteristicHandle;
  private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);
  private static final UUID ANKI_SERVICE_UUID = UUID.fromString("BE15BEEF-6186-407E-8381-0BD89C4D8DF4");
  private static final UUID ANKI_CHARACTERISTIC_WIRTE_UUID = UUID.fromString("BE15BEE1-6186-407E-8381-0BD89C4D8DF4");
  private DataTransportService dataTransportService;

  Car(BluetoothDevice device, DataTransportService dataTransportService)
  {
    this.device = device;
    this.dataTransportService = dataTransportService;    
    bluetoothGatt = device.getBluetoothGatt();
  }

  static boolean isCar(BluetoothDevice device)
  {
    return device.getName().endsWith(" Drive");
  }
  
  boolean connect(String interfaceName) throws KuraException
  {
    try
    {
      bluetoothGatt.connect(interfaceName+":random");
      bluetoothGatt.setBluetoothLeNotificationListener(new CarListener());
      for (BluetoothGattService service : bluetoothGatt.getServices())
      {
        if (service.getUuid().equals(ANKI_SERVICE_UUID))
        {          
          for (BluetoothGattCharacteristic characteristic : bluetoothGatt.getCharacteristics(service.getStartHandle(), service.getEndHandle()))
          {
            if (characteristic.getUuid().equals(ANKI_CHARACTERISTIC_WIRTE_UUID))
            {
              writeCharacteristicHandle = characteristic.getValueHandle();
            }
          }
        }
      }
      activateNotifications();
      setSdkMode(true);
      resetOffsetFromRoadCenter();
      return true;
    }
    catch (KuraException ex)
    {
      LOGGER.error("Could not connect to car ", ex);
      return false;
    }
  }   
  
  public boolean isConnected()
  {
    try
    {
      return bluetoothGatt.checkConnection();
    }
    catch(KuraException ex)
    {
      LOGGER.error("Could not check connection of car "+this, ex);
      return false;
    }
  }

  private void setSdkMode(boolean on)
  {
    AnkiMessage msg = AnkiMessage.createSetSdkModeMessage(on, (byte)1);
    writeMessage(msg);
  }

  private void activateNotifications()
  {
    bluetoothGatt.writeCharacteristicValue("0x0e", "0100"); // activate notifications
  }
  
  void disconnect()
  {
    bluetoothGatt.disconnect();
  }
  
  
  @Override
  public boolean equals(Object obj)
  {
    if (this==obj)
    {
      return true;
    }
    if (!(obj instanceof Car))
    {
      return false;
    }
    
    Car other = (Car)obj;
    return other.device.getAdress().equals(device.getAdress());
  }
  
  @Override
  public int hashCode()
  {
    return device.getAdress().hashCode();
  }
  
  public String getName()
  {
    String address = getAddress();
    switch(address)
    {
      case "C7:E8:D2:22:BB:DE":
        return "Duke (Green)";
      case "D6:3C:A3:24:A0:F9":
        return "Ground Shock (Blue)";
      case "D1:53:02:F5:C6:DD":
        return "Skull (Black)";
      case "DC:FE:9C:BA:9E:84":
        return "Gurroian (Silver)";
      default:
        return "Unkown "+address;
    }
  }
  
  public String getAddress()
  {
    return device.getAdress();
  }
  
  public boolean isOnCharger()
  {
    byte[] bytes = device.getName().getBytes();
    return (bytes[0] & 0x40) > 0; 
  }
  
  public boolean isLowBattery()
  {
    byte[] bytes = device.getName().getBytes();
    return (bytes[0] & 0x20) > 0; 
  }
  
  public boolean isHighBattery()
  {
    byte[] bytes = device.getName().getBytes();
    return (bytes[0] & 0x10) > 0; 
  }
  
  public int getVersion()
  {
    byte[] bytes = device.getName().getBytes();
    int version = bytes[1] + bytes[2] << 8;
    return version;
  }
  
  public void setLight(Light light, boolean on)
  {
    AnkiMessage msg = AnkiMessage.createSetLightsMessage(light, on);
    writeMessage(msg);
  }
  
  public void setSpeed(int speedInMillimeterPerSecond)
  {
    AnkiMessage msg = AnkiMessage.createSetSpeedMessage(speedInMillimeterPerSecond, 20000, false);
    writeMessage(msg);
  }

  public void setOffset(float offset)
  {
    resetOffsetFromRoadCenter();
    AnkiMessage msg = AnkiMessage.createChangeLaneMessage(100, 20000, offset);    
    writeMessage(msg);
  }

  private void resetOffsetFromRoadCenter()
  {
    AnkiMessage msg = AnkiMessage.createSetOffsetFromRoadCenterMessage(0.0f);    
    writeMessage(msg);
  }
  
  private void writeMessage(AnkiMessage msg)
  {
    writeMessage(msg.toHexString());
  }
  
  public void handle(RawAnkiMessage rawAnkiMessage)
  {
    String rawMessage = rawAnkiMessage.getRawMessage();
    rawMessage = removeSpaces(rawMessage);
    writeMessage(rawMessage);
  }


  private String removeSpaces(String rawMessage)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos=0; pos < rawMessage.length(); pos++)
    {
      char ch = rawMessage.charAt(pos);
      if (ch !=' ')
      {
        builder.append(ch);
      }
    }
    return builder.toString();
  }
  
  private void writeMessage(String rawMessage)
  {
    LOGGER.debug("Write Message: "+rawMessage+" to car "+this);
    bluetoothGatt.writeCharacteristicValue(writeCharacteristicHandle, rawMessage);    
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("Car [name=");
    builder.append(getName());
    builder.append(", address=");
    builder.append(device.getAdress());
    builder.append(", version=");
    builder.append(getVersion());
    builder.append(", isOnCharger=");
    builder.append(isOnCharger());
    builder.append(", isLowBattery=");
    builder.append(isLowBattery());
    builder.append(", isHighBattery=");
    builder.append(isHighBattery());
    builder.append("]");
    return builder.toString();
  }
  
  private class CarListener implements BluetoothLeNotificationListener
  {
    private int messageId = 0;
    public CarListener()
    {
    }

    @Override
    public void onDataReceived(String handle, String value)
    {
      try
      {
        RawAnkiMessage message = new RawAnkiMessage(device.getAdress(), value);
        DataTransportToken token = dataTransportService.publish("anki", message.toBytes(), 1, false);
        LOGGER.info("Published message with payload "+message);
      }
      catch (Exception ex)
      {
        LOGGER.error("Error publishing MQTT message", ex);
      }
      
    }
  }
}
