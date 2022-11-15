package ORM_EXE.repository;

import ORM_EXE.entity.User;
import ORM_EXE.repository.Repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CreateTableQueryTests {
    private Repo repo;
    private Connection con;

    @BeforeEach
    void beforeEach(){
        con = MysqlConnection.getInstance().getConnection();
    }

    @AfterEach
    void afterEach() {
        MysqlConnection.getInstance().close();
    }

    @Test
    void createTableInDB_UserTableNotExists_TableCreated(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        try {
            ResultSet rs = con.prepareStatement("checksum table user;").executeQuery();
            rs.next();
            assertTrue(rs.getObject("Checksum")!=null, "Table user was not created while using the createTableInDB method.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createTableInDB_UserTableExists_ExistingTableNotChanged(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        try {
            ResultSet rsBefore = con.prepareStatement("checksum table user;").executeQuery();
            rsBefore.next();
            repo.createTableInDB();
            ResultSet rsAfter = con.prepareStatement("checksum table user;").executeQuery();
            rsAfter.next();
            assertTrue(rsBefore.getObject("Checksum")==rsAfter.getObject("Checksum"), "Table user was not created while using the createTableInDB method.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
