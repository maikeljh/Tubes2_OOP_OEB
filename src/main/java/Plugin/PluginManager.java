package Plugin;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@XmlRootElement
public class PluginManager implements Serializable {
    private List<Plugin> plugins;
    private List<Class<?>> clazzes;

    public PluginManager() {
        plugins = new ArrayList<>();
        clazzes = new ArrayList<>();
    }

    public void loadPlugin(File jarFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Class Loader
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});

        // Read Jar File
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();

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
            if (Plugin.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                // Create Plugin Class
                Class<? extends Plugin> pluginClass = clazz.asSubclass(Plugin.class);

                // Construct Plugin
                Constructor<? extends Plugin> constructor;
                Plugin plugin;

                constructor = pluginClass.getDeclaredConstructor();
                plugin = constructor.newInstance();

                // Add plugin to list of plugins
                clazzes.add(pluginClass);
                plugins.add(plugin);
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
}
