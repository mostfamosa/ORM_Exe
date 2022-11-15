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



            //Creating table example:

//            CreateTable<User> createTable = new CreateTable<>(User.class);
//            createTable.createTableInDB();
            Add<User> add = new Add(User.class);
            User user1 = new User(7, "Rivka", 58, 24, 'A', LocalDate.of(1998, 2, 22), false);
//            Update update = new Update(User.class);
//            update.updateField("name","Rivka",6);
            add.addItem(user1);
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
