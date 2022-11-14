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

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
