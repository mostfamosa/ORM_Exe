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
            //stmt.executeUpdate("CREATE TABLE AgentDetail (idNo INT(64) NOT NULL AUTO_INCREMENT, initials VARCHAR(2),agentDate DATE, agentCount INT(64),PRIMARY KEY (`idNo`));");
            //stmt.executeUpdate("DROP TABLE AgentDetail;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }
}
