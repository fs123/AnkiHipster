package ch.axonivy.kura.anki;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.bluetooth.BluetoothAdapter;
import org.eclipse.kura.bluetooth.BluetoothDevice;
import org.eclipse.kura.bluetooth.BluetoothLeScanListener;
import org.eclipse.kura.data.DataTransportService;
import org.osgi.service.component.ComponentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CarScanner implements BluetoothLeScanListener
{
  private static final Logger LOGGER = LoggerFactory.getLogger(CarScanner.class);

  private ScheduledExecutorService worker;
  private BluetoothAdapter bluetoothAdapter;
  private ScheduledFuture<?> handle;
  private String interfaceName = "hci0";
  private List<Car> cars = new ArrayList<Car>();

  private DataTransportService dataTransportService;

  CarScanner(BluetoothAdapter bluetoothAdapter, DataTransportService dataTransportService)
  {
    this.bluetoothAdapter = bluetoothAdapter;
    this.dataTransportService = dataTransportService;
  }

  void start()
  {
    worker = Executors.newSingleThreadScheduledExecutor();

    try
    {
      LOGGER.info("Bluetooth adapter interface => " + interfaceName);
      LOGGER.info("Bluetooth adapter address => " + bluetoothAdapter.getAddress());
      LOGGER.info("Bluetooth adapter le enabled => " + bluetoothAdapter.isLeReady());

      if (!bluetoothAdapter.isEnabled())
      {
        LOGGER.info("Enabling bluetooth adapter...");
        bluetoothAdapter.enable();
        LOGGER.info("Bluetooth adapter address => " + bluetoothAdapter.getAddress());
      }
      handle = worker.scheduleAtFixedRate(new Runnable()
        {
          @Override
          public void run()
          {
            checkScan();
          }
        }, 0, 30, TimeUnit.SECONDS);
    }
    catch (Exception e)
    {
      LOGGER.error("Error starting car scanner", e);
      throw new ComponentException(e);
    }
  }

  void stop()
  {
    synchronized (this)
    {
      for (Car car : cars)
      {
        car.disconnect();
      }
    }
    if (bluetoothAdapter != null)
    {
      if (bluetoothAdapter.isScanning())
      {
        LOGGER.debug("m_bluetoothAdapter.isScanning");
        bluetoothAdapter.killLeScan();
      }
    }

    if (handle != null)
    {
      handle.cancel(true);
    }

    if (worker != null)
    {
      worker.shutdown();
    }

    // cancel bluetoothAdapter
    bluetoothAdapter = null;
  }
  
  private void checkScan()
  {
    bluetoothAdapter.startLeScan(this);
  }

  @Override
  public void onScanFailed(int arg0)
  {
    LOGGER.warn("Scannning failed. Error code "+arg0);
  }

  @Override
  public void onScanResults(List<BluetoothDevice> devices)
  {
    try
    {
      for (BluetoothDevice device : devices)
      {
        LOGGER.info("Found Bluetooth Device " + device.getName() +" ["+device.getAdress()+"]");
        if (Car.isCar(device))
        {
          Car car = new Car(device, dataTransportService);
          boolean unknownCar;
          synchronized(this)
          {
            unknownCar = !cars.contains(car);
          }
          if (unknownCar)
          {
            boolean connected = car.connect(interfaceName);
            if (connected)
            {
              LOGGER.info("Connected to car "+car);
              synchronized(this)
              {
                if (!cars.contains(car))
                {
                  cars.add(car);
                }
              }
            }
          }
        }
      }
    }
    catch(Exception ex)
    {
      LOGGER.error("Error during scan ", ex);
    }
  }

  synchronized List<Car> getCars()
  {
    return new ArrayList<>(cars);
  }

  public void checkConnections()
  {
    List<Car> connectedCars = getCars();
    LOGGER.info("Connected cars "+connectedCars);
    List<Car> notConnectedCars = new ArrayList<>();
    for (Car car : connectedCars)
    {
      if (!car.isConnected())
      {
        notConnectedCars.add(car);
      }
    }
    if (!notConnectedCars.isEmpty())
    {
      synchronized (this)
      {
        
        LOGGER.info("Remove cars that are no longer connected "+notConnectedCars);
        cars.removeAll(notConnectedCars);
      }
    }
  }

  public Car getCar(String address)
  {
    return cars.stream().filter(car->address.equals(car.getAddress())).findAny().orElse(null);    
  }
}
