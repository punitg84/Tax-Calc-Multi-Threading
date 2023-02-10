package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.ExceptionMessage.THREAD_INTERRUPTION_EXCEPTION;
import static taxcalcmultithread.constants.Threads.NO_OF_PRODUCER_THREAD;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.threads.ProducerThread;

@AllArgsConstructor
@Builder
public class ProducerThreadController {

  private DbRepo dbRepo;
  private SharedBufferController sharedBufferController;
  private List<ProducerThread> producers;

  public void startProcessing() throws Exception {
    producers = new ArrayList<>(NO_OF_PRODUCER_THREAD);

    for (int i = 1; i <= NO_OF_PRODUCER_THREAD; i++) {
      producers.add(ProducerThread.builder()
          .sharedBufferController(sharedBufferController)
          .dbRepo(dbRepo)
          .build());
    }

    producers.stream().forEach(Thread::start);
  }

  public void joinThreads() throws Exception {
    try {
      for (final ProducerThread producer : producers) {
        producer.join();
      }
    } catch (Exception e) {
      throw new Exception(THREAD_INTERRUPTION_EXCEPTION, e);
    }
  }

}
