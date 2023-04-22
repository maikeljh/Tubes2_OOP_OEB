package DataStore;
import System.Inventory;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONAdapter<T> implements DataAdapter<T> {
    // Function to read data from a file
    public Inventory<T> readData(String filePath, Class<T> classT, Class<?>[] classTypes){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(Inventory.class, classT);
            Inventory<T> result = mapper.readValue(new File(filePath), type);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Inventory<T> result = new Inventory<T>();
        return result;
    }

    // Function to write data to a file
    public void writeData(String filePath, Class<T> classT, Class<?>[] classTypes, Inventory<T> newData){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
