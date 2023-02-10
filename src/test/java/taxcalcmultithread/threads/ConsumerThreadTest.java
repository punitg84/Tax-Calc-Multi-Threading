package taxcalcmultithread.threads;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taxcalcmultithread.repositries.DbRepo;
import taxcalcmultithread.controllers.ItemCollectionController;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;
import taxcalcmultithread.models.RawItem;

@ExtendWith(MockitoExtension.class)
class ConsumerThreadTest {
  @Mock
  private ItemCollectionController itemCollectionControllerMock;
  @Mock
  private SharedBufferController sharedBufferControllerMock;
  @Mock
  private DbRepo dbRepoMock;
  @Mock
  private RawItem itemMock;
  @InjectMocks
  ConsumerThread consumerThreadMock;

  @Test
  void testRun() throws InterruptedException {

    when(dbRepoMock.isCompleted()).thenReturn(false,false,true);

    when(sharedBufferControllerMock.isEmpty()).thenReturn(true,true,true);

    when(sharedBufferControllerMock.removeItem()).thenReturn(itemMock);

    doNothing().when(itemMock).setTaxedCost(isA(Double.class));

    doNothing().when(itemCollectionControllerMock).addItem(isA(Item.class));

    consumerThreadMock.start();
    consumerThreadMock.join();

    verify(itemCollectionControllerMock,times(2)).addItem(isA(Item.class));
  }

}