package taxcalcmultithread.controllers.sharedbuffercontrollertestscenario;

import lombok.Builder;
import lombok.Getter;
import taxcalcmultithread.testscenario.GenericTestScenario;

@Getter
public class IsFullTestScenario extends GenericTestScenario {

  private int listSize;
  private boolean output;

  @Builder
  public IsFullTestScenario(String errMessage, String testCaseName, int listSize, boolean output) {
    super(errMessage, testCaseName);
    this.listSize = listSize;
    this.output = output;
  }

}
