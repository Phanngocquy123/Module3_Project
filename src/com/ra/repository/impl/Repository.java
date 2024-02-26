package com.ra.repository.impl;

import com.ra.repository.IRepository;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.MySqlConnect;
import com.ra.util.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Repository<T, K> implements IRepository<T, K> {
    @Override
    public List<T> findAll(Class<T> entityClass) {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MySqlConnect.open();
            ps = conn.prepareStatement("SELECT * FROM " + entityClass.getAnnotation(Table.class).name());
            rs = ps.executeQuery();
            while (rs.next()){
                T entity = readResultSet(rs, entityClass);
                result.add(entity);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return result;
    }

    @Override
    public T findId(K key, Class<T> entityClass) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MySqlConnect.open();
            List<Field> keysField = getKey(entityClass);
            String keysName = keysField
                    .stream().map(f -> colName(f) + " = ?")
                    .collect(Collectors.joining(","));
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1}",
                    tblName(entityClass), keysName);
            ps = conn.prepareStatement(sql);
            ps.setObject(1, key);
            rs = ps.executeQuery();
            while (rs.next()){
                T entity = readResultSet(rs, entityClass);
                return entity;
            }

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public T add(T entity) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MySqlConnect.open();
            List<Field> fields = getColumns((Class<T>) entity.getClass());

            String columns = fields.stream()
                    .map(this::colName).collect(Collectors.joining(","));
            String values = fields.stream()
                    .map(f -> "?").collect(Collectors.joining(","));
            String sql = MessageFormat
                    .format("INSERT INTO {0}({1}) VALUES ({2})", tblName((Class<T>) entity.getClass()), columns, values);

            int index = 1;
            ps = conn.prepareStatement(sql);
            for (Field f : fields){
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            ps.executeUpdate();
            return entity;
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public T edit(T entity) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MySqlConnect.open();
            List<Field> updateFields = getColumnsIgnoreKey((Class<T>) entity.getClass());
            List<Field> keyFields = getKey((Class<T>) entity.getClass());

            String columns = updateFields.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
            String key = keyFields.stream().map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
            String sql = MessageFormat.format("UPDATE {0} SET {1} WHERE {2}", tblName((Class<T>) entity.getClass()), columns, key);
            // System.out.println(sql);
            ps = conn.prepareStatement(sql);
            int index = 1;
            for(Field f : updateFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            for(Field f : keyFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            ps.executeUpdate();
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return null;
    }

    @Override
    public boolean remove(K id, Class<T> entityClass) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Field[] fields = entityClass.getDeclaredFields();
            conn = MySqlConnect.open();
            String fieldId = Arrays.stream(fields).map(f -> colName(f) + " = ?").collect(Collectors.joining(","));
                  //  .filter(f -> f.getAnnotation(Id.class) != null)
                 //   .map(f -> f.getAnnotation(Column.class).name())
                  //  .findFirst().get();
            String sql = MessageFormat.format("DELETE FROM {0} WHERE {1}",
                    tblName(entityClass), fieldId);

            ps = conn.prepareStatement(sql);
            ps.setObject(1, id);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
        return false;
    }

    private T readResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        T entity = clazz.getDeclaredConstructor().newInstance();
        List<Field> fields = getColumns(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(Date.class)) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                field.set(entity, fmt.parse(rs.getString(colName(field))));
            } else {
                field.set(entity, rs.getObject(colName(field)));
            }
        }
        return entity;
    }

    private List<Field> getColumns(Class<T> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Objects.nonNull(f.getAnnotation(Column.class)))
                .collect(Collectors.toList());
    }

    private String colName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column))
            return column.name();
        return null;
    }

    private List<Field> getKey(Class<T> entityClass) {
        List<Field> fields = getColumns(entityClass);
        return fields.stream()
                .filter(f -> Objects.nonNull(f.getAnnotation(Id.class)))
                .collect(Collectors.toList());
    }

    private String tblName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        if (Objects.nonNull(table))
            return table.name();
        return null;
    }

    private List<Field> getColumnsIgnoreKey(Class<T> entityClass) {
        List<Field> fields = getColumns(entityClass);
        return fields.stream()
                .filter(f -> Objects.isNull(f.getAnnotation(Id.class)))
                .collect(Collectors.toList());
    }
}
