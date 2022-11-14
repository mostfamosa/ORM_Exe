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

            //stmt.executeUpdate("DROP TABLE User;");
            CreateTable<User> createTable= new CreateTable<>(User.class);
            createTable.createTableInDB();

            mysqlcon.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
