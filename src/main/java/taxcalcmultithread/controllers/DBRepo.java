package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.Fields.NAME;
import static taxcalcmultithread.constants.Fields.PRICE;
import static taxcalcmultithread.constants.Fields.QUANTITY;
import static taxcalcmultithread.constants.Fields.TYPE;
import static taxcalcmultithread.constants.Query.SQL_SELECT_ALL_ITEM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import taxcalcmultithread.models.Item;

@Getter
public class DBRepo {

  private ResultSet resultSet;
  private Connection conn;
  private boolean completed;

  public DBRepo() throws SQLException {
    createConnection();
    setResultSet();
  }

  public void createConnection() throws SQLException {
    try {
      String connectionUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
      conn = DriverManager.getConnection(connectionUrl, "root", "nuclei@08@04@2001");
    } catch (SQLException e) {
      throw new SQLException("Error while instantiating the sql connection", e);
    }
  }

  public void setResultSet() throws SQLException {
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement(SQL_SELECT_ALL_ITEM);
      resultSet = ps.executeQuery();
    } catch (SQLException e) {
      throw new SQLException("Error while loading data from db", e);
    } finally {
      if (Objects.nonNull(ps)) {
        ps.closeOnCompletion();
      }
    }

  }

  public Item getNext() throws SQLException {

    try {
      resultSet.next();

      completed = resultSet.isLast();
      if (completed) {
        conn.endRequest();
      }

      return Item.createItem(resultSet.getString(NAME),
          resultSet.getString(TYPE),
          resultSet.getDouble(PRICE),
          resultSet.getInt(QUANTITY));

    } catch (SQLException e) {
      throw new SQLException("Error while getting next item", e);
    }
  }

}
