package DataStore;
import java.io.File;

import jakarta.xml.bind.*;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import DataStore.ObjectList;

import javax.xml.namespace.QName;


public class XMLAdapter<T> implements DataAdapter<T>{
    @Override
    public ObjectList<T> readData(String filePath, Class<T> classType) {
        try {
            // Creating a File object
            File file = new File(filePath);
            // Creating a JAXB context
            JAXBContext context = JAXBContext.newInstance(ObjectList.class, classType);
            // Create a Unmarshaller object from the JAXB context
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Reading from the XML File
            ObjectList<T> objectList = (ObjectList<T>) unmarshaller.unmarshal(file);
            return objectList;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        ObjectList<T> emptyList = new ObjectList<T>();
        return emptyList;
    }
    @Override
    public void writeData(String filePath, Class<T> classType, T newData) {
        try {
            // Creating path object from the path string
            Path path = Paths.get(filePath);

            // Creating a File Object
            File file = new File(filePath);

            // Creating a JAXB context
            JAXBContext context = JAXBContext.newInstance(ObjectList.class, classType);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Initialize a list of T
            List<T> tList = new ArrayList<T>();

            // If a file exist then overwrite the file
            if (Files.exists(path)) {
                ObjectList<T> unmarshalled = (ObjectList<T>) unmarshaller.unmarshal(file);
                tList = unmarshalled.getObjectList();
            }

            tList.add(newData);

            ObjectList<T> objectList = new ObjectList<T>();
            objectList.setObjects(tList);

            // Create marshaller to write to XML file
            Marshaller marshaller = context.createMarshaller();

            // Set marshaller properties
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            String typeName = objectList.getObjectList().get(0).getClass().getSimpleName().toLowerCase();
            QName qname = new QName(typeName);
            JAXBElement<ObjectList> objectListElement = new JAXBElement<>(qname, ObjectList.class, objectList);

            // Write to XML file
            marshaller.marshal(objectListElement, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
