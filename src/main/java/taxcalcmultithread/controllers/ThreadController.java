package taxcalcmultithread.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.repositries.DbRepo;

@AllArgsConstructor
@Builder
public class ThreadController {

  private DbRepo dbRepo;
  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  public void startProcessing() throws Exception {
    final ProducerThreadController producerThreadController = ProducerThreadController.builder()
        .sharedBufferController(sharedBufferController)
        .dbRepo(dbRepo)
        .build();

    final ConsumerThreadController consumerThreadController = ConsumerThreadController.builder()
        .sharedBufferController(sharedBufferController)
        .itemCollectionController(itemCollectionController)
        .build();

    producerThreadController.startProcessing();

    consumerThreadController.startProcessing();

    producerThreadController.joinThreads();

    consumerThreadController.joinThreads();
  }

}
