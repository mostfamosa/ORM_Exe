package ORM_EXE.repository;

import ORM_EXE.entity.Item;
import ORM_EXE.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

public class ReadQueryTests {
    private static Repo<User> userRepo;
    private static Repo<Item> itemRepo;

    @BeforeEach
    void setup() {
        userRepo = new Repo(User.class);
        itemRepo = new Repo(Item.class);
    }

    @Test
    void ReadQuery_getAllItems_found() {
        List<User> usersList = userRepo.getAllItems();
        boolean flag = usersList.size() > 0;
        assertEquals(true, flag, "The items are not found!");

    }

    @Test
    void ReadQuery_getAllItems_notFound() {
        assertThrows(NullPointerException.class, () -> itemRepo.getAllItems(), "The items are found!");
    }

    @Test
    void ReadQuery_getItemById_found() {
        User user = userRepo.getItemById(5);
        boolean flag = user != null;
        assertEquals(true, flag, "The item is not found!");

    }

    @Test
    void ReadQuery_getItemById_notFound() {
        assertThrows(NullPointerException.class, () -> itemRepo.getItemById(1), "The item is found!");
    }


    @Test
    void ReadQuery_getItemByProp_found() {
        List<User> usersList = userRepo.getItemsByProp("age","20");
        boolean flag = usersList.size() > 0;
        assertEquals(true, flag, "The items are not found!");

    }

    @Test
    void ReadQuery_getItemByProp_notFound() {
        assertThrows(NullPointerException.class, () -> itemRepo.getItemsByProp("age", "0"), "The items are found!");
    }

}
