package org.example;

import java.sql.*;

import static org.example.Constants.H2;

public class Way3 {

    public static void main(String[] args) throws SQLException {
        // jdbc:h2:mem: тут закодована вся інфа для DriverManager для підключення до бд
        // Connection  - отримання конекшену до бд
        try (Connection connection = DriverManager.getConnection(H2); // jdbc:h2" база данних in memory

             Statement statement = connection.createStatement() ) {
            // далі завдяки стетментам створюємо таблицю
            statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");
            insertData(connection);
            readData(statement);

        }
    }

    private static void insertData(Connection connection) {
        // завдяки PreparedStatement легше і уникати дублювання
      try ( PreparedStatement preparedStatement = connection.prepareStatement (
                  "INSERT  INTO  robot(val, name) VALUES (?, ?)") ) {
          // далі встановлюємо значення
          for (int i = 1; i < 15; i++) {
              preparedStatement.setInt(1, i);
              preparedStatement.setString(2, "robot" + i);
             preparedStatement.addBatch(); // для пришвидчення готуємо запит до виконання
             // preparedStatement.execute();
          }
          preparedStatement.executeBatch(); // а тут вже запит виконуємо в один захід
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

    private static void readData(Statement statement){

        // executeQuery це вже для вибірки, повертає  ResultSet це обьект який нам повертає певні значенняз бд
        try (ResultSet resultSet = statement.executeQuery("SELECT val,name FROM robot")) {
            while (resultSet.next()) { //  resultSet.next() це по суті ітератор який повертає поля якщо є
                int val = resultSet.getInt("val"); // 1
                String name = resultSet.getString("name");
                System.out.println("val = " + val + ", name = " + name);
               // System.out.println("val = " + resultSet.getInt("val") + ", name = " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


 // https://www.youtube.com/watch?v=M-vbfe9ovhw