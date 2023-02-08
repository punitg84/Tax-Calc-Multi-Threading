package taxcalcmultithread.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import taxcalcmultithread.enums.Type;

@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class Item {

  protected String name;
  protected double price;
  protected int quantity;
  protected Type type;
  protected double taxedCost;

  public static Item createItem(String name, String typeString, double price, int quantity) {
    Type type = Type.valueOf(typeString);

    return switch (type) {
      case MANUFACTURED -> ManufacturedItem.builder()
          .name(name)
          .price(price)
          .quantity(quantity)
          .type(type)
          .build();

      case IMPORTED -> ImportedItem.builder()
          .name(name)
          .price(price)
          .quantity(quantity)
          .type(type)
          .build();

      case RAW -> RawItem.builder()
          .name(name)
          .price(price)
          .quantity(quantity)
          .type(type)
          .build();
    };
  }

  public abstract double calcTaxedCost();

}