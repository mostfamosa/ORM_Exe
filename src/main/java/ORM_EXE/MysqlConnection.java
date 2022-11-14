package ORM_EXE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {

    private final Connection connection;
    private static MysqlConnection instance = null;

    private MysqlConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orm_exe", "sqluser", "sqluserpw");
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

    public void close(){
        try {
            this.connection.close();
            instance = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
