package DataStore;
import System.Inventory;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONAdapter implements DataAdapter {
    // Function to read data from a file
    public Object readData(String filePath, Class<?>[] classTypes){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Class<?> inventoryClass = classTypes[0];
            Class<?> classT = classTypes[1];
            JavaType type = mapper.getTypeFactory().constructParametricType(inventoryClass, classT);
            Object result = mapper.readValue(new File(filePath), type);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Function to write data to a file
    public void writeData(String filePath, Class<?>[] classTypes, Object newData){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
