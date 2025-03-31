/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assessor;

import java.io.File;
import java.io.FileWriter;
import assessor.util.ConfigHelper;
import static assessor.util.FlatLaf.setupFlatLaf;
import javax.swing.SwingUtilities;

/**
 *
 * @author Toryang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        createConfigIfMissing();
        
        setupFlatLaf();
        launchUI();
        
        System.out.println("Database URL: " + ConfigHelper.getDbUrl());
        System.out.println("User: " + ConfigHelper.getDbUser());
    }
    
    private static void createConfigIfMissing() {
        File configFile = new File("config.ini");
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(
                    "[Database]\n" +
                    "Server=localhost;\n" +
                    "Port=3306;\n" +
                    "Database=certificationdb;\n" +
                    "User ID=root;\n" +
                    "Password=root;\n"
                );
                System.out.println("Created new config.ini with default values");
            } catch (Exception e) {
                System.err.println("Error creating config file:");
                e.printStackTrace();
            }
        }
    }
    
    private static void launchUI() {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
