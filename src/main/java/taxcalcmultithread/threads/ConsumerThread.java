package taxcalcmultithread.threads;

import static taxcalcmultithread.constants.Threads.NO_OF_PRODUCER_THREAD;

import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;
import taxcalcmultithread.repositries.DbRepo;

@AllArgsConstructor
@Log4j2
@Builder
public class ConsumerThread extends Thread {

  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;
  private DbRepo dbRepo;

  @Override
  public void run() {
    while (true) {
      try {
        log.info("Consuming");
        Item item = null;
        synchronized (sharedBufferController) {
          if (sharedBufferController.isEmpty() &&
              sharedBufferController.getProducerCompleted() == NO_OF_PRODUCER_THREAD) {
            log.info("Completed");
            break;
          }
          item = sharedBufferController.removeItem();
        }
        item.setTaxedCost(item.calcTaxedCost());
        itemCollectionController.addItem(item);
      } catch (SQLException e){
        if (e.getMessage().equals("All Item Processed")) {
          break;
        } else {
          throw new RuntimeException("Error while processing item", e);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException("Error while processing new item", e);
      }
    }
  }

}
