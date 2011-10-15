package main.java.org.halvors.Game.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;

public class Configuration {
	private final GameServer server;
    private final Properties properties = new Properties();
    private final File file;

    public Configuration(GameServer server, File file) {
    	this.server = server;
        this.file = file;
        
        if (file.exists()) {
            try {
                properties.load(new FileInputStream(file));
            } catch (Exception exception) {
                //a.log(Level.WARNING, "Failed to load " + file1, exception);
                createConfiguration();
            }
        } else {
            server.log(Level.WARNING, file + " does not exist");
            createConfiguration();
        }
    }

    public void createConfiguration() {
    	server.log(Level.INFO, "Generating new properties file");
        saveConfiguration();
    }
    
    public void saveConfiguration() {
        try {
            properties.store(new FileOutputStream(file), "Game server properties");
        } catch (Exception e) {
            server.log(Level.WARNING, "Failed to save " + file);
            createConfiguration();
        }
    }
    
    public String getStringProperty(String s, String s1) {
        if (!properties.containsKey(s)) {
        	properties.setProperty(s, s1);
            saveConfiguration();
        }
        
        return properties.getProperty(s, s1);
    }

    public int getIntProperty(String s, int i) {
        try {
            return Integer.parseInt(getStringProperty(s, (new StringBuilder()).append("").append(i).toString()));
        } catch(Exception exception) {
        	properties.setProperty(s, (new StringBuilder()).append("").append(i).toString());
        }
        
        return i;
    }

    public boolean getBooleanProperty(String s, boolean flag) {
        try {
            return Boolean.parseBoolean(getStringProperty(s, (new StringBuilder()).append("").append(flag).toString()));
        } catch(Exception exception) {
        	properties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
        }
        
        return flag;
    }

    public void setProperty(String s, boolean flag) {
    	properties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
        saveConfiguration();
    }
}