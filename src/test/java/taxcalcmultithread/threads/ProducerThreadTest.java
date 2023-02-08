package taxcalcmultithread.threads;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taxcalcmultithread.controllers.DBRepo;
import taxcalcmultithread.controllers.SharedBufferController;
import taxcalcmultithread.models.Item;
import taxcalcmultithread.models.RawItem;

@ExtendWith(MockitoExtension.class)
class ProducerThreadTest {
  @Mock
  private SharedBufferController sharedBufferControllerMock;
  @Mock
  private DBRepo dbRepoMock;
  @Mock
  private RawItem itemMock;
  @InjectMocks
  ProducerThread producerThreadMock;

  @Test
  void testRun() throws InterruptedException, SQLException {
    when(dbRepoMock.isCompleted()).thenReturn(false,false,true);

    when(dbRepoMock.getNext()).thenReturn(itemMock);

    doNothing().when(sharedBufferControllerMock).addItem(isA(Item.class));

    producerThreadMock.start();
    producerThreadMock.join();

    verify(sharedBufferControllerMock,times(2)).addItem(isA(Item.class));
  }

}