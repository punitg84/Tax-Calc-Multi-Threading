package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.ExceptionMessage.ALL_ITEM_PRODUCED_EXCEPTION;
import static taxcalcmultithread.constants.Threads.NO_OF_PRODUCER_THREAD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.models.Item;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class SharedBufferController {

  private SharedBuffer sharedBuffer;
  private int producerCompleted;

  public void incProducerCompleted() {
    producerCompleted++;
    if (producerCompleted == NO_OF_PRODUCER_THREAD) {
      notifyAll();
    }
  }

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

  public Item removeItem() throws Exception {
    while (isEmpty() && getProducerCompleted() != NO_OF_PRODUCER_THREAD) {
      wait();
    }
    if (isEmpty()) {
      throw new Exception(ALL_ITEM_PRODUCED_EXCEPTION);
    }
    final Item item = sharedBuffer.getList().remove(0);
    notifyAll();
    return item;
  }

}
