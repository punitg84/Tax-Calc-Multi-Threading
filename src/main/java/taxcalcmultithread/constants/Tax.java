package taxcalcmultithread.constants;

public class Tax {
  public static final double RAW_ITEM_TAX_RATE = 12.5;
  public static final double MANUFACTURED_ITEM_TAX_RATE = 12.5;
  public static final double MANUFACTURED_ITEM_ADDITIONAL_TAX_RATE = 2;
  public static final int IMPORTED_ITEM_TAX_RATE = 10;
  public static final int IMPORTED_ITEM_FIXED_SURCHARGE_BELOW_FIRST_LIMIT = 5;
  public static final int IMPORTED_ITEM_FIXED_SURCHARGE_ABOVE_FIRST_LIMIT_BELOW_SECOND_LIMIT = 10;
  public static final int IMPORTED_ITEM_SURCHARGE_RATE_ABOVE_SECOND_LIMIT = 5;
  public static final int IMPORTED_ITEM_FIRST_LIMIT = 100;
  public static final int IMPORTED_ITEM_SECOND_LIMIT = 200;
}
