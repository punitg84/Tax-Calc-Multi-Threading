package taxcalcmultithread.collection;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import taxcalcmultithread.models.Item;

@Getter
public class SharedBuffer {
  List<Item> list = new LinkedList<>();
  public static final int BUFFER_CAPACITY = 2;
}
