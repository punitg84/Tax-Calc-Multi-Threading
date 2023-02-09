package taxcalcmultithread.view;

import java.sql.SQLException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.collection.ItemCollection;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.controllers.ThreadController;
import taxcalcmultithread.models.Item;

@Log4j2
public class Application {

  private final ItemCollectionController itemCollectionController;
  private final ThreadController threadController;

  public Application() throws SQLException {
    final DbRepo dbRepo = new DbRepo();

    final SharedBufferController sharedBufferController = SharedBufferController.builder()
        .sharedBuffer(new SharedBuffer())
        .build();

    itemCollectionController = ItemCollectionController.builder()
        .itemCollection(new ItemCollection())
        .build();

    threadController = ThreadController.builder()
        .dbRepo(dbRepo)
        .itemCollectionController(itemCollectionController)
        .sharedBufferController(sharedBufferController)
        .build();
  }

  public void startProcessing() throws Exception {
    long start = System.nanoTime();
    threadController.startProcessing();
    long end = System.nanoTime();
    log.info("Execution time - "+ (end-start));
    printData();
  }

  public void printData() {
    final List<Item> itemList = itemCollectionController.getItemList();
//    itemList.stream().forEach(log::info);
    log.info(itemList.size());
  }

  public void run() {
    try {
      startProcessing();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

}
