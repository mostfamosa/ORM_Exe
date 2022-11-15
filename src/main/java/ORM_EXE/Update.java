package ORM_EXE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.MysqlType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Update<T> {
    private static Logger logger = LogManager.getLogger(Update.class.getName());
    private Class<T> clz;
    private MysqlConnection mysqlcon;
    private Connection con;

    public Update(Class<T> clz) {
        this.clz = clz;
    }

    public void updateField(String field, T updatedValue, int id) {

        try {
            if (field == null || updatedValue == null || !Validator.isFieldExistsInTable(con, clz.getSimpleName().toLowerCase(), field)) {
                logger.warn("warn 300: failed to update field in item");
            } else {
                mysqlcon = MysqlConnection.getInstance();
                con = mysqlcon.getConnection();
                String sqlUpdate = "UPDATE " + clz.getSimpleName().toLowerCase() + " SET " + field + " = ? " + "WHERE id =" + id;
                PreparedStatement ps = con.prepareStatement(sqlUpdate);
                if (updatedValue.getClass().isPrimitive()) {
                    if (updatedValue.getClass().getSimpleName().equals("Character")) {
                        ps.setString(1, String.valueOf(updatedValue));
                    } else {
                        ps.setObject(1, updatedValue);
                    }
                } else {
                    Gson gson = new Gson();
                    String jsonObj = gson.toJson(updatedValue);
                    ps.setString(1, jsonObj);
                }
                ps.execute();
                mysqlcon.close();
                logger.info("info 400 : item " + clz.getSimpleName() + " updated at field " + field + " set value to " + updatedValue);
            }
        } catch (SQLException e) {
            logger.error("error 200: failed to update item " + clz.getSimpleName() + " at field" + field + " set value to " + updatedValue);
            throw new RuntimeException(e);
        }
    }

    public void updateItem(T item) {
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            Statement stmt = con.createStatement();
            Field[] allFields = clz.getDeclaredFields();
            String insertCommand = "UPDATE " + clz.getSimpleName().toLowerCase() + " SET ";
            Integer id = null;
            for (int i = 0; i < allFields.length; i++) {
                allFields[i].setAccessible(true);
                String name = allFields[i].getName().substring(allFields[i].getName().lastIndexOf('.') + 1);
                if (name.equals("id")) {
                    id = (Integer) allFields[i].get(item);
                } else {
                    insertCommand += " " + name + " = " + "?";
                    if (i != allFields.length - 1) {
                        insertCommand += ", ";
                    }
                }

            }
            insertCommand += " WHERE id = " + id;
            PreparedStatement ps = con.prepareStatement(insertCommand);
            int index = 1;
            for (int i = 0; i < allFields.length && index < allFields.length; i++) {
                allFields[i].setAccessible(true);
                Object value = null;
                value = allFields[i].get(item);
                String name = allFields[i].getName().substring(allFields[i].getName().lastIndexOf('.') + 1);
                if (!name.equals("id")) {
                    if (allFields[i].getType().isPrimitive()) {
                        if (value.getClass().getSimpleName().equals("Character")) {
                            ps.setString(index++, String.valueOf(value));
                        } else {
                            ps.setObject(index++, value);
                        }
                    } else {
                        Gson gson = new Gson();
                        String jsonObj = gson.toJson(value);
                        ps.setString(index++, jsonObj);
                    }
                }
            }
            ps.execute();
            mysqlcon.close();
            logger.info("info 400 : item updated " + item);
        } catch (SQLException e) {
            logger.error("info 200 : failed to update item " + item);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
