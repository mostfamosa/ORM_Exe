package ORM_EXE;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {

        MysqlConnection mysqlcon = MysqlConnection.getInstance();
        try {
            Connection con = mysqlcon.getConnection();
            Add add = new Add(Item.class);
            Update update=new Update(User.class);

            User user1 = new User(6, "Rivka", 50, 24, 'A', LocalDate.of(1998, 2, 22), false);
            update.updateItem(user1);
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

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
