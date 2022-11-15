package ORM_EXE.entity;

import ORM_EXE.annotations.Primary_Key;

public class ItemWithPrimaryKey {
    @Primary_Key
    private int id;
    private String name;

    public ItemWithPrimaryKey() {
    }

    public ItemWithPrimaryKey(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
