package assessor.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class ReportLoader {
    private volatile boolean isRefreshing = false;
    private final Object refreshLock = new Object();
    private boolean refreshInProgress = false;
    
    private final DefaultTableModel model;
    private final LoadCallbacks callbacks;
    
    public interface LoadCallbacks {
        void onLoadStart();
        void onLoadComplete();
        void onLoadError(String message);
    }
    
    public ReportLoader(DefaultTableModel model, LoadCallbacks callbacks) {
        this.model = model;
        this.callbacks = callbacks;
    }

    public void loadData() {
        if (refreshInProgress) return;
        refreshInProgress = true;
        
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeLater(callbacks::onLoadStart);
                
                try (Connection conn = DriverManager.getConnection(
                        ConfigHelper.getDbUrl(),
                        ConfigHelper.getDbUser(),
                        ConfigHelper.getDbPassword());
                     
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id AS ID, MaritalStatus, ParentGuardian, Patient, Hospital,"
                             + " HospitalAddress, Relationship, Barangay, CertificationDate, CertificationTime,"
                             + " AmountPaid, ReceiptNo, ReceiptDateIssued, PlaceIssued, Type AS Type, Signatory"
                             + " FROM reports")) {
                    
                    // Process results
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    
                    // Build column names
                    String[] columns = new String[colCount];
                    for (int i = 0; i < colCount; i++) {
                        columns[i] = meta.getColumnName(i + 1);
                    }
                    
                    // Build rows
                    Object[][] data = new Object[100][colCount];
                    int rowCount = 0;
                    while (rs.next()) {
                        if (rowCount >= data.length) {
                            data = Arrays.copyOf(data, data.length * 2);
                        }
                        for (int col = 0; col < colCount; col++) {
                            data[rowCount][col] = rs.getObject(col + 1);
                        }
                        rowCount++;
                    }
                    
                    // Final data preparation
                    final String[] finalColumns = columns;
                    final Object[][] finalData = Arrays.copyOf(data, rowCount);
                    
                    // Update table
                    SwingUtilities.invokeLater(() -> {
                        model.setColumnIdentifiers(finalColumns);
                        model.setRowCount(0);
                        for (Object[] row : finalData) {
                            model.addRow(row);
                        }
                        callbacks.onLoadComplete();
                    });
                    
                } catch (SQLException ex) {
                    SwingUtilities.invokeLater(() -> {
                        callbacks.onLoadError(ex.getMessage());
                        JOptionPane.showMessageDialog(null,
                            "Database Error: " + ex.getMessage(),
                            "Connection Failed",
                            JOptionPane.ERROR_MESSAGE);
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                refreshInProgress = false;
                try {
                    get(); // Handle exceptions
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> 
                        callbacks.onLoadError(ex.getMessage()));
                }
            }
        }.execute();
    }
    public boolean hasActiveRefresh() {
        return refreshInProgress;
    }
}
