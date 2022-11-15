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
        //            Connection con = mysqlcon.getConnection();
//            Add add = new Add(Item.class);
//            Update update=new Update(User.class);

        //stmt.executeUpdate("DROP TABLE User;");
        // CreateTable<User> createTable= new CreateTable<>(User.class);
        //createTable.createTableInDB();

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

        ReadFromDB<User> userReadFromDB = new ReadFromDB<>(User.class);
        List<User> users = userReadFromDB.getAllItems();
        users.forEach(user -> System.out.println(user.toString()));
//        System.out.println(userReadFromDB.getItemsByProp("weight","80"));


//            con.close();
    }
}
