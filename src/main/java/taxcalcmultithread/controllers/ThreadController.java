package taxcalcmultithread.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.threads.ConsumerThread;
import taxcalcmultithread.threads.ProducerThread;

@AllArgsConstructor
@Builder
public class ThreadController {

  private DbRepo dbRepo;
  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  public void startProcessing() throws Exception {
    final ConsumerThread consumerThread = ConsumerThread.builder()
        .itemCollectionController(itemCollectionController)
        .sharedBufferController(sharedBufferController)
        .dbRepo(dbRepo)
        .build();

    final ProducerThread producerThread = ProducerThread.builder()
        .dbRepo(dbRepo)
        .sharedBufferController(sharedBufferController).build();

    try {
      consumerThread.start();
      producerThread.start();

      consumerThread.join();
      producerThread.join();
    } catch (Exception e) {
      throw new Exception("The Processing of threads was interrupted", e);
    }
  }

}
