package taxcalcmultithread.repositries;

import static taxcalcmultithread.constants.Query.SQL_SELECT_ALL_ITEM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import lombok.Getter;
import taxcalcmultithread.config.DbConfig;
import taxcalcmultithread.constants.Fields;
import taxcalcmultithread.models.Item;

@Getter
public class DbRepo {

  private ResultSet resultSet;
  private Connection conn;
  private PreparedStatement preparedStatement;

  public DbRepo() throws SQLException {
    createConnection();
    setResultSet();
  }

  private void createConnection() throws SQLException {
    try {
      final String connectionUrl = DbConfig.url;
      conn = DriverManager.getConnection(connectionUrl, DbConfig.username, DbConfig.password);
    } catch (SQLException e) {
      throw new SQLException("Error while instantiating the sql connection", e);
    }
  }

  private void setResultSet() throws SQLException {
    try {
      preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ITEM);
      resultSet = preparedStatement.executeQuery();
    } catch (SQLException e) {
      throw new SQLException("Error while loading data from db", e);
    } finally {
      if (Objects.nonNull(preparedStatement)) {
        preparedStatement.closeOnCompletion();
      }
    }
  }

  public Item getNext() throws SQLException {
    if(resultSet.next()){
      return Item.createItem(resultSet.getString(Fields.NAME),
          resultSet.getString(Fields.TYPE),
          resultSet.getDouble(Fields.PRICE),
          resultSet.getInt(Fields.QUANTITY));
    }else{
      throw new SQLException("All Item Processed");
    }
  }
}
