package taxcalcmultithread.controllers;

import static taxcalcmultithread.constants.Query.SQL_SELECT_ALL_ITEM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import taxcalcmultithread.models.Item;

@Getter
public class DBRepo {

  private ResultSet resultSet;
  private Connection conn;
  private boolean completed = false;

  public void connection() throws SQLException {
    String connectionUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
    conn = DriverManager.getConnection(connectionUrl, "root", "nuclei@08@04@2001");
  }

  public DBRepo() throws SQLException {
    connection();
    PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_ITEM);
    resultSet = ps.executeQuery();
  }

  public Item getNext() throws Exception {
    resultSet.next();
    completed = resultSet.isLast();
    if(completed) conn.endRequest();
    return Item.createItem(resultSet.getString("name"),
        resultSet.getString("type"),
        resultSet.getDouble("price"),
        resultSet.getInt("quantity"));
  }

}
