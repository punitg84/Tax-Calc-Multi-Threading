package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.ExceptionMessage.THREAD_INTERRUPTION_EXCEPTION;
import static taxcalcmultithread.constants.Threads.NO_OF_CONSUMER_THREAD;
import static taxcalcmultithread.constants.Threads.NO_OF_PRODUCER_THREAD;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.threads.ConsumerThread;
import taxcalcmultithread.threads.ProducerThread;

@AllArgsConstructor
@Builder
public class ThreadController {

  private DbRepo dbRepo;
  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  public void startProcessing() throws Exception {
    final List<ProducerThread> producers = new ArrayList<>(NO_OF_PRODUCER_THREAD);
    final List<ConsumerThread> consumers = new ArrayList<>(NO_OF_CONSUMER_THREAD);

    for(int i = 1;i<=NO_OF_PRODUCER_THREAD;i++){
      producers.add(ProducerThread.builder()
          .sharedBufferController(sharedBufferController)
          .dbRepo(dbRepo)
          .build());
    }

    for(int i = 1;i<=NO_OF_CONSUMER_THREAD;i++){
      consumers.add(ConsumerThread.builder()
          .itemCollectionController(itemCollectionController)
          .sharedBufferController(sharedBufferController)
          .build());
    }

    producers.stream().forEach(Thread::start);
    consumers.stream().forEach(Thread::start);

    try {
      for(final ProducerThread producer:producers){
        producer.join();
      }
      for(final ConsumerThread consumer:consumers){
        consumer.join();
      }
    } catch (Exception e) {
      throw new Exception(THREAD_INTERRUPTION_EXCEPTION, e);
    }
  }

}
