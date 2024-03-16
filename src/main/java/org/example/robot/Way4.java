package org.example.robot;

import java.sql.*;

import static org.example.robot.Constants.MSQL;
import static org.example.robot.Constants.SELECT_ALL;

public class Way4 {  // приклад з бд MYSQL
    public static void main(String[] args) throws SQLException {
        // jdbc:h2:mem: тут закодована вся інфа для DriverManager для підключення до бд
        // Connection  - отримання конекшену до бд
        try (Connection connection = DriverManager.getConnection(MSQL,"root", "1234");

             Statement statement = connection.createStatement() ) {
            // далі завдяки стетментам створюємо таблицю
            statement.execute("DROP TABLE IF EXISTS robot");
            statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");
            insertData(connection);
            readData(statement);

        }
    }

    private static void insertData(Connection connection) throws SQLException {
        boolean autoComit = connection.getAutoCommit();
        connection.setAutoCommit(false); //знімаємо автопідтвердженя дій авто коміт
        // завдяки PreparedStatement легше і уникати дублювання
        try ( PreparedStatement preparedStatement = connection.prepareStatement (
                "INSERT  INTO  robot(val, name) VALUES (?, ?)") ) {
// кожний запит в бд це окрема транзакція ніби відкрили - зберігли - закомітили чи зробили роулбек якщо помилка
            for (int i = 1; i < 12; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "robot" + i);
                preparedStatement.execute();
            }
            connection.commit();

        } catch (SQLException e) {
            connection.rollback(); // якщо ловимо помилку відкочуємо зміни
        } finally {
            connection.setAutoCommit(autoComit); // в любому випадку повернемось до попереднього стану
        }



    }

    private static void readData(Statement statement){

        // executeQuery це вже для вибірки, повертає  ResultSet це обьект який нам повертає певні значенняз бд
        try (ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
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
