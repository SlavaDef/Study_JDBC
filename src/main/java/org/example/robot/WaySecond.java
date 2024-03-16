package org.example.robot;

import java.sql.*;

public class WaySecond {

    public static void main(String[] args) throws SQLException {
        // jdbc:h2:mem: тут закодована вся інфа для DriverManager для підключення до бд
        // Connection  - отримання конекшену до бд
        try ( Connection connection = DriverManager.getConnection("jdbc:h2:mem:"); // jdbc:h2" база данних in memory

             Statement statement = connection.createStatement() ) {
            // далі завдяки стетментам створюємо таблицю
             statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");

             // завдяки PreparedStatement легше і уникати дублювання
             PreparedStatement preparedStatement = connection.prepareStatement (
                     "INSERT  INTO  robot(val, name) VALUES (?, ?)") ;

            // далі встановлюємо значення
            for (int i = 1; i<10; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "robot"+i);
                preparedStatement.execute();
            }

            // executeQuery це вже для вибірки, повертає  ResultSet це обьект який нам повертає певні значенняз бд
            try (ResultSet resultSet = statement.executeQuery("SELECT val,name FROM robot")) {
                while (resultSet.next()) { //  resultSet.next() це по суті ітератор який повертає поля якщо є
                    int val = resultSet.getInt("val"); // 1
                    String name = resultSet.getString("name");
                    System.out.println("val = " + val + ", name = " + name);
                }
            }
        }
    }
}
