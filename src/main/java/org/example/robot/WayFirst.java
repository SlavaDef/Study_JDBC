package org.example.robot;

import java.sql.*;

public class WayFirst {
    public static void main(String[] args) throws SQLException {
        // jdbc:h2:mem: тут закодована вся інфа для DriverManager для підключення до бд
        // Connection  - отримання конекшену до бд
       try ( Connection connection = DriverManager.getConnection("jdbc:h2:mem:"); // jdbc:h2" база данних in memory
             // далі завдяки стетментам створюємо таблицю
              Statement statement = connection.createStatement() ){ // завдяки try все закриється

            // execute використовується для створення і видалення, здебільшого і додавання
            statement.execute("CREATE TABLE robot(val INT, name VARCHAR(100))");
            statement.execute("INSERT  INTO  robot(val, name) VALUES (1, 'robot1')");
            statement.execute("INSERT  INTO  robot(val, name) VALUES (2, 'robot2')");
            statement.execute("INSERT  INTO  robot(val, name) VALUES (3, 'robot3')");
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