package DataStore;
import java.io.File;
import java.io.Serializable;

import jakarta.xml.bind.*;

public class XMLAdapter implements DataAdapter, Serializable {
    public Object readData(String filePath, Class<?>[] classTypes) {
        try {
            // Creating a File object
            File file = new File(filePath);

            // Creating a JAXB context
            JAXBContext context = JAXBContext.newInstance(classTypes);

            // Create an Unmarshaller object from the JAXB context
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Reading from the XML File
            Object object = unmarshaller.unmarshal(file);
            return object;
        } catch (JAXBException e) {
            // Do Nothing
        }
        return null;
    }
    @Override
    public void writeData(String filePath, Class<?>[] classTypes, Object newData) {
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
            // Do Nothing
        }
    }
}