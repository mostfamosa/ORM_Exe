package ORM_EXE;


import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadFromDB<T> {
    private static Logger logger = LogManager.getLogger(ReadFromDB.class.getName());

    private Class<T> clz;
    private MysqlConnection mysqlcon;
    private Connection con;

    public ReadFromDB(Class<T> clz) {
        this.clz = clz;
    }

    public List<T> getAllItems() {
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(String.format(
                    "select * from %s", clz.getSimpleName().toLowerCase()));
            List<T> result = new ArrayList<>();
            while (res.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[] declaredFields = clz.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    System.out.println(res.getObject(field.getName()));
                    if (!field.getType().isPrimitive()) {
                        Gson g = new Gson();
                        Object obj = g.fromJson(res.getObject(field.getName()).toString(), field.getType());
                        field.set(item, obj);
                    } else if (field.getType().equals(char.class)) {
                        char a = res.getObject(field.getName()).toString().charAt(0);
                        field.set(item, a);
                    } else
                        field.set(item, res.getObject(field.getName()));
                }
                logger.info("info 400 : item added "+item);
                result.add(item);
            }
            mysqlcon.close();
            return result;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public T getItemById(int id) {
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(String.format(
                    "select * from %s where id = %s", clz.getSimpleName().toLowerCase(), id));
            if (res.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[] declaredFields = clz.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (!field.getType().isPrimitive()) {
                        Gson g = new Gson();
                        Object obj = g.fromJson(res.getObject(field.getName()).toString(), field.getType());
                        field.set(item, obj);
                    } else if (field.getType().equals(char.class)) {
                        char a = res.getObject(field.getName()).toString().charAt(0);
                        field.set(item, a);
                    } else
                        field.set(item, res.getObject(field.getName()));
                }
                mysqlcon.close();
                return item;
            } else
                System.out.println("There is no " + clz.getSimpleName() + " with id = " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public List<T> getItemsByProp(String propName, String propValue) {
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(String.format(
                    "select * from %s where %s = '%s'",
                    clz.getSimpleName().toLowerCase(), propName, propValue));
            List<T> result = new ArrayList<>();

            while (res.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[] declaredFields = clz.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (!field.getType().isPrimitive()) {
                        Gson g = new Gson();
                        Object obj = g.fromJson(res.getObject(field.getName()).toString(), field.getType());
                        field.set(item, obj);
                    } else if (field.getType().equals(char.class)) {
                        char a = res.getObject(field.getName()).toString().charAt(0);
                        field.set(item, a);
                    } else
                        field.set(item, res.getObject(field.getName()));
                }
                result.add(item);
            }
            mysqlcon.close();
            return result;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

}