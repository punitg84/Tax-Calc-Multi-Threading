package taxcalcmultithread.view;

import java.sql.SQLException;
import java.util.List;
import taxcalcmultithread.collection.ItemCollection;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.controllers.ThreadController;
import taxcalcmultithread.models.Item;

public class Application {

  private ItemCollectionController itemCollectionController;
  private ThreadController threadController;

  public Application() throws SQLException {
    DBRepo dBRepo = new DBRepo();
    SharedBufferController sharedBufferController = new SharedBufferController(new SharedBuffer());
    itemCollectionController = new ItemCollectionController(new ItemCollection());
    threadController = new ThreadController(dBRepo,itemCollectionController,sharedBufferController);
  }

  public void startProcessing() throws InterruptedException {
    threadController.startProcessing();
    printData();
  }

  public void printData(){
    List<Item> itemList = itemCollectionController.getItemList();
    itemList.stream().forEach(System.out::println);
  }

  public void run() throws InterruptedException {
    startProcessing();
  }

}
