package ORM_EXE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnection {

    private final Connection connection;
    private static MysqlConnection instance = null;
    Properties prop;


    private MysqlConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm_exe", "root", "abcd1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MysqlConnection getInstance() {
        if(MysqlConnection.instance == null) {
            instance = new MysqlConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}