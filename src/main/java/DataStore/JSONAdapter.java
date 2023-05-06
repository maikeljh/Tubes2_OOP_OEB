package DataStore;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class JSONAdapter implements DataAdapter, Serializable {
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
            // Do Nothing
        }
        return null;
    }

    // Function to write data to a file
    public void writeData(String filePath, Class<?>[] classTypes, Object newData){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), newData);
        } catch (IOException e) {
            // Do Nothing
        }
    }
}
