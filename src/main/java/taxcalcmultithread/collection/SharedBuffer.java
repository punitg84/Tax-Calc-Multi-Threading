package taxcalcmultithread.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.Setter;
import taxcalcmultithread.models.Item;

@Getter
@Setter
public class SharedBuffer {

  private List<Item> list = new LinkedList<>();
  public static final int BUFFER_CAPACITY = 2;
  private int producerCompleted=0;

}
