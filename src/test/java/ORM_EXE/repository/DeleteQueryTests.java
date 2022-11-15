package ORM_EXE.repository;

import ORM_EXE.entity.User;
import ORM_EXE.repository.Repo;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteQueryTests {
    private Repo repo;
    private Connection con;

    @Test
    void deleteTable_UserTable_TableDeleted(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        repo.deleteTable();
        con = MysqlConnection.getInstance().getConnection();
        try {
            ResultSet rs = con.prepareStatement("checksum table user;").executeQuery();
            rs.next();
            assertTrue(rs.getObject("Checksum")==null, "Delete Table was performed on table, but table still exists in database.");
            MysqlConnection.getInstance().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteOneRecordByProperty_TableNotExists_DeleteNotPerformed(){
        repo = new Repo<>(User.class);
        repo.deleteTable();
        int result = repo.deleteOneRecordByProperty(User.class.getDeclaredFields()[0].getName(), null);
        assertTrue(result==-1, "Table doesn't exist but delete operation was performed.");
    }

    @Test
    void deleteOneRecordByProperty_FieldNotExists_DeleteNotPerformed(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        int result = repo.deleteOneRecordByProperty("FieldNotExists", null);
        assertTrue(result==-1, "Field doesn't exist in table but delete operation was performed.");
    }

    @Test
    void deleteAllRecordsByProperty_TableNotExists_DeleteNotPerformed(){
        repo = new Repo<>(User.class);
        repo.deleteTable();
        int result = repo.deleteAllRecordsByProperty(User.class.getDeclaredFields()[0].getName(), null);
        assertTrue(result==-1, "Table doesn't exist but delete operation was performed.");
    }

    @Test
    void deleteAllRecordsByProperty_FieldNotExists_DeleteNotPerformed(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        int result = repo.deleteAllRecordsByProperty("FieldNotExists", null);
        assertTrue(result==-1, "Field doesn't exist in table but delete operation was performed.");
    }

    @Test
    void deleteOneRecordByProperty_idPropertyExists_RecordDeleted(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        User user = new User(10, "Rachel", 50, 20, 'C', LocalDate.of(1996, 12, 25), true);
        repo.addItem(user);
        int result = repo.deleteOneRecordByProperty("id", 10);
        assertTrue(result==1, "The record exists in table, but was not deleted.");
    }

    @Test
    void deleteAllRecordsByProperty_namePropertyExists_RecordsDeleted(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        User user = new User(10, "Rachel", 50, 20, 'C', LocalDate.of(1996, 12, 25), true);
        repo.addItem(user);
        repo.addItem(new User(11111, "Rachel", 50, 20, 'C', LocalDate.of(1996, 12, 25), true));
        int result = repo.deleteAllRecordsByProperty("name", "Rachel");
        assertTrue(result>1, "Records exist in table but were not deleted.");
    }

}
