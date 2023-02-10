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
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;
import taxcalcmultithread.models.RawItem;

@ExtendWith(MockitoExtension.class)
class ProducerThreadTest {
  @Mock
  private SharedBufferController sharedBufferControllerMock;
  @Mock
  private DbRepo dbRepoMock;
  @Mock
  private RawItem itemMock;
  @InjectMocks
  ProducerThread producerThreadMock;

  @Test
  void testRun() throws Exception {

    when(dbRepoMock.getNext()).thenReturn(itemMock)
        .thenReturn(itemMock)
        .thenThrow(new Exception(ALL_ITEM_PRODUCED_EXCEPTION));

    doNothing().when(sharedBufferControllerMock).addItem(isA(Item.class));

    producerThreadMock.start();
    producerThreadMock.join();

    verify(sharedBufferControllerMock,times(2)).addItem(isA(Item.class));
  }

}