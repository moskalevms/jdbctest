import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sberbank.SpringConfig;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;
import service.impl.CrudOperationUserImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestExample {

   // private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE;Mode=Oracle";
   // private static final String USERNAME = "sa";
   // private static final String PASSWORD = "";

 //   private Connection connection;


    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    JdbcConnection connnection = context.getBean("jdbcConn", JdbcConnection.class);

    @Autowired
    private JdbcConnection connection;

    private CrudOperations<Long, User> crudOperations;
    private Server server;

    @Before()
    public void init() throws SQLException {

        DeleteDbFiles.execute("~", "test", true);
        server = Server.createWebServer();
        server.start();
        connection.getConnection();
        //Connection conn = DriverManager.getConnection(URL, "sa", "");
        crudOperations = new CrudOperationUserImpl();
        Statement statement = connection.getConnection().createStatement();
        statement.execute("create table if not exists users(id serial primary key auto_increment not null, login varchar(255), password varchar(255), firstName varchar(255), lastName varchar(255))");
    }

    @Test
    public void testCreate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testUpdate(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        user.setPassword("987");
        crudOperations.update(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @Test
    public void testDelete(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        crudOperations.delete(user);
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", null, user2 );
    }

    @Test
    public void testRead(){
        User user = new User(1, "admin", "123", "Ivan", "Ivanov");
        crudOperations.create(user);
        crudOperations.read(user.getId());
        User user2 = crudOperations.read(user.getId());
        Assert.assertEquals("Все хорошо", user, user2 );
    }

    @After
    public void stop() throws SQLException {
      // Connection conn = DriverManager.getConnection(URL, "sa", "");
        Statement statement = connection.getConnection().createStatement();
        statement.execute("DROP table users");
        server.stop();
    }

}
