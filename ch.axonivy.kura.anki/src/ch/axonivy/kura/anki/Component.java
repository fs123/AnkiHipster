package ch.axonivy.kura.anki;

import org.eclipse.kura.bluetooth.BluetoothAdapter;
import org.eclipse.kura.bluetooth.BluetoothService;
import org.eclipse.kura.data.DataTransportService;
import org.eclipse.kura.data.transport.listener.DataTransportListener;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Component implements Runnable
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Component.class);
  private static final String APP_ID = "ch.axonivy.kura.anki";

  private BluetoothService bluetoothService;
  private DataTransportService dataTransportService;
  private String interfaceName = "hci0";
  private CarScanner carScanner;
  private DataTransportListener dataTransportListener = new AnkiCloudClientListener(this);  

  protected void activate(ComponentContext componentContext)
  {
    dataTransportService.addDataTransportListener(dataTransportListener);
    BluetoothAdapter bluetoothAdapter = bluetoothService.getBluetoothAdapter(interfaceName);
    if (bluetoothAdapter != null)
    {
      carScanner = new CarScanner(bluetoothAdapter, dataTransportService);
      carScanner.start();
      new Thread(this).start();
    }
    else
    {
      LOGGER.warn("No bluetooth adapter found for interface "+ interfaceName);
    }
    LOGGER.info("Bundle " + APP_ID + " has started!");
  }

  protected void deactivate(ComponentContext componentContext)
  {
    dataTransportService.removeDataTransportListener(dataTransportListener);
    if (carScanner != null)
    {
      carScanner.stop();
      carScanner = null;
    }
    LOGGER.info("Bundle " + APP_ID + " has stopped!");

  }

  public void setBluetoothService(BluetoothService bluetoothService)
  {
    this.bluetoothService = bluetoothService;
  }

  public void unsetBluetoothService(BluetoothService bluetoothService)
  {
    this.bluetoothService = null;
  }
  
  public void setDataTransportService(DataTransportService dataTransportService)
  {
    this.dataTransportService = dataTransportService;
  }
  
  public void unsetDataTransportService(DataTransportService dataTransportService)
  {
    this.dataTransportService = null;
  }

  @Override
  public void run()
  {
    LOGGER.info("Thread started");
    try
    {
      while (carScanner != null)
      {
//        setSpeed(2000);
        sleep();
        carScanner.checkConnections();
//        setOffset(70.0f);
//        for (Light light : EnumSet.allOf(Light.class))
//        {
//          setLights(light, true);
//          setOffset(-140.0f);
//          sleep();
//          setLights(light, false);
//          setOffset(140.0f);
//          sleep();
//        }
      }
    }
    catch(Exception ex)
    {
      LOGGER.error("Exception in thread", ex);
    }
    finally
    {
      LOGGER.info("Thread stopped");
    }    
  }

  private void sleep()
  {
    try
    {
      Thread.sleep(10000);
    }
    catch (InterruptedException ex)
    {
    }
  }

  public Car getCar(String address)
  {
    return carScanner.getCar(address);
  }
}
