package stratego.mode;

import stratego.network.Networker;

public interface Mode{

  private Runnable worker;

  public void startWorker(Networker online);

  public void terminate();

}
