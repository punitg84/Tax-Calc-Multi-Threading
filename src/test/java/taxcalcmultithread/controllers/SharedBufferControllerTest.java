package taxcalcmultithread.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taxcalcmultithread.collection.SharedBuffer;
import taxcalcmultithread.controllers.sharedbuffercontrollertestscenario.IsFullTestScenario;
import taxcalcmultithread.models.Item;

@ExtendWith(MockitoExtension.class)
class SharedBufferControllerTest {

  @Mock
  private SharedBuffer sharedBufferMock;
  @Mock
  private List<Item> listMock;
  @Mock
  private Item item;
  @InjectMocks
  private SharedBufferController sharedBufferControllerMock;

  private static Stream<IsFullTestScenario> generateTestScenarioForIsFull(){
    return Stream.of(
        IsFullTestScenario.builder()
            .testCaseName("Non full")
            .output(false)
            .listSize(1)
            .build(),
        IsFullTestScenario.builder()
            .testCaseName("Full")
            .output(true)
            .listSize(2)
            .build()
    );
  }
  @ParameterizedTest
  @MethodSource("generateTestScenarioForIsFull")
  void testIsFull(IsFullTestScenario testCase) {
    String testCaseName = testCase.getTestCaseName();
    boolean output = testCase.isOutput();
    int listSize = testCase.getListSize();

    when(sharedBufferMock.getList()).thenReturn(listMock);

    when(listMock.size()).thenReturn(listSize);

    assertEquals(output, sharedBufferControllerMock.isFull(),testCaseName);
  }


}