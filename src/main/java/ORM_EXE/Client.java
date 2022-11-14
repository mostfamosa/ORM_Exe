package ORM_EXE;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {
    public static void main(String[] args) {

        MysqlConnection mysqlcon = MysqlConnection.getInstance();
        try {
            Connection con = mysqlcon.getConnection();
            Statement stmt = con.createStatement();

            //Creating table example:
            /*
            CreateTable<User> createTable= new CreateTable<>(User.class);
            createTable.createTableInDB();
            */

            //Delete examples:
            /*
            Delete<User> delete = new Delete<>(User.class);
            delete.deleteTable();
            delete.deleteAllRecordsByProperty("name","Carol");
            delete.deleteOneRecordByProperty("name","marol");
            */

            mysqlcon.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
