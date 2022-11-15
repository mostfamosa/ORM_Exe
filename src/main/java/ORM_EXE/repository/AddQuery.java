package ORM_EXE.repository;


import ORM_EXE.utils.Validator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public class AddQuery<T> {
    private static Logger logger = LogManager.getLogger(AddQuery.class.getName());
    private Class<T> clz;
    private MysqlConnection mysqlcon;
    private Connection con;
    public static final int MYSQL_DUPLICATE_PK = 1062;

    public AddQuery(Class<T> clz) {
        this.clz = clz;
    }


    public <T> void addItem(T item) {
        if (item == null) {
            logger.warn("warn 200: failed to add null item");
            throw new IllegalArgumentException("cannot add null");
        }
        try {
            mysqlcon = MysqlConnection.getInstance();
            con = mysqlcon.getConnection();
            if (!Validator.isTableExists(con, clz.getSimpleName().toLowerCase())) {
                CreateTableQuery createTable = new CreateTableQuery(clz);
                createTable.createTableInDB();
                mysqlcon = MysqlConnection.getInstance();
                con = mysqlcon.getConnection();
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
            PreparedStatement ps = con.prepareStatement(insertCommand);
            for (int i = 0; i < allFields.length; i++) {
                allFields[i].setAccessible(true);
                Object value = null;
                value = allFields[i].get(item);
                if (allFields[i].getType().isPrimitive()) {
                    if (value.getClass().getSimpleName().equals("Character")) {
                        ps.setString(i + 1, String.valueOf(value));
                    } else {
                        ps.setObject(i + 1, value);
                    }
                } else {
                    Gson gson = new Gson();
                    String jsonObj = gson.toJson(value);
                    ps.setString(i + 1, jsonObj);
                }
            }
            ps.execute();
            mysqlcon.close();
            logger.info("info 400 : item added " + item);
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                logger.warn("warn 300: failed to add item with primary key that is already in use");
                throw new IllegalArgumentException("cannot add item with primary that already exists in the db");
            } else {
                logger.error("error 200: failed to add item " + item);
                throw new RuntimeException(e);
            }
        } catch (IllegalAccessException e) {
            logger.error("error 200: failed to add item " + item);
            throw new RuntimeException(e);
        }
    }


    public <T> void addMultipleItems(List<T> items) {
        if (items == null || items.size() == 0) {
            logger.warn("warn 200: failed to add empty list of items");
            throw new IllegalArgumentException("cannot add null");
        } else {
            for (T item :
                    items) {
                addItem(item);
            }
        }
    }

}
