package taxcalcmultithread.threads;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static taxcalcmultithread.constants.ExceptionMessage.ALL_ITEM_PRODUCED_EXCEPTION;

import java.sql.SQLException;
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
  private RawItem itemMock;
  @InjectMocks
  ConsumerThread consumerThreadMock;

  @Test
  void testRun() throws Exception {

    when(sharedBufferControllerMock.removeItem()).thenReturn(itemMock)
        .thenReturn(itemMock)
        .thenThrow(new Exception(ALL_ITEM_PRODUCED_EXCEPTION));

    doNothing().when(itemMock).setTaxedCost(isA(Double.class));

    doNothing().when(itemCollectionControllerMock).addItem(isA(Item.class));

    consumerThreadMock.start();
    consumerThreadMock.join();

    verify(itemCollectionControllerMock, times(2)).addItem(isA(Item.class));
  }

}