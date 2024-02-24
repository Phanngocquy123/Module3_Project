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
            Field[] fields = entityClass.getDeclaredFields();
            conn = MySqlConnect.open();
            String fieldId = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .map(f -> f.getAnnotation(Column.class).name()).findFirst().get();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?",
                    entityClass.getAnnotation(Table.class).name(), fieldId);
            ps = conn.prepareStatement(sql);
            ps.setObject(1, key);
            rs = ps.executeQuery();
            while (rs.next()){
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field f: fields){
                    f.setAccessible(true);
                    f.set(entity, rs.getObject(f.getAnnotation(Column.class).name()));
                }
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
            Field[] fields = entity.getClass().getDeclaredFields();
            String columns = Arrays.stream(fields)
                    .map(f -> f.getAnnotation(Column.class).name())
                    .collect(Collectors.joining(","));

            String values = Arrays.stream(fields)
                    .map(f -> "?").collect(Collectors.joining(","));

            conn = MySqlConnect.open();
            String sql = MessageFormat.format("INSERT INTO {0}({1}) VALUES ({2})",
                    entity.getClass().getAnnotation(Table.class).name(), columns, values);

            int index = 1;
            ps = conn.prepareStatement(sql);
            for (Field f : fields){
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }

            int result = ps.executeUpdate();
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

            Field[] fields = entity.getClass().getDeclaredFields();
            String tblName = entity.getClass().getAnnotation(Table.class).name();
            List<Field> updateFields = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) == null)
                    .collect(Collectors.toList());

            List<Field> keyFields = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .collect(Collectors.toList());

            String columns = updateFields.stream()
                    .map(f -> f.getAnnotation(Column.class).name() + " = ?")
                    .collect(Collectors.joining(","));
            String key = keyFields.stream()
                    .map(f -> f.getAnnotation(Column.class).name() + " = ?")
                    .collect(Collectors.joining(","));
            String sql = MessageFormat.format("UPDATE {0} SET {1} where {2}", tblName, columns, key);

            int index = 1;
            ps = conn.prepareStatement(sql);
            for(Field f : updateFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            for(Field f : keyFields) {
                f.setAccessible(true);
                ps.setObject(index++, f.get(entity));
            }
            int result = ps.executeUpdate();
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
            String fieldId = Arrays.stream(fields)
                    .filter(f -> f.getAnnotation(Id.class) != null)
                    .map(f -> f.getAnnotation(Column.class).name())
                    .findFirst().get();
            String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ?",
                    entityClass.getAnnotation(Table.class).name(), fieldId);

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
}
