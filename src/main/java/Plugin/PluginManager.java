package Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private List<BasePlugin> plugins;

    public PluginManager() {
        plugins = new ArrayList<>();
    }

    public void loadPlugin(File jarFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        URLClassLoader classLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                continue;
            }
            String className = jarEntry.getName().substring(0, jarEntry.getName().length() - ".class".length());
            className = className.replace('/', '.');
            Class<?> clazz = classLoader.loadClass(className);
            if (BasePlugin.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                Class<? extends BasePlugin> pluginClass = clazz.asSubclass(BasePlugin.class);
                Constructor<? extends BasePlugin> constructor = pluginClass.getDeclaredConstructor();
                BasePlugin plugin = constructor.newInstance();
                plugins.add(plugin);
            }
        }
        jar.close();
    }

    public List<BasePlugin> getPlugins(){
        return plugins;
    }
}
