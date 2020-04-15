package ru.sberbank.service;

import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static Connection connection;
    private static Server server;

    static {
        try {
            DeleteDbFiles.execute("~", "test", true);
            server = Server.createWebServer();
            server.start();
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection = conn;
            Statement statement = conn.createStatement();
            statement.execute("create table if not exists users(id serial primary key auto_increment not null, login varchar(255), password varchar(255), firstName varchar(255), lastName varchar(255))");
            server.stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
