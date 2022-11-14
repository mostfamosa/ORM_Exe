package ORM_EXE;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadFromDB<T> {
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
                    if(field.getName()=="birthDate")
                        continue;
                    if(field.getName()=="grade")
                        continue;
                    field.set(item, res.getObject(field.getName()));
                }
                result.add(item);
            }
            con.close();
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
                    field.set(item, res.getObject(field.getName()));
                }
                con.close();
                return item;
            } else
                System.out.println("There is no " + clz.getSimpleName() + " with id = " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public List<T> getItemsByProp(String propName,String propValue) {
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(String.format(
                    "select * from %s where %s = '%s'",
                    clz.getSimpleName().toLowerCase(), propName,propValue));
            List<T> result = new ArrayList<>();

            while (res.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[] declaredFields = clz.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    field.set(item, res.getObject(field.getName()));
                }
                result.add(item);
            }
            con.close();
            return result;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

}