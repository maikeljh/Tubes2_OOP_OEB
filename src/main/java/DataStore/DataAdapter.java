package DataStore;
import System.Inventory;

public interface DataAdapter<T> {
    // Function to read data from a file
    public Inventory<T> readData(String filePath, Class<T> classT, Class<?>[] classTypes);

    // Function to write data to a file
    public void writeData(String filePath, Class<T> classT, Class<?>[] classTypes, Inventory<T> newData);
}