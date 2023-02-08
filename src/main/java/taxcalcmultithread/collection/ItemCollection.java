package taxcalcmultithread.collection;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import taxcalcmultithread.models.Item;

@Getter
public class ItemCollection {

  private final List<Item> itemList = new ArrayList<>();

}