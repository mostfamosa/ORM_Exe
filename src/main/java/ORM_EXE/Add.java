package ORM_EXE;


import com.google.gson.Gson;
import com.mysql.cj.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add<T> {
    private Class<T> clz;
    public Add(Class<T> clz) {
        this.clz = clz;
        this.mysqlConnection = MysqlConnection.getInstance();
    }

    MysqlConnection mysqlConnection;
    public static final int MYSQL_DUPLICATE_PK = 1062;


    private boolean checkIfTableExist() {
        ResultSet rs = null;
        try {
            DatabaseMetaData md = mysqlConnection.getConnection().getMetaData();
            rs = md.getTables(null, null, clz.getSimpleName().toLowerCase() , null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public <T> void addItem(T item) {
        try {
            Statement stmt = mysqlConnection.getConnection().createStatement();
            if (!checkIfTableExist()) {
                CreateTable createTable=new CreateTable(clz);
                createTable.createTableInDB();
            }
                Field[] allFields = clz.getDeclaredFields();
                String insertCommand = "INSERT INTO " + clz.getSimpleName().toLowerCase() + " VALUES(";
                for (int i = 0; i < allFields.length; i++) {
                    insertCommand += '?';
                    if (i != allFields.length - 1) {
                        insertCommand += ", ";
                    } else {
                        insertCommand += ')';
                    }
                }
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


        } catch (SQLException e) {

            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new RuntimeException("Primary key already used");
            } else throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> void addMultiple(List<T> items){
        for (T item:
             items) {
            addItem(item);
        }
    }

}
