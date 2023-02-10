package taxcalcmultithread.repositries;

import static taxcalcmultithread.constants.ExceptionMessage.ALL_ITEM_PRODUCED_EXCEPTION;
import static taxcalcmultithread.constants.ExceptionMessage.DB_CONNECTION_EXCEPTION;
import static taxcalcmultithread.constants.ExceptionMessage.LOADING_DATA_EXCEPTION;
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
      final String connectionUrl = DbConfig.URL;
      conn = DriverManager.getConnection(connectionUrl, DbConfig.USERNAME, DbConfig.PASSWORD);
    } catch (SQLException e) {
      throw new SQLException(DB_CONNECTION_EXCEPTION, e);
    }
  }

  private void setResultSet() throws SQLException {
    try {
      preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ITEM);
      resultSet = preparedStatement.executeQuery();
    } catch (SQLException e) {
      throw new SQLException(LOADING_DATA_EXCEPTION, e);
    } finally {
      if (Objects.nonNull(preparedStatement)) {
        preparedStatement.closeOnCompletion();
      }
    }
  }

  public Item getNext() throws Exception {
    if (resultSet.next()) {
      return Item.createItem(resultSet.getString(Fields.NAME),
          resultSet.getString(Fields.TYPE),
          resultSet.getDouble(Fields.PRICE),
          resultSet.getInt(Fields.QUANTITY));
    } else {
      throw new Exception(ALL_ITEM_PRODUCED_EXCEPTION);
    }
  }

}
