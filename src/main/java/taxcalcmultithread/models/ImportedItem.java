package taxcalcmultithread.models;

import lombok.Builder;
import taxcalcmultithread.constants.Tax;
import taxcalcmultithread.enums.Type;

public class ImportedItem extends Item {

  @Builder
  public ImportedItem(
      final String name,
      final Type type,
      final double price,
      final int quantity,
      final double taxedCost) {
    super(name, price, quantity, type, taxedCost);
  }

  @Override
  public double calcTaxedCost() {
    double taxedCost = (price * Tax.IMPORTED_ITEM_TAX_RATE) / 100.0;
    if (price + taxedCost <= Tax.IMPORTED_ITEM_FIRST_LIMIT) {
      taxedCost += Tax.IMPORTED_ITEM_FIXED_SURCHARGE_BELOW_FIRST_LIMIT;
    } else if (price + taxedCost <= Tax.IMPORTED_ITEM_SECOND_LIMIT) {
      taxedCost += Tax.IMPORTED_ITEM_FIXED_SURCHARGE_ABOVE_FIRST_LIMIT_BELOW_SECOND_LIMIT;
    } else {
      taxedCost += ((taxedCost + price) * Tax.IMPORTED_ITEM_SURCHARGE_RATE_ABOVE_SECOND_LIMIT) / 100.0;
    }
    return taxedCost;
  }

}
