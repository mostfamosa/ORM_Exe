package ORM_EXE;

import com.google.gson.Gson;
import com.mysql.cj.MysqlType;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Update <T>{
    private Class<T> clz;
    MysqlConnection mysqlConnection;

    public Update(Class<T> clz) {
        this.clz = clz;
        mysqlConnection=MysqlConnection.getInstance();
    }

    public void updateField(String field, T updatedValue, int id){
        try {
            String sqlUpdate = "UPDATE "+clz.getSimpleName().toLowerCase() + 's' +" SET "+ field+" = ? "+ "WHERE id ="+id;
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
    public void updateItem(T item){
        try {
            Statement stmt = mysqlConnection.getConnection().createStatement();

                Field[] allFields = clz.getDeclaredFields();
                String insertCommand = "UPDATE " + clz.getSimpleName().toLowerCase() + 's' + " SET ";


                PreparedStatement ps = mysqlConnection.getConnection().prepareStatement(insertCommand);
                for (int i = 0; i < allFields.length; i++) {
                    allFields[i].setAccessible(true);
                    Object value = null;
                    value = allFields[i].get(item);
                    if ((MysqlType)MySqlTypes.DEFAULT_MYSQL_TYPES.get(value.getClass())==null) {
                        if(value.getClass().getSimpleName().equals("Character")){
                            ps.setString(i + 1, String.valueOf(value));
                        }
                        else {
                            Gson gson = new Gson();
                            String jsonObj = gson.toJson(value);
                            ps.setString(i + 1, jsonObj);
                        }
                    } else {
                        ps.setObject(i + 1, value);
                    }
                }
                ps.execute();
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } else {
                throw new RuntimeException("no matching table exist");
            }

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
