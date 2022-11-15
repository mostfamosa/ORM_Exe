package ORM_EXE;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete<T> {
    private final Class<T> clz;
    private final MysqlConnection con;

    public Delete(Class<T> clz) {
        this.clz = clz;
        this.con = MysqlConnection.getInstance();
    }

    public int deleteTable(){
        try {
            int result = this.con.getConnection().createStatement().executeUpdate("DROP TABLE IF EXISTS " + this.clz.getSimpleName().toLowerCase() + ";");
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete table " + this.clz.getSimpleName().toLowerCase() ,e);
        }
    }

    public int deleteOneRecordByProperty(String propName, String propValue){
        if(Validator.isTableExists(con.getConnection(),this.clz.getSimpleName().toLowerCase())==false){
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete record by property operation.");
            return -1;
        }
        if(Validator.isFieldExistsInTable(con.getConnection(),this.clz.getSimpleName().toLowerCase(),propName)==false){
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            return -1;
        }
        try {
            int result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getSimpleName().toLowerCase() + " WHERE " + propName +  "= \'"+ propValue + "\' " + "LIMIT 1;");
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete row with " + propName + "=" + propValue + "of table " + this.clz.getSimpleName() + ". ",e);
        }
    }

    public int deleteAllRecordsByProperty(String propName, String propValue){
        if(Validator.isTableExists(con.getConnection(),this.clz.getSimpleName().toLowerCase())==false){
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete multiple records by property operation.");
            return -1;
        }
        if(Validator.isFieldExistsInTable(con.getConnection(),this.clz.getSimpleName().toLowerCase(),propName)==false){
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            return -1;
        }
        try {
            int result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getName().toLowerCase() + " WHERE " + propName +  "='" + propValue + "';");
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete row with " + propName + "=" + propValue + "of table " + this.clz.getSimpleName() + ". ", e);
        }
    }
}