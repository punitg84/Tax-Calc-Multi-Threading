package taxcalcmultithread.threads;

import static taxcalcmultithread.constants.ExceptionMessage.ALL_ITEM_PRODUCED_EXCEPTION;
import static taxcalcmultithread.constants.ExceptionMessage.ERROR_WHILE_PROCESSING_ITEM;

import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.models.Item;
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
    try {

      while(true){

        Item item = null;

        synchronized (dbRepo){
          item = dbRepo.getNext();
        }

        synchronized (sharedBufferController) {
          sharedBufferController.addItem(item);
        }

      }
    } catch (Exception e) {
      if (e.getMessage().equals(ALL_ITEM_PRODUCED_EXCEPTION)) {

        synchronized (sharedBufferController) {
          sharedBufferController.incProducerCompleted();
        }

      } else {
        throw new RuntimeException(ERROR_WHILE_PROCESSING_ITEM, e);
      }
    }
  }

}
