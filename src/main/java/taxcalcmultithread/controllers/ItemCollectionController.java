package taxcalcmultithread.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.collection.ItemCollection;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Builder
public class ItemCollectionController {

  private ItemCollection itemCollection;

  public void addItem(Item item) {
    itemCollection.getItemList().add(item);
  }

  public List<Item> getItemList() {
    return itemCollection.getItemList();
  }

}
