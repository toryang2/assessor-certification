package assessor.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigHelper {
    private static final Map<String, String> configMap = new HashMap<>();
    
    static {
        loadConfig();
    }
    
    private static void loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config.ini"))) {
            String line;
            boolean inDatabaseSection = false;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) continue;
                
                if (line.equalsIgnoreCase("[Database]")) {
                    inDatabaseSection = true;
                    continue;
                }
                
                if (inDatabaseSection) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim().replaceAll(";$", "");
                        configMap.put(key, value);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading configuration:");
            ex.printStackTrace();
        }
    }
    
    public static String getDbUrl() {
        return String.format("jdbc:mysql://%s:%s/%s",
            configMap.get("Server"),
            configMap.get("Port"),
            configMap.get("Database"));
    }
    
    public static String getDbUser() {
        return configMap.get("User ID");
    }
    
    public static String getDbPassword() {
        return configMap.get("Password");
    }
}