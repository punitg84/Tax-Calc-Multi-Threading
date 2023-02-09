package taxcalcmultithread.threads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Log4j2
@Builder
public class ConsumerThread extends Thread {

  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;
  private DbRepo dbRepo;

  @Override
  public void run() {
    int cnt = 0;
    while (true) {
      synchronized (sharedBufferController) {
        log.info("Consuming");
        if (sharedBufferController.isEmpty() && dbRepo.isCompleted()) {
          break;
        }
        try {
          final Item item = sharedBufferController.removeItem();
          item.setTaxedCost(item.calcTaxedCost());
          itemCollectionController.addItem(item);
          cnt++;
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException("Error while processing new item",e);
        }
      }
    }
    log.info("Total consume - "+cnt);
  }

}
