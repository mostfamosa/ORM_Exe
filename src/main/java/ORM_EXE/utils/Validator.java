package ORM_EXE.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validator {
    public static boolean isTableExists(Connection con,String tableName){
        try {
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName,new String[] {"TABLE"});
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get Connection's Database Tables. ",e);
        }
        return false;
    }

    public static boolean isFieldExistsInTable(Connection con,String tableName, String fieldName){
        try {
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getColumns(null, null, tableName, fieldName);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get table's" + tableName + " columns. ",e);
        }
        return false;
    }
}