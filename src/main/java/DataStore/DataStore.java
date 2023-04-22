package DataStore;

public class DataStore<T> {
    private DataAdapter<T> adapter;

    public DataStore(){}

    public void setAdapter(DataAdapter<T> adapter){
        this.adapter = adapter;
    }

    public DataAdapter<T> getDataAdapter(){
        return this.adapter;
    }
}
