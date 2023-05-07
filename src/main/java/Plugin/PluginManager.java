package Plugin;

import Plugin.Decorator.CashierDecorator;
import Plugin.Decorator.CashierDetailDecorator;
import Plugin.Decorator.Decorator;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import Plugin.Decorator.SettingsDecorator;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

@XmlRootElement
public class PluginManager implements Serializable {
    private List<Plugin> plugins;
    private List<Class<?>> clazzes;

    public PluginManager() {
        plugins = new ArrayList<>();
        clazzes = new ArrayList<>();
    }

    public void removePlugin(int idx){
        plugins.remove(idx);
    }

    public void loadPlugin(File jarFile) throws Exception {
        // Class Loader
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});

        // Read Jar File
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();

        // Save Jar File
        File destinationDirectory = new File("src/main/resources/files");
        Files.copy(jarFile.toPath(), destinationDirectory.toPath().resolve(jarFile.getName()), StandardCopyOption.REPLACE_EXISTING);

        while (entries.hasMoreElements()) {
            // Iterate all elements in Jar File
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                continue;
            }

            // Load classname
            String className = jarEntry.getName().substring(0, jarEntry.getName().length() - ".class".length());
            className = className.replace('/', '.');

            // Load class
            Class<?> clazz = classLoader.loadClass(className);
            Class<?>[] others = {Plugin.class, BasePlugin.class, Decorator.class, SettingsDecorator.class, CashierDecorator.class, CashierDetailDecorator.class};

            if (Plugin.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                // Create Plugin Class
                Class<? extends Plugin> pluginClass = clazz.asSubclass(Plugin.class);

                // Check if class is not base classes
                boolean valid = true;
                for (Class<?> cls : others) {
                    if (cls.equals(pluginClass)) {
                        valid = false;
                        break;
                    }
                }

                if(valid){
                    // Construct Plugin
                    Constructor<? extends Plugin> constructor;
                    Plugin plugin;

                    constructor = pluginClass.getDeclaredConstructor();
                    plugin = constructor.newInstance();

                    // Check if plugin already exists
                    for (Plugin check : this.plugins){
                        if (check.getPluginName().equals(plugin.getPluginName())) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("File Error");
                            // Center the header and content text
                            Label header = new Label("Not Valid");
                            header.setStyle("-fx-font-size: 1.5em;");
                            header.setStyle("-fx-font-weight: bold;");
                            header.setAlignment(Pos.CENTER);

                            Text content = new Text("Plugin already exists!");
                            content.setTextAlignment(TextAlignment.CENTER);

                            // Set the content text as a graphic
                            alert.getDialogPane().setContent(new VBox(header, content));
                            alert.setHeaderText("");
                            alert.showAndWait();
                            throw new Exception("Plugin already exists!");
                        }
                    }

                    // Add plugin to list of plugins
                    clazzes.add(pluginClass);
                    plugins.add(plugin);
                }
            } else {
                clazzes.add(clazz);
            }
        }

        // Close jar file
        jar.close();
    }

    public List<Plugin> getPlugins(){
        return plugins;
    }

    public List<Class<?>> getClazzes(){
        return this.clazzes;
    }

    public void setPlugins(List<Plugin> plugins){
        this.plugins = plugins;
    }

    public void setClazzes(List<Class<?>> clazzes){
        this.clazzes = clazzes;
    }

    public Plugin getPlugin(String pluginName){
        for(Plugin plugin : plugins){
            if(plugin.getPluginName().equals(pluginName)){
                return plugin;
            }
        }
        return new Plugin();
    }

    public void removePlugin(String pluginName){
        boolean found = false;
        for(Plugin plugin : plugins){
            if(plugin.getPluginName().equals(pluginName)){
                if(plugin.nextPlugin != null){
                    removePlugin(plugin.nextPlugin);
                }
                plugins.remove(plugin);
                found = true;
                break;
            }
        }

        // Check if the plugin was found
        if (found) {
            // Remove the associated JAR file
            String jarFileName = pluginName + ".jar";
            String jarFilePath = "src/main/resources/files/" + jarFileName;
            File jarFile = new File(jarFilePath);
            if (jarFile.exists()) {
                jarFile.delete();
            }
        }
    }

    public void loadPluginClasses() {
        // Get the directory path for JAR files
        String jarDirectoryPath = "src/main/resources/files";

        // Create a File object for the directory
        File jarDirectory = new File(jarDirectoryPath);

        // Check if the directory exists and is a directory
        if (jarDirectory.exists() && jarDirectory.isDirectory()) {
            // Get a list of JAR files in the directory
            File[] jarFiles = jarDirectory.listFiles((dir, name) -> name.endsWith(".jar"));

            // Iterate over each JAR file
            for (File jarFile : jarFiles) {
                try {
                    // Load plugins from the current JAR file
                    loadPlugin(jarFile);
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }
    }
}
