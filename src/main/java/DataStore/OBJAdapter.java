package DataStore;
import System.Inventory;

import java.io.*;

public class OBJAdapter implements DataAdapter {
    // Function to read data from a file
    public Object readData(String filePath, Class<?>[] classTypes){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR GAN");
        } catch (IOException e) {
            System.out.println("ERROR GAN");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR GAN");
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
