package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.Threads.NO_OF_PRODUCER_THREAD;

import java.sql.SQLException;
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

  public void incProducerCompleted(){
    sharedBuffer.setProducerCompleted(sharedBuffer.getProducerCompleted()+1);
    notifyAll();
  }

  public int getProducerCompleted(){
    return sharedBuffer.getProducerCompleted();
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

  public Item removeItem() throws InterruptedException, SQLException {
    while (isEmpty() &&
        getProducerCompleted() != NO_OF_PRODUCER_THREAD) {
      wait();
    }
    if(isEmpty()){
      throw new SQLException("All Item Processed");
    }
    final Item item = sharedBuffer.getList().remove(0);
    notifyAll();
    return item;
  }

}
