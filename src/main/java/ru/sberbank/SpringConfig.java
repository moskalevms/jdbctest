package ru.sberbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;
import service.impl.CrudOperationUserImpl;

import java.sql.Connection;

@Configuration
public class SpringConfig {

   // Connection connection;

    @Bean(name = "jdbcConn")
    public JdbcConnection jdbcConnection(){
        return new JdbcConnection();
    }

     @Bean(name ="crudImpl")
     public CrudOperations crudOperations(){
        return new CrudOperationUserImpl();
    }

}



