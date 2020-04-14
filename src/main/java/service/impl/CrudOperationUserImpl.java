package service.impl;

import ru.sberbank.entity.User;
import ru.sberbank.service.CrudOperations;

import java.sql.Connection;

public class CrudOperationUserImpl implements CrudOperations {

    private Connection connection;

    public CrudOperationUserImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public User read(long id) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
