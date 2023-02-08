package taxcalcmultithread.models;

import static taxcalcmultithread.constants.Tax.RAW_ITEM_TAX_RATE;

import lombok.Builder;
import taxcalcmultithread.enums.Type;


public class RawItem extends Item {

  @Builder
  public RawItem(
      final String name,
      final Type type,
      final double price,
      final int quantity,
      final double taxedCost) {
    super(name, price, quantity, type, taxedCost);
  }

  @Override
  public double calcTaxedCost() {
    return (price * RAW_ITEM_TAX_RATE) / 100.0;
  }

}
