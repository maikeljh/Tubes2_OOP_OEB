package DataStore;

import System.Inventory;
import System.Settings;

import java.util.Objects;

public class DataStore<T> {
    private DataAdapter adapter;

    public DataStore(){
        this.adapter = new XMLAdapter();
    }

    public void setAdapter(DataAdapter adapter){
        this.adapter = adapter;
    }

    public DataAdapter getDataAdapter(){
        return this.adapter;
    }

    public void checkAdapter(String format){
        if(Objects.equals(format, "xml") && adapter.getClass() != XMLAdapter.class){
            setAdapter(new XMLAdapter());
        } else if(Objects.equals(format, "json") && adapter.getClass() != JSONAdapter.class){
            setAdapter(new JSONAdapter());
        } else if(Objects.equals(format, "obj") && adapter.getClass() != OBJAdapter.class) {
            setAdapter(new OBJAdapter());
        }
    }

    public void saveData(String fileName, Settings settings, Class<?>[] classTypes, Object newData){
        checkAdapter(settings.getFormat());
        this.adapter.writeData(settings.getSaveDirectory() + "/" + fileName + "." + settings.getFormat(), classTypes, newData);
    }

    public Inventory<T> loadData(String fileName, Settings settings, Class<?>[] classTypes) throws Exception {
        checkAdapter(settings.getFormat());
        @SuppressWarnings("unchecked")
        Inventory<T> result = (Inventory<T>) this.adapter.readData(settings.getSaveDirectory() + "/" + fileName + "." + settings.getFormat(), classTypes);
        if(result == null){
            throw new Exception("Null");
        } else {
            return result;
        }
    }
}
