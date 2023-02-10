package taxcalcmultithread;

import java.sql.SQLException;
import taxcalcmultithread.view.Application;

public final class Main {

  private Main() {

  }

  public static void main(final String[] args) throws SQLException {
    final Application application = new Application();
    application.run();
  }

}
