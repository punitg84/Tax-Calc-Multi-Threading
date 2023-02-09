package taxcalcmultithread.threads;

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
    } catch (SQLException e) {
      if (e.getMessage().equals("All Item Processed")) {

        synchronized (sharedBufferController) {
          sharedBufferController.incProducerCompleted();
        }

      } else {
        throw new RuntimeException("Error while processing item", e);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error while adding item", e);
    }
  }

}
