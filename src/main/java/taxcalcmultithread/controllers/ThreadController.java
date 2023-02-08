package taxcalcmultithread.controllers;

import lombok.AllArgsConstructor;
import taxcalcmultithread.threads.ConsumerThread;
import taxcalcmultithread.threads.ProducerThread;

@AllArgsConstructor
public class ThreadController {

  private DBRepo dBRepo;
  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  public void startProcessing() throws InterruptedException {
    ConsumerThread consumerThread =
        new ConsumerThread(itemCollectionController, sharedBufferController, dBRepo);
    ProducerThread producerThread = new ProducerThread(dBRepo, sharedBufferController);

    consumerThread.start();
    producerThread.start();
    consumerThread.join();
    producerThread.join();
  }

}
