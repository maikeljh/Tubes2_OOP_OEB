package DataStore;

import Plugin.PluginManager;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class OBJAdapter implements DataAdapter, Serializable  {
    // Function to read data from a file
    public Object readData(String filePath, Class<?>[] classTypes){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (Exception e) {
            // Do nothing
        }
        return null;
    }

    // Function to write data to a file
    public void writeData(String filePath, Class<?>[] classTypes, Object newData){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(newData);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            // Do Nothing
        } catch (IOException e) {
            // Do Nothing
        }
    }
}
