package ORM_EXE.repository;

import java.util.List;

public class Repo<T> {
    private Class<T> clz;

    private CreateTableQuery createTable;
    private AddQuery addQuery;
    private UpdateQuery updateQuery;
    private ReadQuery readQuery;
    private DeleteQuery deleteQuery;
    public Repo(Class<T> clz) {
        this.clz = clz;
        createTable = new CreateTableQuery<>(clz);
        addQuery = new AddQuery<>(clz);
        updateQuery = new UpdateQuery<>(clz);
        readQuery = new ReadQuery<>(clz);
        deleteQuery = new DeleteQuery<>(clz);
    }

    public void createTableInDB(){
        createTable.createTableInDB();
    }

    public <T> void addItem(T item){
        addQuery.addItem(item);
    }
    public <T> void addMultipleItems(List<T> items){
        addQuery.addMultipleItems(items);
    }

    public <T> void updateField(String field, T updatedValue, int id){
        updateQuery.updateField(field,updatedValue,id);
    }

    public <T> void updateItem(T item){
        updateQuery.updateItem(item);
    }

    public List<T> getAllItems(){
        return readQuery.getAllItems();
    }

    public T getItemById(int id){
        return (T) readQuery.getItemById(id);
    }

    public List<T> getItemsByProp(String propName, String propValue){
        return readQuery.getItemsByProp(propName,propValue);
    }

    public int deleteTable(){
        return deleteQuery.deleteTable();
    }

    public <T> int deleteOneRecordByProperty(String propName, T propValue){
        return deleteQuery.deleteOneRecordByProperty(propName,propValue);
    }

    public <T> int deleteAllRecordsByProperty(String propName, T propValue){
        return deleteQuery.deleteAllRecordsByProperty(propName,propValue);
    }





}
