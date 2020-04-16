package ru.sberbank.service;

public interface CrudOperations<K, V> {
    boolean createTable();
    void create(V obj);
    void update(V obj);
    V read(K key);
    void delete(K key);
}
