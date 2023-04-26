package DataStore;

public interface DataAdapter {
    // Function to read data from a file
    public Object readData(String filePath, Class<?>[] classTypes);

    // Function to write data to a file
    public void writeData(String filePath, Class<?>[] classTypes, Object newData);
}