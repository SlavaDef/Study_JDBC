package org.example.robot;

public class Constants {

    public static final String H2 = "jdbc:h2:mem:";

    public static final String MSQL = "jdbc:mysql://localhost/robot?serverTimezone=Europe/Kiev";

    public static final String SELECT_ALL = "SELECT val,name FROM robot";

    public static final String SELECT_SECOND = "SELECT val,name FROM robot WHERE val=2";

    private final String PAROLL = "1234";

    public String getPAROLL() {
        return PAROLL;
    }
}
