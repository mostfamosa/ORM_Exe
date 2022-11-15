package ORM_EXE.repository;

import ORM_EXE.utils.Validator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

class DeleteQuery<T> {
    private final Class<T> clz;
    private MysqlConnection con;
    public DeleteQuery(Class<T> clz) {
        this.clz = clz;
    }

    private static Logger logger = LogManager.getLogger(DeleteQuery.class.getName());

    public int deleteTable() {
        try {
            con = MysqlConnection.getInstance();
            int result = this.con.getConnection().createStatement().executeUpdate("DROP TABLE IF EXISTS " + this.clz.getSimpleName().toLowerCase() + ";");
            logger.info("Deleted table " + this.clz.getSimpleName().toLowerCase());
            con.close();
            return result;
        } catch (SQLException e) {
            logger.error("Failed to create sql statement while trying to delete table " + this.clz.getSimpleName().toLowerCase());
            throw new RuntimeException("Failed to create statement while trying to delete table " + this.clz.getSimpleName().toLowerCase(), e);
        }
    }

    public <T> int deleteOneRecordByProperty(String propName, T propValue) {
        con = MysqlConnection.getInstance();
        if (Validator.isTableExists(con.getConnection(), this.clz.getSimpleName().toLowerCase()) == false) {
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete record by property operation.");
            logger.debug("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete record by property operation.");
            con.close();
            return -1;
        }
        if (Validator.isFieldExistsInTable(con.getConnection(), this.clz.getSimpleName().toLowerCase(), propName) == false) {
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            logger.debug("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            con.close();
            return -1;
        }
        if (propName == null || propValue == null) {
            System.out.println("Property and value for update can't ba null. Didn't perform delete operation.");
            logger.debug("Property and value for update can't ba null. Didn't perform delete operation.");
            con.close();
            return -1;
        }

        String queryStr = "DELETE FROM " + this.clz.getSimpleName().toLowerCase() + " WHERE " + propName + "=";
        if (propValue.getClass().isPrimitive()) {
            queryStr = queryStr + propValue;
        } else {
            if (propValue.getClass().getSimpleName().equals("Character")) {
                queryStr = queryStr + "'" + String.valueOf(propValue) + "'";
            } else {
                Gson gson = new Gson();
                String jsonObj = gson.toJson(propValue);
                queryStr = queryStr + "'" + jsonObj + "'";
            }
        }
        try {
            queryStr = queryStr + " LIMIT 1 ;";
            System.out.println(queryStr);
            int result = this.con.getConnection().createStatement().executeUpdate(queryStr);
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            logger.info(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            con.close();
            return result;
        } catch (SQLException e) {
            logger.error("Failed to create sql statement while trying to delete row with " + propName + "=" + propValue + " of table " + this.clz.getSimpleName() + ". ");
            throw new RuntimeException("Failed to create sql statement while trying to delete row with " + propName + "=" + propValue + " of table " + this.clz.getSimpleName() + ". ", e);
        }
    }

    public <T> int deleteAllRecordsByProperty(String propName, T propValue) {
        con = MysqlConnection.getInstance();
        if (Validator.isTableExists(con.getConnection(), this.clz.getSimpleName().toLowerCase()) == false) {
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete multiple records by property operation.");
            logger.debug("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete record by property operation.");
            con.close();
            return -1;
        }
        if (Validator.isFieldExistsInTable(con.getConnection(), this.clz.getSimpleName().toLowerCase(), propName) == false) {
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            logger.debug("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            con.close();
            return -1;
        }
        if (propName == null || propValue == null) {
            System.out.println("Property and value for update can't ba null. Didn't perform delete operation.");
            logger.debug("Property and value for update can't ba null. Didn't perform delete operation.");
            con.close();
            return -1;
        }

        String queryStr = "DELETE FROM " + this.clz.getSimpleName().toLowerCase() + " WHERE " + propName + "=";
        if (propValue.getClass().isPrimitive()) {
            queryStr = queryStr + propValue;
        } else {
            if (propValue.getClass().getSimpleName().equals("Character")) {
                queryStr = queryStr + "'" + String.valueOf(propValue) + "'";
            } else {
                Gson gson = new Gson();
                String jsonObj = gson.toJson(propValue);
                queryStr = queryStr + "'" + jsonObj + "'";
            }
        }
        try {
            queryStr = queryStr + " ;";
            System.out.println(queryStr);
            int result = this.con.getConnection().createStatement().executeUpdate(queryStr);
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            logger.info(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            con.close();
            return result;
        } catch (SQLException e) {
            logger.error("Failed to create sql statement while trying to delete row with " + propName + "=" + propValue + " of table " + this.clz.getSimpleName() + ". ");
            throw new RuntimeException("Failed to create statement while trying to delete row with " + propName + "=" + propValue + " of table " + this.clz.getSimpleName() + ". ", e);
        }
    }
}