package ORM_EXE.repository;
import ORM_EXE.utils.Validator;

import java.sql.SQLException;
import java.util.regex.Pattern;

 class DeleteQuery<T> {
    private final Class<T> clz;
    private MysqlConnection con;

    public DeleteQuery(Class<T> clz) {
        this.clz = clz;
    }

    public int deleteTable(){
        try {
            con = MysqlConnection.getInstance();
            int result = this.con.getConnection().createStatement().executeUpdate("DROP TABLE IF EXISTS " + this.clz.getSimpleName().toLowerCase() + ";");
            con.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete table " + this.clz.getSimpleName().toLowerCase() ,e);
        }
    }

    public int deleteOneRecordByProperty(String propName, String propValue){
        con = MysqlConnection.getInstance();
        if(Validator.isTableExists(con.getConnection(),this.clz.getSimpleName().toLowerCase())==false){
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete record by property operation.");
            con.close();
            return -1;
        }
        if(Validator.isFieldExistsInTable(con.getConnection(),this.clz.getSimpleName().toLowerCase(),propName)==false){
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            con.close();
            return -1;
        }
        try {
            int result;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if(pattern.matcher(propValue).matches()){
                result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getSimpleName().toLowerCase() + " WHERE " + propName + "="+ propValue + " LIMIT 1;");
            }else {
                result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getSimpleName().toLowerCase() + " WHERE " + propName + "='" + propValue + "'" + " OR " + propName + "='\"" + propValue + "\"'" + " LIMIT 1;");
            }
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            con.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete row with " + propName + "=" + propValue + "of table " + this.clz.getSimpleName() + ". ",e);
        }

    }

    public int deleteAllRecordsByProperty(String propName, String propValue){
        con = MysqlConnection.getInstance();
        if(Validator.isTableExists(con.getConnection(),this.clz.getSimpleName().toLowerCase())==false){
            System.out.println("Table " + this.clz.getSimpleName().toLowerCase() + " doesn't exist in database. Can't perform delete multiple records by property operation.");
            con.close();
            return -1;
        }
        if(Validator.isFieldExistsInTable(con.getConnection(),this.clz.getSimpleName().toLowerCase(),propName)==false){
            System.out.println("Column " + propName + " doesn't exist in table " + this.clz.getSimpleName().toLowerCase() + ". Can't perform delete multiple records by property operation.");
            con.close();
            return -1;
        }
        try {
            int result;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if(pattern.matcher(propValue).matches()) {
                result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getName().toLowerCase() + " WHERE " + propName + "=" + propValue + ";");
            }else{
                result = this.con.getConnection().createStatement().executeUpdate("DELETE FROM " + this.clz.getName().toLowerCase() + " WHERE " + propName + "='" + propValue + "'" + " OR " + propName + "='\"" + propValue + "\"'" + ";");
            }
            System.out.println(result + " rows deleted in table " + this.clz.getSimpleName().toLowerCase());
            con.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create statement while trying to delete row with " + propName + "=" + propValue + "of table " + this.clz.getSimpleName() + ". ", e);
        }
    }
}