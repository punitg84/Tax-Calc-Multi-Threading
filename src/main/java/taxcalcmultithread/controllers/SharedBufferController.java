package taxcalcmultithread.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Builder
public class SharedBufferController {

  private SharedBuffer sharedBuffer;

  public boolean isFull() {
    return sharedBuffer.getList().size() == SharedBuffer.BUFFER_CAPACITY;
  }

  public void addItem(final Item item) throws InterruptedException {
    while (isFull()) {
      wait();
    }
    sharedBuffer.getList().add(item);
    notifyAll();
  }

  public boolean isEmpty() {
    return sharedBuffer.getList().isEmpty();
  }

  public Item removeItem() throws InterruptedException {
    while (isEmpty()) {
      wait();
    }
    final Item item = sharedBuffer.getList().remove(0);
    notifyAll();
    return item;
  }

}
