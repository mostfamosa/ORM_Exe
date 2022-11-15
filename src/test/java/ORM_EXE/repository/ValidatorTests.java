package ORM_EXE.repository;

import ORM_EXE.entity.User;
import ORM_EXE.repository.Repo;
import ORM_EXE.utils.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTests {
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
    void isTableExists_TableUserExists_MethodReturnsTrue(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        boolean exists = Validator.isTableExists(con,User.class.getSimpleName().toLowerCase());
        assertTrue(exists==true, "Table exists but isTableExists returns false.");
    }

    @Test
    void isTableExists_TableUserNotExists_MethodReturnsFalse(){
        repo = new Repo<>(User.class);
        repo.deleteTable();
        boolean exists = Validator.isTableExists(con,User.class.getSimpleName().toLowerCase());
        assertTrue(exists==false, "Table doesn't exist but isTableExists returns true.");
    }

    @Test
    void isFieldExistsInTable_FieldExists_MethodReturnsTrue(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        boolean exists = Validator.isFieldExistsInTable(con,User.class.getSimpleName().toLowerCase(),User.class.getDeclaredFields()[0].getName());
        assertTrue(exists==true, "Field exists in table but method isFieldExistsInTable() returns false.");
    }

    @Test
    void isFieldExistsInTable_FieldNotExists_MethodReturnsFalse(){
        repo = new Repo<>(User.class);
        repo.createTableInDB();
        boolean exists = Validator.isFieldExistsInTable(con,User.class.getSimpleName().toLowerCase(),"FieldNotExists");
        assertTrue(exists==false, "Field doesn't exist in table but method isFieldExistsInTable() returns true.");
    }
}
