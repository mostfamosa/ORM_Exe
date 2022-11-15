package ORM_EXE.repository;

import ORM_EXE.entity.Item;
import ORM_EXE.entity.ItemWithPrimaryKey;
import ORM_EXE.entity.User;
import ORM_EXE.repository.Repo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddQueryTest {
    private Repo<User> userRepo;
    private Repo<Item> itemRepo;
    private Repo<ItemWithPrimaryKey> itemWithPrimaryKeyRepo;

    @DisplayName("adding item to exists table")
    @Test
    public void givenItem_thenAddItemToExistTable() {
        userRepo = new Repo<>(User.class);
        User user = new User(10, "Rachel", 50, 20, 'C', LocalDate.of(1996, 12, 25), true);
        userRepo.addItem(user);
        assertNotNull(userRepo.getItemById(10), "failed to add item");
    }

    @DisplayName("adding item to table that does not exist")
    @Test
    public void givenItem_thenAddItemToNewTable() {
        itemRepo = new Repo<>(Item.class);
        Item item = new Item(1, "testItem", "test@test.com");
        itemRepo.addItem(item);
        Item t = itemRepo.getItemById(1);
        assertNotNull(itemRepo.getItemById(1), "failed to add item");
    }

    @DisplayName("adding null to table")
    @Test
    public void givenNull_thenItemIsNotAdded() {
        itemRepo = new Repo<>(Item.class);
        assertThrows(IllegalArgumentException.class, () -> itemRepo.addItem(null), "null added to table");
    }

    @DisplayName("adding item with primary key that already exists in the table")
    @Test
    public void givenItemWithPrimaryKeyThatExists_thenItemIsNotAdded() {
        itemWithPrimaryKeyRepo=new Repo<>(ItemWithPrimaryKey.class);
        ItemWithPrimaryKey itemWithPrimaryKey=new ItemWithPrimaryKey(1, "Rachel");
        assertThrows(IllegalArgumentException.class, () -> itemWithPrimaryKeyRepo.addItem(itemWithPrimaryKey), "item with primary key that already exists added to table");
    }
    @DisplayName("adding multiple items")
    @Test
    public void givenListOfItems_thenItemsAdded() {
        userRepo = new Repo<>(User.class);
        User user1 = new User(12,"Daria",50,20,'C', LocalDate.of(1996,12,25),true);
        User user2 = new User(13,"Rachel",50,20,'A', LocalDate.of(1996,12,25),true);
        User user3 = new User(14,"Mostafa",50,20,'B', LocalDate.of(1996,12,25),true);
        List<User> users=new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        userRepo.addMultipleItems(users);
        assertTrue(userRepo.getAllItems().stream().filter(u->u.getId()==12||u.getId()==13||u.getId()==14).count()>0,"multiple items have not been added");
    }
    @DisplayName("adding null")
    @Test
    public void givenNull_thenItemsNotAdded() {
        userRepo = new Repo<>(User.class);
        assertThrows(IllegalArgumentException.class, () -> userRepo.addMultipleItems(null), "null added to table");
    }
    @DisplayName("adding empty list")
    @Test
    public void givenEmptyList_thenItemsNotAdded() {
        userRepo = new Repo<>(User.class);
        List<User> users=new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> userRepo.addMultipleItems(users), "empty list added to table");
    }
}
