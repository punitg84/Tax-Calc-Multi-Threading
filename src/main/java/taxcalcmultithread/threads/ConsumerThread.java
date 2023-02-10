package taxcalcmultithread.threads;

import static taxcalcmultithread.constants.ExceptionMessage.ALL_ITEM_PRODUCED_EXCEPTION;
import static taxcalcmultithread.constants.ExceptionMessage.ERROR_WHILE_PROCESSING_ITEM;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Log4j2
@Builder
public class ConsumerThread extends Thread {

  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;

  @Override
  public void run() {
    try {

      while (true) {

        Item item;

        synchronized (sharedBufferController) {
          item = sharedBufferController.removeItem();
        }

        item.setTaxedCost(item.calcTaxedCost());

        synchronized (itemCollectionController) {
          itemCollectionController.addItem(item);
        }
      }
    } catch (Exception e) {
      if (e.getMessage().equals(ALL_ITEM_PRODUCED_EXCEPTION)) {
        log.info("Completed" + this.getName());
      } else {
        throw new RuntimeException(ERROR_WHILE_PROCESSING_ITEM, e);
      }
    }
  }

}
