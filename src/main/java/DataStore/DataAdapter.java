package DataStore;
import System.Inventory;

public interface DataAdapter<T> {
    // Function to read data from a file
    public Inventory<T> readData(String filePath, Class<?>[] classType);

    // Function to write data to a file
    public void writeData(String filePath, Class<?>[] classType, Inventory<T> newData);
}