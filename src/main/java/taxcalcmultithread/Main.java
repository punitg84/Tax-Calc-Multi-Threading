package taxcalcmultithread;

import java.sql.SQLException;
import taxcalcmultithread.view.Application;

public class Main {

  public static void main(String args[]) throws SQLException {
    Application application = new Application();
    application.run();
  }

}
