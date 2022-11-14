package ORM_EXE;

import com.google.gson.Gson;
import com.mysql.cj.MysqlType;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static ORM_EXE.Add.MYSQL_DUPLICATE_PK;

public class Update<T> {
    private Class<T> clz;
    MysqlConnection mysqlConnection;

    public Update(Class<T> clz) {
        this.clz = clz;
        mysqlConnection = MysqlConnection.getInstance();
    }

    public void updateField(String field, T updatedValue, int id) {
        try {
            String sqlUpdate = "UPDATE " + clz.getSimpleName().toLowerCase()  + " SET " + field + " = ? " + "WHERE id =" + id;
            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(sqlUpdate);
            if (updatedValue.getClass().getSimpleName().equals("Character")) {
                ps.setString(1, String.valueOf(updatedValue));
            } else {
                ps.setObject(1, updatedValue);
            }
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateItem(T item) {
        try {
            Statement stmt = mysqlConnection.getConnection().createStatement();
            Field[] allFields = clz.getDeclaredFields();
            String insertCommand = "UPDATE " + clz.getSimpleName().toLowerCase() + " SET ";
            Integer id = null;
            for (int i=0; i<allFields.length; i++) {
                allFields[i].setAccessible(true);
                String name = allFields[i].getName().substring(allFields[i].getName().lastIndexOf('.') + 1);
                if (name.equals("id")) {
                    id = (Integer) allFields[i].get(item);
                } else {
                    insertCommand += " " +name + " = " + "?";
                    if (i != allFields.length - 1) {
                        insertCommand += ", ";
                    }
                }

            }
            insertCommand += " WHERE id = " + id;
            System.out.println(insertCommand);
            PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(insertCommand);
            int index=1;
            for (int i=0;i<allFields.length&&index<allFields.length; i++) {
                allFields[i].setAccessible(true);
                Object value = null;
                value = allFields[i].get(item);
                String name = allFields[i].getName().substring(allFields[i].getName().lastIndexOf('.') + 1);
                if (!name.equals("id")) {
                    if ((MysqlType) MySqlTypes.DEFAULT_MYSQL_TYPES.get(value.getClass()) == null) {
                        if (value.getClass().getSimpleName().equals("Character")) {
                            ps.setString(index++, String.valueOf(value));
                        } else {
                            Gson gson = new Gson();
                            String jsonObj = gson.toJson(value);
                            ps.setString(index++, jsonObj);
                        }
                    } else {
                        ps.setObject(index++, value);
                    }
                }
            }
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new RuntimeException("Primary key already used");
            } else throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
