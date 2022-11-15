package ORM_EXE.repository;

import ORM_EXE.utils.Validator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class UpdateQuery<T> {
    private static Logger logger = LogManager.getLogger(UpdateQuery.class.getName());
    private Class<T> clz;
    private MysqlConnection mysqlcon;
    private Connection con;

    public UpdateQuery(Class<T> clz) {
        this.clz = clz;
    }

    public <T> void updateField(String field, T updatedValue, int id) {

        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            if (field == null || updatedValue == null || !Validator.isFieldExistsInTable(con, clz.getSimpleName().toLowerCase(), field)) {
                logger.warn("warn 300: failed to update field in item");
                throw new IllegalArgumentException("cannot update item");
            } else {
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
                int success = ps.executeUpdate();
                if (success > 0) {
                    logger.info("info 400 : item " + clz.getSimpleName() + " updated at field " + field + " set value to " + updatedValue);
                } else {
                    logger.error("error 200: failed to update item " + clz.getSimpleName() + " at field" + field + " set value to " + updatedValue);
                    throw new RuntimeException("failed to update");
                }
                mysqlcon.close();
            }
        } catch (SQLException e) {
            logger.error("error 200: failed to update item " + clz.getSimpleName() + " at field" + field + " set value to " + updatedValue);
            throw new RuntimeException(e);
        }
        finally {
            mysqlcon.close();
        }
    }

    public <T> void updateItem(T item) {
        if (item == null) {
            logger.warn("warn 200: failed to update null item");
            throw new IllegalArgumentException("cannot update null");
        }

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
            int success = ps.executeUpdate();
            if (success > 0) {
                logger.info("info 400 : item updated " + item);
            } else {
                logger.error("info 200 : failed to update item " + item);
                throw new RuntimeException("failed to update item");
            }

        } catch (SQLException e) {
            logger.error("info 200 : failed to update item " + item);
            throw new RuntimeException("failed to update item");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }finally {
            mysqlcon.close();
        }
    }
}
