package org.example.robot;

public class Constants {

    public static final String H2 = "jdbc:h2:mem:";

    public static final String MSQL = "jdbc:mysql://localhost/robot?serverTimezone=Europe/Kiev";

    public static final String MYSQL = "jdbc:mysql://localhost/blog?serverTimezone=Europe/Kiev";

    public static final String SELECT_ALL = "SELECT val,name FROM robot";

    public static final String SELECT_SECOND = "SELECT val,name FROM robot WHERE val=2";

    private final String PAROLL = "1234";
    public static final String CREATE_USERS = "CREATE TABLE users (user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "fullName VARCHAR(200) NOT NULL, pseudonym VARCHAR(20) NOT NULL," +
            "email VARCHAR(50) NOT NULL )";
    public static final String INSERT_INTO_USERS = "INSERT INTO users(fullName,pseudonym,email) VALUES(?,?,?)";


    public String getPAROLL() {
        return PAROLL;
    }

    public static final String CREATE_POST = "CREATE TABLE post (post_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "text TEXT NOT NULL, author BIGINT REFERENCES users(user_id)," +
            "createdIn TIMESTAMP )";

    public static final String INSERT_INTO_POST = "INSERT INTO post(post_id,text,author,createdIn) VALUES(?,?,?,?)";
}
