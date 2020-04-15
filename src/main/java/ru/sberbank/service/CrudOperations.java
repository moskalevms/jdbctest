package ru.sberbank.service;

public interface CrudOperations<T> {
    void create(T obj);
    void update(T obj);
    T read(long id);
    void delete(T obj);
}
