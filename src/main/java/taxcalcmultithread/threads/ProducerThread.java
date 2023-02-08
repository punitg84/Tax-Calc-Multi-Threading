package taxcalcmultithread.threads;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.SharedBufferController;

@AllArgsConstructor
@Log4j2
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
        log.info("Producer");
        try {
          sharedBufferController.addItem(dBRepo.getNext());
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
