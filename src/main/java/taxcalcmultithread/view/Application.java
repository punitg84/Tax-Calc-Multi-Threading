package taxcalcmultithread.view;

import java.sql.SQLException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import taxcalcmultithread.collection.ItemCollection;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.controllers.ThreadController;
import taxcalcmultithread.models.Item;

@Log4j2
public class Application {

  private ItemCollectionController itemCollectionController;
  private ThreadController threadController;

  public Application() throws SQLException {
    DBRepo dBRepo = new DBRepo();

    SharedBufferController sharedBufferController = SharedBufferController.builder()
        .sharedBuffer(new SharedBuffer())
        .build();

    itemCollectionController = ItemCollectionController.builder()
        .itemCollection(new ItemCollection())
        .build();

    threadController = ThreadController.builder()
        .dBRepo(dBRepo)
        .itemCollectionController(itemCollectionController)
        .sharedBufferController(sharedBufferController)
        .build();
  }

  public void startProcessing() throws Exception {
    threadController.startProcessing();
    printData();
  }

  public void printData() {
    List<Item> itemList = itemCollectionController.getItemList();
    itemList.stream().forEach(System.out::println);
  }

  public void run() {
    try {
      startProcessing();
    }catch (Exception e){
      log.error(e.getMessage());
    }
  }

}
