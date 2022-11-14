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
            Add add = new Add(User.class);
//            Update update=new Update(User.class);
//            update.updateItem("grade",'B',1);
//            update.updateItem("name", "Rivka",1);
            Item item1 = new Item(6, "Rachel", "abcd");
//            Item item2 = new Item(4, "Rachel", "abcd");
//            Item item3 = new Item(5, "Rachel", "abcd");
//            List<Item> items = new ArrayList<>();
//            items.add(item1);
//            items.add(item2);
//            items.add(item3);
//            add.addMultiple(items);
           // add.addItem(item1);
            User user1 = new User(7, "Rachel", 50, 24, 'A', LocalDate.of(1998, 2, 22), false, item1);
//            User user2 = new User(5, "Rachel", 50, 24, 'A', LocalDate.of(1998, 2, 22), false);
//            User user3 = new User(6, "Rachel", 50, 24, 'A', LocalDate.of(1998, 2, 22), false);
//            List<User> users = new ArrayList<>();
//            users.add(user1);
//            users.add(user2);
//            users.add(user3);
//            add.addMultiple(users);
            add.addItem(user1);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
