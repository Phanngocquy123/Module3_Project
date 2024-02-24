package com.ra.repository;

import java.util.List;

public interface IRepository<T, K> {
    List<T> findAll(Class<T> entityClass);
    T findId(K key, Class<T> entityClass);
    T add(T entity);
    T edit(T entity);
    boolean remove(K id, Class<T> entityClass);
}
