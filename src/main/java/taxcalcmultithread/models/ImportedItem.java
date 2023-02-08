package taxcalcmultithread.models;

import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_FIRST_LIMIT;
import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_FIXED_SURCHARGE_ABOVE_FIRST_LIMIT_BELOW_SECOND_LIMIT;
import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_FIXED_SURCHARGE_BELOW_FIRST_LIMIT;
import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_SECOND_LIMIT;
import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_SURCHARGE_RATE_ABOVE_SECOND_LIMIT;
import static taxcalcmultithread.constants.Tax.IMPORTED_ITEM_TAX_RATE;

import lombok.Builder;
import taxcalcmultithread.enums.Type;

public class ImportedItem extends Item {

  @Builder
  public ImportedItem(String name, Type type, double price, int quantity,double taxedCost) {
    super(name, price, quantity, type,taxedCost);
  }

  public double calcTaxedCost() {
    double taxedCost = (price * IMPORTED_ITEM_TAX_RATE) / 100.0;
    if (price + taxedCost <= IMPORTED_ITEM_FIRST_LIMIT) {
      taxedCost += IMPORTED_ITEM_FIXED_SURCHARGE_BELOW_FIRST_LIMIT;
    } else if (price + taxedCost <= IMPORTED_ITEM_SECOND_LIMIT) {
      taxedCost += IMPORTED_ITEM_FIXED_SURCHARGE_ABOVE_FIRST_LIMIT_BELOW_SECOND_LIMIT;
    } else {
      taxedCost += ((taxedCost + price) * IMPORTED_ITEM_SURCHARGE_RATE_ABOVE_SECOND_LIMIT) / 100.0;
    }
    return taxedCost;
  }
}
