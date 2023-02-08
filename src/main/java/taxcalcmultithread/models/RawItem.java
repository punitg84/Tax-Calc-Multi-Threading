package taxcalcmultithread.models;

import static taxcalcmultithread.constants.Tax.RAW_ITEM_TAX_RATE;

import lombok.Builder;
import taxcalcmultithread.enums.Type;


public class RawItem extends Item {

  @Builder
  public RawItem(String name, Type type, double price, int quantity, double taxedCost) {
    super(name, price, quantity, type, taxedCost);
  }

  public double calcTaxedCost() {
    return (price * RAW_ITEM_TAX_RATE) / 100.0;
  }

}
