package DataStore;
import java.util.ArrayList;
import java.util.List;

public interface DataAdapter<T> {
    // Function to read data from a file
    public ObjectList<T> readData(String filePath, Class<T> classType);

    // Function to write data to a file
    public void writeData(String filePath, Class<T> classType, T newData);
}
