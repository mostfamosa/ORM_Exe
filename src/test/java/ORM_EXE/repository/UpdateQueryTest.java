package ORM_EXE.repository;

import ORM_EXE.entity.Item;
import ORM_EXE.entity.ItemWithPrimaryKey;
import ORM_EXE.entity.User;
import ORM_EXE.repository.Repo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateQueryTest {
    private Repo<User> userRepo;

    @DisplayName("updating item")
    @Test
    public void givenItem_thenUpdate() {
        userRepo = new Repo<>(User.class);
        User user = new User(10, "Daria", 50, 20, 'C', LocalDate.of(1996, 12, 25), true);
        userRepo.updateItem(user);
        assertTrue(userRepo.getItemById(10).equals(user), "failed to update item");
    }
    @DisplayName("updating null throws exception")
    @Test
    public void givenNull_thenThrowException() {
        userRepo = new Repo<>(User.class);
        assertThrows(IllegalArgumentException.class, () -> userRepo.updateItem(null), "updated null");
    }
    @DisplayName("updating field with id that does not exist")
    @Test
    public void givenIdThatIsNotExistsToUpdateItem_thenThrowException() {
        User user = new User(300, "Daria", 50, 20, 'C', LocalDate.of(1996, 12, 25), true);
        assertThrows(RuntimeException.class, () -> userRepo.updateItem(user), "updated item id that is not exists");
    }
    @DisplayName("updating field")
    @Test
    public void givenField_thenUpdate() {
        userRepo = new Repo<>(User.class);
        userRepo.updateField("name","Rachel",10);
        assertTrue(userRepo.getItemById(10).getName().equals("Rachel"), "failed to update item");
    }

    @DisplayName("updating field with id that does not exist")
    @Test
    public void givenIdThatIsNotExistsToUpdateByField_thenThrowException() {
        userRepo = new Repo<>(User.class);
        assertThrows(RuntimeException.class, () -> userRepo.updateField("name","Rachel",200), "updated field with id that is not exists");
    }
    @DisplayName("updating field with null field")
    @Test
    public void givenNullField_thenThrowException() {
        userRepo = new Repo<>(User.class);
        assertThrows(IllegalArgumentException.class, () -> userRepo.updateField(null,"Rachel",10), "updated null field");
    }
    @DisplayName("updating field with null value to update")
    @Test
    public void givenNullValue_thenThrowException() {
        userRepo = new Repo<>(User.class);
        assertThrows(IllegalArgumentException.class, () -> userRepo.updateField("name",null,10), "updated null value");
    }
    @DisplayName("updating empty field")
    @Test
    public void givenEmptyField_thenThrowException() {
        userRepo = new Repo<>(User.class);
        assertThrows(IllegalArgumentException.class, () -> userRepo.updateField("","Daria",10), "updated empty field");
    }
}
