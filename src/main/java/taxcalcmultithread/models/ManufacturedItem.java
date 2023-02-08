package taxcalcmultithread.models;

import static taxcalcmultithread.constants.Tax.MANUFACTURED_ITEM_ADDITIONAL_TAX_RATE;
import static taxcalcmultithread.constants.Tax.MANUFACTURED_ITEM_TAX_RATE;

import lombok.Builder;
import lombok.ToString;
import taxcalcmultithread.enums.Type;

public class ManufacturedItem extends Item {

  @Builder
  public ManufacturedItem(String name, Type type, double price, int quantity,double taxedCost) {
    super(name, price, quantity, type,taxedCost);
  }

  public double calcTaxedCost() {
    double taxedCost = (price * MANUFACTURED_ITEM_TAX_RATE) / 100.0;
    taxedCost += ((price + taxedCost) * MANUFACTURED_ITEM_ADDITIONAL_TAX_RATE) / 100.0;
    return taxedCost;
  }
}
