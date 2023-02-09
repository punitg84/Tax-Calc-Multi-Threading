package taxcalcmultithread.threads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.controllers.SharedBufferController;

@AllArgsConstructor
@Log4j2
@Builder
public class ProducerThread extends Thread {

  private DbRepo dbRepo;
  private SharedBufferController sharedBufferController;

  @Override
  public void run() {
    int cnt = 0;
    while (true) {
      synchronized (sharedBufferController) {
        log.info("Producing");
        if (dbRepo.isCompleted()) {
          break;
        }
        try {
          sharedBufferController.addItem(dbRepo.getNext());
          cnt++;
          Thread.sleep(100);
        } catch (Exception e) {
          throw new RuntimeException("Error while getting new item",e);
        }
      }
    }
    log.info("Total produce - "+cnt);
  }

}
