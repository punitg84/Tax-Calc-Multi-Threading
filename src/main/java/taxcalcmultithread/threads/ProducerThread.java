package taxcalcmultithread.threads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.SharedBufferController;

@AllArgsConstructor
@Log4j2
@Builder
public class ProducerThread extends Thread {

  private DBRepo dBRepo;
  private SharedBufferController sharedBufferController;

  @Override
  public void run() {
    while (true) {
      synchronized (sharedBufferController) {
        if (dBRepo.isCompleted()) {
          break;
        }
        try {
          sharedBufferController.addItem(dBRepo.getNext());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
