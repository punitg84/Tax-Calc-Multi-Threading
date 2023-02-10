package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.ExceptionMessage.THREAD_INTERRUPTION_EXCEPTION;
import static taxcalcmultithread.constants.Threads.NO_OF_CONSUMER_THREAD;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.threads.ConsumerThread;

@Builder
@AllArgsConstructor
public class ConsumerThreadController {

  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  private List<ConsumerThread> consumers;

  public void startProcessing() throws Exception {
    consumers = new ArrayList<>(NO_OF_CONSUMER_THREAD);

    for (int i = 1; i <= NO_OF_CONSUMER_THREAD; i++) {
      consumers.add(ConsumerThread.builder()
          .itemCollectionController(itemCollectionController)
          .sharedBufferController(sharedBufferController)
          .build());
    }

    consumers.stream().forEach(Thread::start);
  }

  public void joinThreads() throws Exception {
    try {
      for (final ConsumerThread consumer : consumers) {
        consumer.join();
      }
    } catch (Exception e) {
      throw new Exception(THREAD_INTERRUPTION_EXCEPTION, e);
    }
  }

}
