package DataStore;

import System.Inventory;

public class DataStore<T> {
    private DataAdapter adapter;

    public DataStore(){}

    public void setAdapter(DataAdapter adapter){
        this.adapter = adapter;
    }

    public DataAdapter getDataAdapter(){
        return this.adapter;
    }

    public void saveData(String fileName, Class<?>[] classTypes, Object newData){
        this.adapter.writeData("src/main/resources/files/" + fileName, classTypes, newData);
    }

    public Inventory<T> loadData(String fileName, Class<?>[] classTypes){
        @SuppressWarnings("unchecked")
        Inventory<T> result = (Inventory<T>) this.adapter.readData("src/main/resources/files/" + fileName, classTypes);
        return result;
    }
}
