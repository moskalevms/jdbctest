package ru.sberbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;
import ru.sberbank.service.JdbcConnection;
import service.impl.CrudOperationUserImpl;

@Configuration
public class SpringConfig {
    private JdbcConnection conn = new JdbcConnection();
    @Bean(name = "user")
    public User user(){
        return new User();
    }
    @Bean(name ="crudImpl")
    public CrudOperations crudOperations(User user){
        return new CrudOperationUserImpl(conn.getConnection());
    }
}



