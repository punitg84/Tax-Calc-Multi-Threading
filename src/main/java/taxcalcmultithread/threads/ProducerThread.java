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
    while (true) {
      synchronized (sharedBufferController) {
        if (dbRepo.isCompleted()) {
          break;
        }
        try {
          sharedBufferController.addItem(dbRepo.getNext());
        } catch (Exception e) {
          throw new RuntimeException("Error while getting new item",e);
        }
      }
    }
  }

}
