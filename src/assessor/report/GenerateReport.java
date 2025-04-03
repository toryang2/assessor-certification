package assessor.report;

import assessor.util.ConfigHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.swing.JRViewer;

public class GenerateReport {
    
    public static void generateReport(Map<String, Object> parameters, 
                                    String customTitle, String iconPath) {
        Connection connection = null;
        try {
            FlatLightLaf.setup();
            connection = createConnection();
            
            validateIDs(parameters.get("SelectedIDs"));
            
            InputStream jrxmlStream = loadTemplate(parameters);
            JasperPrint jasperPrint = compileAndFill(jrxmlStream, parameters, connection);
            
            displayReport(jasperPrint, customTitle, iconPath);
            
        } catch (NumberFormatException e) {
            showError("Invalid ID Format", "Please select valid numeric report IDs");
        } catch (JRException e) {
            showError("Report Error", "Template error: " + e.getMessage());
        } catch (Exception e) {
            showError("Generation Failed", "Cannot generate report: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    private static Connection createConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            ConfigHelper.getDbUrl(),
            ConfigHelper.getDbUser(),
            ConfigHelper.getDbPassword()
        );
    }

    private static void validateIDs(Object ids) {
        if (!(ids instanceof List)) {
            throw new IllegalArgumentException("SelectedIDs must be a List");
        }
        
        List<?> idList = (List<?>) ids;
        if (idList.isEmpty()) {
            throw new IllegalArgumentException("No IDs selected");
        }
    }

    private static InputStream loadTemplate(Map<String, Object> parameters) 
        throws FileNotFoundException {

        // 1. Get and validate ReportType parameter
        Object typeObj = parameters.get("ReportType");
        if (typeObj == null) {
            throw new IllegalArgumentException("Missing ReportType parameter");
        }

        // 2. Process type string
        String rawType = typeObj.toString().toLowerCase();
        String[] typeParts = rawType.split("[^a-z]"); // Split on non-letters
        if (typeParts.length == 0) {
            throw new IllegalArgumentException("Invalid ReportType format");
        }
        String baseType = typeParts[0]; // Get first alphabetic segment

        // 3. Build template path
        String templatePath = String.format(
            "/assessor/report/jasper/%s.jrxml",
            baseType
        );

        // 4. Load resource
        InputStream stream = GenerateReport.class.getResourceAsStream(templatePath);
        if (stream == null) {
            throw new FileNotFoundException(
                String.format("Template not found for type '%s' (resolved path: %s)", 
                    rawType, templatePath)
            );
        }
        return stream;
    }

    private static JasperPrint compileAndFill(InputStream jrxml, 
                                            Map<String, Object> params,
                                            Connection conn) throws JRException {
        JasperDesign design = JRXmlLoader.load(jrxml);
        JasperReport report = JasperCompileManager.compileReport(design);
        return JasperFillManager.fillReport(report, params, conn);
    }

    private static void displayReport(JasperPrint print, String title, String iconPath) {
        SwingUtilities.invokeLater(() -> {
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setTitle(title);

            if (iconPath.toLowerCase().endsWith(".svg")) {
                // Load SVG icon
                FlatSVGIcon svgIcon = new FlatSVGIcon(iconPath, 32, 32);
                viewer.setIconImage(svgIcon.getImage());
            } else {
                // Load other image formats (PNG, JPG, etc.)
                URL iconUrl = GenerateReport.class.getResource(iconPath);
                if (iconUrl != null) {
                    viewer.setIconImage(new ImageIcon(iconUrl).getImage());
                } else {
                    System.err.println("Icon resource missing: " + iconPath);
                    // Optionally show an error dialog
                }
            }

            viewer.setZoomRatio(0.75f);
            viewer.setVisible(true);
        });
    }

    public static JPanel generateReportPanel(Map<String, Object> parameters,
                                           String customTitle, 
                                           String iconPath) {
        try {
            Connection connection = createConnection();
            validateIDs(parameters.get("SelectedIDs"));
            InputStream jrxmlStream = loadTemplate(parameters);
            JasperPrint jasperPrint = compileAndFill(jrxmlStream, parameters, connection);
            return createReportViewer(jasperPrint, customTitle, iconPath);
        } catch (Exception e) {
            throw new RuntimeException("Report generation failed", e);
        }
    }

    private static JPanel createReportViewer(JasperPrint print, 
                                           String title, 
                                           String iconPath) {
        JPanel container = new JPanel(new BorderLayout());
        
        // Create toolbar with title and icon
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JLabel(title));
        try {
            URL iconUrl = GenerateReport.class.getResource(iconPath);
            if (iconUrl != null) {
                toolbar.add(new JLabel(new ImageIcon(iconUrl)));
            }
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }
        
        // Add JRViewer component
        JRViewer viewer = new JRViewer(print);
//      container.add(toolbar, BorderLayout.NORTH);
        container.add(viewer, BorderLayout.CENTER);
        viewer.setZoomRatio(0.75f);
        
        return container;
    }


    private static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (Exception e) {
            System.err.println("Connection close error: " + e.getMessage());
        }
    }

    private static void showError(String title, String message) {
        JOptionPane.showMessageDialog(null,
            message,
            title,
            JOptionPane.ERROR_MESSAGE);
    }
}
