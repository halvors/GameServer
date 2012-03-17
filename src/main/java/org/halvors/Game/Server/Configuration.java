package org.halvors.Game.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class Configuration {
	private final GameServer server;
    private final Properties properties = new Properties();
    private final File file;
    
    private final Set<String> banList = new HashSet<String>();
    private final File banListFile = new File("banlist.txt");

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
        
        loadBanListFile();
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
    
    public void banPlayer(String name) {
    	banList.add(name.toLowerCase());
    	saveBanListFile();
    }
    
    public void unbanPlayer(String name) {
    	banList.remove(name.toLowerCase());
    	saveBanListFile();
    }
    
    private void loadBanListFile() {
        try {
        	banList.clear();
            
            BufferedReader reader = new BufferedReader(new FileReader(banListFile));
            
            for (String s = ""; (s = reader.readLine()) != null;) {
            	banList.add(s.trim().toLowerCase());
            }

            reader.close();
        } catch (Exception e) {
            server.log(Level.WARNING, "Failed to load ban list: " + e.toString());
        }
    }

    private void saveBanListFile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(banListFile, false));
            String s;
            
            for (Iterator<String> it = banList.iterator(); it.hasNext(); writer.println(s)) {
                s = (String) it.next();
            }

            writer.close();
        } catch (Exception e) {
            server.log(Level.WARNING, "Failed to save ban list: " + e.toString());
        }
    }
}