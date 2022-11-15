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
        Connection con = mysqlcon.getConnection();

        /*Create Table*/
//        CreateTable<User> createTable = new CreateTable<>(User.class);
//        createTable.createTableInDB();


        /*Create object of the class*/
//        Add add = new Add(User.class);
//        User user = new User(1,"Daria",50,18,'A',LocalDate.of(1996,12,25),true);
//        add.addItem(user);

        /*get objects of the class*/
//        ReadFromDB<User> userReadFromDB = new ReadFromDB<>(User.class);
//        List<User> users = userReadFromDB.getAllItems();
//        users.forEach(user -> System.out.println(user.toString()));

        /*update object of the class*/
//        Update update = new Update(User.class);
//        User user2 = new User(1,"Rachel",50,18,'A',LocalDate.of(1996,12,25),true);
//        update.updateItem(user2);
//        mysqlcon.close();
//
//        ReadFromDB<User> userReadFromDB = new ReadFromDB<>(User.class);
//        List<User> users = userReadFromDB.getAllItems();
//        users.forEach(user -> System.out.println(user.toString()));

        /*delete object*/
//        Delete<User> delete = new Delete<>(User.class);
//        delete.deleteOneRecordByProperty("age","18");

    }
}
