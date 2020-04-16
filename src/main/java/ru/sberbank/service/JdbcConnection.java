package ru.sberbank.service;

import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component("jdbcConn")
public class JdbcConnection {
    private  String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle";
    private  String USERNAME = "sa";
    private  String PASSWORD = "";
    public Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Ошибка полчения дрйвера");
        }
        return null;

    }
}
