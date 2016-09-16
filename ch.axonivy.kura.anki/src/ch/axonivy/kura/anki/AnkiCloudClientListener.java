package ch.axonivy.kura.anki;


import java.io.IOException;

import org.eclipse.kura.data.DataTransportToken;
import org.eclipse.kura.data.transport.listener.DataTransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ivyteam.hipster.anki.iot.message.RawAnkiMessage;

public class AnkiCloudClientListener implements DataTransportListener
{
  private static final Logger logger = LoggerFactory.getLogger(AnkiCloudClientListener.class);
  private Component component;


  public AnkiCloudClientListener(Component component)
  {
    this.component = component;
  }

  @Override
  public void onConfigurationUpdated(boolean arg0)
  {
    logger.info("Configuration updated");
  }

  @Override
  public void onConfigurationUpdating(boolean arg0)
  {
    logger.info("Configuration updating");    
  }

  @Override
  public void onConnectionEstablished(boolean arg0)
  {
    logger.info("Connection established");
  }

  @Override
  public void onConnectionLost(Throwable arg0)
  {
    logger.info("Connection lost");
  }

  @Override
  public void onDisconnected()
  {
    logger.info("Disconnected");
  }

  @Override
  public void onDisconnecting()
  {
    logger.info("Disconnecting");
  }

  @Override
  public void onMessageArrived(String arg0, byte[] arg1, int arg2, boolean arg3)
  {
    try
    {
      RawAnkiMessage rawAnkiMessage = RawAnkiMessage.fromBytes(arg1);
      component.getCar(rawAnkiMessage.getAddress()).handle(rawAnkiMessage);
    }
    catch (IOException ex)
    {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }

  @Override
  public void onMessageConfirmed(DataTransportToken arg0)
  {
  }


}
