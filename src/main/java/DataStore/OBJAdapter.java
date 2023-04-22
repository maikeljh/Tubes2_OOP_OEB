package DataStore;
import System.Inventory;

import java.io.*;

public class OBJAdapter<T> implements DataAdapter<T> {
    // Function to read data from a file
    public Inventory<T> readData(String filePath, Class<T> classT, Class<?>[] classTypes){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Inventory<T> result = (Inventory<T>) objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR GAN");
        } catch (IOException e) {
            System.out.println("ERROR GAN");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR GAN");
        }
        Inventory<T> result = new Inventory<T>();
        return result;
    }

    // Function to write data to a file
    public void writeData(String filePath, Class<T> classT, Class<?>[] classTypes, Inventory<T> newData){
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
