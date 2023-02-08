package taxcalcmultithread.threads;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Log4j2
public class ConsumerThread extends Thread{
  private ItemCollectionController itemCollectionController;
  private SharedBufferController sharedBufferController;
  private DBRepo dbRepo;

  @Override
  public void run(){
    synchronized (sharedBufferController){
      while (!sharedBufferController.isEmpty()|| !dbRepo.isCompleted()) {
        log.info("Consumer");
        try {
          Item item = sharedBufferController.removeItem();
          item.setTaxedCost(item.calcTaxedCost());
          itemCollectionController.addItem(item);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
