package DataStore;
import java.io.File;
import jakarta.xml.bind.*;
import System.Inventory;

public class XMLAdapter<T> implements DataAdapter<T>{
    public Inventory<T> readData(String filePath, Class<T> classT, Class<?>[] classTypes) {
        try {
            // Creating a File object
            File file = new File(filePath);

            // Creating a JAXB context
            JAXBContext context = JAXBContext.newInstance(classTypes);

            // Create an Unmarshaller object from the JAXB context
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Reading from the XML File
            Inventory<T> objectList = (Inventory<T>) unmarshaller.unmarshal(file);
            return objectList;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Inventory<T> objectList = new Inventory<T>();
        return objectList;
    }
    @Override
    public void writeData(String filePath, Class<T> classT, Class<?>[] classTypes, Inventory<T> newData) {
        try {
            // Creating a File Object
            File file = new File(filePath);

            // Creating a JAXB context
            JAXBContext context = JAXBContext.newInstance(classTypes);

            // Create a Marshaller object from the JAXB context
            Marshaller marshaller = context.createMarshaller();

            // Set marshaller properties
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            // Write to XML file
            marshaller.marshal(newData, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
