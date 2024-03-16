package org.example;

import java.sql.*;

import static org.example.Constants.*;

public class Way5 {

    public static void main(String[] args) throws SQLException {

        startApp();

    }


    private static void startApp() {
        try (Connection con = DriverManager.getConnection(MSQL, "root", new Constants().getPAROLL());
             Statement statement = con.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS robot");
            statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");
            insertData(con, 10);
            readData(statement, SELECT_ALL);
            readData(statement, SELECT_SECOND);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertData(Connection connection, int countOfEntity) throws SQLException {
        boolean autoComit = connection.getAutoCommit();
        connection.setAutoCommit(false); //знімаємо автопідтвердженя дій авто коміт
        // завдяки PreparedStatement легше і уникати дублювання
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT  INTO  robot(val, name) VALUES (?, ?)")) {
// кожний запит в бд це окрема транзакція ніби відкрили - зберігли - закомітили чи зробили роулбек якщо помилка
            for (int i = 1; i <= countOfEntity; i++) {
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

    private static void readData(Statement statement, String sql) {

        // executeQuery це вже для вибірки, повертає  ResultSet це обьект який нам повертає певні значенняз бд
        try (ResultSet resultSet = statement.executeQuery(sql)) {
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


     /*  private static void createTables() throws SQLException {
        try (Connection con = DriverManager.getConnection(MSQL, "root", new Constants().getPAROLL());
             Statement statement = con.createStatement()) {
            // далі завдяки стетментам створюємо таблицю
            statement.execute("DROP TABLE IF EXISTS robot");
            statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");
        }
    } */
}
