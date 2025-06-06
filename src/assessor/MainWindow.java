/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package assessor;

import assessor.input.*;
import assessor.report.GenerateReport;
import assessor.ui.UserSettings;
import assessor.util.CurrencyRenderer;
import assessor.util.DateRenderer;
import assessor.util.NonEditableTableModel;
import assessor.util.KeyboardRefresh;
import assessor.util.RedTextRenderer;
import assessor.util.ReportLoader;
import assessor.util.ReportLoader.LoadCallbacks;
import assessor.util.TableRightRenderer;
import assessor.util.TimeRenderer;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Toryang
 */
public class MainWindow extends javax.swing.JFrame {
    
    private class LoginForm extends JDialog {
        private JTextField txtUsername;
        private JPasswordField txtPassword;
        private boolean authenticated = false;

        public LoginForm() {
            super(MainWindow.this, "Login", true);  // Changed here
            initialize();
        }

        private void initialize() {
            setLayout(new BorderLayout(10, 10));
            setPreferredSize(new Dimension(300, 200));

            // Header
            JPanel headerPanel = new JPanel();
            JLabel lblTitle = new JLabel("Assessor Login");
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
            headerPanel.add(lblTitle);

            // Form
            JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            txtUsername = new JTextField();
            txtPassword = new JPasswordField();

            formPanel.add(new JLabel("Username:"));
            formPanel.add(txtUsername);
            formPanel.add(new JLabel("Password:"));
            formPanel.add(txtPassword);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnLogin = new JButton("Login");
            JButton btnCancel = new JButton("Cancel");

            btnLogin.addActionListener(e -> authenticate());
            btnCancel.addActionListener(e -> dispose());

            buttonPanel.add(btnCancel);
            buttonPanel.add(btnLogin);

            // Layout
            add(headerPanel, BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        private void authenticate() {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            // Simple hardcoded credentials (replace with real authentication)
            if ("admin".equals(username) && "admin".equals(password)) {
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Authentication Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        public boolean isAuthenticated() {
            return authenticated;
        }
    }
    
    private UserSettings userSettings;
    private NonEditableTableModel model;
    
    private ReportLoader reportLoader;
    private boolean autoGenerateReport = false;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        userSettings = new UserSettings("defaultPassword");
        styleButtons(btnHospitalization, btnBailBond, btnNoLandHolding, btnPhilHealth, btnScholarship);
        postInitSetup();
        String[] columns = {"ID", "Type"};
        model = new NonEditableTableModel();
        model.setColumnIdentifiers(columns);
        setTabIcons();
        setupButtonActions();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 5));
        jMainPanel = new javax.swing.JPanel();
        jNoLandHoldingPanel = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        btnHospitalization = new javax.swing.JButton();
        btnBailBond = new javax.swing.JButton();
        btnNoLandHolding = new javax.swing.JButton();
        btnPhilHealth = new javax.swing.JButton();
        btnScholarship = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jReportPanel = new javax.swing.JPanel();
        jReportScrollPane = new javax.swing.JScrollPane();
        jTableReports = new javax.swing.JTable();
        jPrintReportPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1800, 1000));

        Title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("NO LANDHOLDING");

        btnHospitalization.setText("Hospitalization");
        btnHospitalization.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnBailBond.setText("Bail Bond");
        btnBailBond.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnNoLandHolding.setText("No Land Holding");
        btnNoLandHolding.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnPhilHealth.setText("PhilHealth");
        btnPhilHealth.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnScholarship.setText("Scholarship");
        btnScholarship.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jNoLandHoldingPanelLayout = new javax.swing.GroupLayout(jNoLandHoldingPanel);
        jNoLandHoldingPanel.setLayout(jNoLandHoldingPanelLayout);
        jNoLandHoldingPanelLayout.setHorizontalGroup(
            jNoLandHoldingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jNoLandHoldingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jNoLandHoldingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jNoLandHoldingPanelLayout.createSequentialGroup()
                        .addGroup(jNoLandHoldingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnScholarship, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPhilHealth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNoLandHolding, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBailBond, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHospitalization, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jNoLandHoldingPanelLayout.setVerticalGroup(
            jNoLandHoldingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jNoLandHoldingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(btnHospitalization)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBailBond)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNoLandHolding)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPhilHealth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScholarship)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jMainPanelLayout = new javax.swing.GroupLayout(jMainPanel);
        jMainPanel.setLayout(jMainPanelLayout);
        jMainPanelLayout.setHorizontalGroup(
            jMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jNoLandHoldingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jMainPanelLayout.setVerticalGroup(
            jMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jNoLandHoldingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTableReports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableReports.setShowGrid(true);
        jTableReports.getTableHeader().setResizingAllowed(false);
        jTableReports.getTableHeader().setReorderingAllowed(false);
        jReportScrollPane.setViewportView(jTableReports);

        javax.swing.GroupLayout jReportPanelLayout = new javax.swing.GroupLayout(jReportPanel);
        jReportPanel.setLayout(jReportPanelLayout);
        jReportPanelLayout.setHorizontalGroup(
            jReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jReportScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE)
                .addContainerGap())
        );
        jReportPanelLayout.setVerticalGroup(
            jReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jReportPanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jReportScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Certificates", jReportPanel);

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPrintReportPanelLayout = new javax.swing.GroupLayout(jPrintReportPanel);
        jPrintReportPanel.setLayout(jPrintReportPanelLayout);
        jPrintReportPanelLayout.setHorizontalGroup(
            jPrintReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPrintReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(1021, Short.MAX_VALUE))
        );
        jPrintReportPanelLayout.setVerticalGroup(
            jPrintReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPrintReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(615, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Report", jPrintReportPanel);

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        btnRefresh.setText("Refresh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh)
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("jMenu3");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jMainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    
    private void postInitSetup() {
        
	setTitle("Assessor Certification");
        
//        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icon/ico.png"));
//        setIconImage(icon.getImage());
        
        setIconImages( FlatSVGUtils.createWindowIconImages( "/resources/icon/favicon.svg" ) );
        
        // Configure table with fresh model immediately
        NonEditableTableModel cleanModel = new NonEditableTableModel();
        jTableReports.setModel(cleanModel);
        
        // Initialize loader with callback for UI updates
        reportLoader = new ReportLoader(cleanModel, new LoadCallbacks() {
            @Override
            public void onLoadStart() {
                btnRefresh.setEnabled(false);
                cleanModel.setRowCount(0);
                cleanModel.addRow(new Object[]{"Loading reports..."});
            }

            @Override
            public void onLoadComplete() {
                btnRefresh.setEnabled(true);
                configureColumnRenderers(); // ADD THIS LINE
                
                
                if (autoGenerateReport && jTableReports.getRowCount() > 0) {
                    jTableReports.setRowSelectionInterval(0, 0); // Select first row
                    handleReportGeneration();
                    autoGenerateReport = false; // Reset flag
                }
                
//                SwingUtilities.invokeLater(() -> {
//                    try {
//                        TableColumnModel columnModel = jTableReports.getColumnModel();
//                        int patientColumnIndex = columnModel.getColumnIndex("PATIENT"); // Case-sensitive!
//                        columnModel.moveColumn(patientColumnIndex, 1);
//                        int typeColumnIndex = columnModel.getColumnIndex("Type");
//                        columnModel.moveColumn(typeColumnIndex, 1);
//                    } catch (IllegalArgumentException e) {
//                        System.out.println("Column 'patient' not found after loading data.");
//                    }
//                });
//                for (int i = 0; i < jTableReports.getColumnModel().getColumnCount(); i++) {
//                    System.out.println("Column " + i + ": " + jTableReports.getColumnModel().getColumn(i).getHeaderValue());
//                }
                // Add debug output
                System.out.println("Columns in model:");
                for (int i = 0; i < model.getColumnCount(); i++) {
                    System.out.println(i + ": " + model.getColumnName(i));
                }

                System.out.println("First 5 rows:");
                for (int row = 0; row < Math.min(5, model.getRowCount()); row++) {
                    System.out.println(
                        "ID: " + model.getValueAt(row, 0) + 
                        " | Type: " + model.getValueAt(row, 1)
                    );
                }
                try {
                    jTableReports.getColumn("Type");
                    jTableReports.getColumn("ID");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                        "Critical error: Required columns not found!\n" +
                        "Please check database connection and schema.",
                        "Configuration Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onLoadError(String message) {
                cleanModel.setRowCount(0);
                cleanModel.addRow(new Object[]{"Error: " + message});
                btnRefresh.setEnabled(true);
            }
        });
    
        // Setup refresh button
        btnRefresh.addActionListener(e -> reportLoader.loadData());
        
        new KeyboardRefresh(reportLoader, this);
                
        // Initial load
        reportLoader.loadData();
        
        setupUserButton();
        
        // Mouse click listener
        jTableReports.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    handleReportGeneration();
                }
            }
        });

        // Key press listener
        jTableReports.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleReportGeneration();
                }
            }
        });
    }
    
    private void setupButtonActions() {
        btnHospitalization.addActionListener(e -> openHospitalizationForm());
        btnScholarship.addActionListener(e -> openScholarshipForm());
    }
    
    private void openHospitalizationForm() {
        HospitalizationForm form = new HospitalizationForm();
        
        // Set callback for save completion
        form.setSaveCallback(success -> {
            if (success) {
                autoGenerateReport = true; // Set flag to trigger report after reload
                reportLoader.loadData();
            }
        });
        
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }
    
        private void openScholarshipForm() {
        ScholarshipForm form = new ScholarshipForm();
        
        // Set callback for save completion
        form.setSaveCallback(success -> {
            if (success) {
                autoGenerateReport = true; // Set flag to trigger report after reload
                reportLoader.loadData();
            }
        });
        
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }
    
    public void setTabIcons() {
    // Load SVG icons
    FlatSVGIcon certificateIcon = new FlatSVGIcon("assessor/ui/icons/certificate.svg", 16, 16);
    FlatSVGIcon reportIcon = new FlatSVGIcon("assessor/ui/icons/printer.svg", 16, 16);

    // Set Certificates tab (NO close button)
    setTabComponent(jReportPanel, "Certificates", certificateIcon, false);

    // Set Report tab (WITH close button), initially hidden
    setTabComponent(jPrintReportPanel, "Report", reportIcon, true);
    jTabbedPane.remove(jPrintReportPanel); // Hide tab by default
}

    // Custom method for setting tab components
    private void setTabComponent(JPanel panel, String title, FlatSVGIcon icon, boolean hasCloseButton) {
        int index = jTabbedPane.indexOfComponent(panel);
        if (index == -1) return; // Ensure tab exists

        // Panel for tab (title + icon + optional close button)
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tabPanel.setOpaque(false);

        // Label for icon + title
        JLabel titleLabel = new JLabel(title, icon, JLabel.LEFT);
        tabPanel.add(titleLabel);

        // Add close button only if allowed
        if (hasCloseButton) {
            JButton closeButton = new JButton(new FlatSVGIcon("assessor/ui/icons/close.svg", 25, 25));
            closeButton.setBorderPainted(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusable(false);
            closeButton.setPreferredSize(new Dimension(16, 16));

            // Remove tab on button click
            closeButton.addActionListener(e -> {
                int tabIndex = jTabbedPane.indexOfComponent(panel);
                if (tabIndex != -1) {
                    jTabbedPane.remove(tabIndex);
                }
            });

            tabPanel.add(closeButton);
        }

        // Set custom tab
        jTabbedPane.setTabComponentAt(index, tabPanel);
    }

    // Method to show jPrintReportPanel when needed
    public void showPrintReportTab() {
        if (jTabbedPane.indexOfComponent(jPrintReportPanel) == -1) {
            jTabbedPane.addTab("Report", null, jPrintReportPanel);
            setTabIcons(); // Reapply icons and close buttons
        }
    }

    private void handleReportGeneration() {
        int row = jTableReports.getSelectedRow();
        if (row == -1) return;

        System.out.println("handleReportGeneration called");
        try {
            // Get raw values
            int idColumn = jTableReports.getColumn("ID").getModelIndex();
            int typeColumn = jTableReports.getColumn("Type").getModelIndex();

            Object rawId = jTableReports.getValueAt(row, idColumn);
            Object rawType = jTableReports.getValueAt(row, typeColumn);

            // Validate and convert
            List<Integer> selectedIDs = new ArrayList<>();
            try {
                selectedIDs.add(Integer.parseInt(rawId.toString()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String reportType = rawType != null ? rawType.toString().trim() : "unknown";

            // Prepare parameters
            Map<String, Object> params = new HashMap<>();
            params.put("SelectedIDs", selectedIDs);
            params.put("ReportType", reportType);  // Direct type from database

//            GenerateReport.generateReport(
//                params,
//                reportType + " Report",  // Title for display
//                "assessor/ui/icons/printer.svg"

            JPanel reportViewer = GenerateReport.generateReportPanel(
            params,
            reportType + " Report",
            "/assessor/ui/icons/printer.png"
            );

            configureReportTab(reportViewer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error generating report: " + e.getMessage(), 
                "Generation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configureReportTab(JPanel reportContent) {
        // Clear previous content
        jPrintReportPanel.removeAll();
        jPrintReportPanel.setLayout(new BorderLayout());
        jPrintReportPanel.add(reportContent, BorderLayout.CENTER);
        jPrintReportPanel.revalidate();
        jPrintReportPanel.repaint();

        // Add tab if not present
        if (!tabExists(jPrintReportPanel)) {
            jTabbedPane.addTab("Report", jPrintReportPanel);
            FlatSVGIcon reportIcon = new FlatSVGIcon("assessor/ui/icons/printer.svg", 16, 16);
            setTabComponent(jPrintReportPanel, "Report", reportIcon, true);
        }

        // Switch to report tab
        jTabbedPane.setSelectedComponent(jPrintReportPanel);
    }

    private boolean tabExists(JPanel panel) {
        for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
            if (jTabbedPane.getComponentAt(i) == panel) {
                return true;
            }
        }
        return false;
    }
    
    private void setupUserButton() {
        JMenuBar menuBar = this.jMenuBar1;
        menuBar.add(Box.createHorizontalGlue());

        JButton userButton = new JButton();
        userButton.putClientProperty("JButton.buttonType", "toolBarButton");
        userButton.setFocusable(false);
        userButton.setToolTipText("User Settings");

        // Load SVG icon
        try {
            FlatSVGIcon svgIcon = new FlatSVGIcon("assessor/ui/icons/users.svg", 15, 15);
            userButton.setIcon(svgIcon);
        } catch(Exception e) {
            // Fallback to standard icon
            ImageIcon icon = new ImageIcon(getClass().getResource("/assessor/ui/icons/users.png"));
            userButton.setIcon(icon);
            userButton.setText("User");
        }

        userButton.addActionListener(e -> showPasswordDialog());
        menuBar.add(userButton);
    }
    
    private void showPasswordDialog() {
        JDialog dialog = new JDialog(this, "Change Password", true);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel headerLabel = new JLabel("Account Security");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        headerPanel.add(headerLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        InputField currentPassField = new InputField("Current Password", true);
        InputField newPassField = new InputField("New Password", true);
        newPassField.setStrengthMeter(true);
        InputField confirmPassField = new InputField("Confirm Password", true);

        formPanel.add(currentPassField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(newPassField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(confirmPassField);

        // Action Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JButton btnCancel = new JButton("Cancel");
        JButton btnSubmit = new JButton("Update Password");
        btnSubmit.setBackground(new Color(0, 120, 215));
        btnSubmit.setForeground(Color.WHITE);

        btnSubmit.addActionListener(e -> processPasswordChange(
            currentPassField.getPassword(),
            newPassField.getPassword(),
            confirmPassField.getPassword(),
            dialog
        ));

        btnCancel.addActionListener(e -> dialog.dispose());

        actionPanel.add(btnCancel);
        actionPanel.add(btnSubmit);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(actionPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Custom Input Field Component
    class InputField extends JPanel {
        private JPasswordField passwordField;
        private JProgressBar strengthBar;

        public InputField(String label, boolean isPassword) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel fieldLabel = new JLabel(label);
            fieldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            if(isPassword) {
                passwordField = new JPasswordField(20);
            } else {
                passwordField = new JPasswordField(20);
                passwordField.setEchoChar((char)0);
            }

            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));

            add(fieldLabel);
            add(Box.createVerticalStrut(5));
            add(passwordField);
        }

        public void setStrengthMeter(boolean enabled) {
            if(enabled) {
                strengthBar = new JProgressBar(0, 4);
                strengthBar.setStringPainted(true);
                strengthBar.setString("Strength");
                strengthBar.setForeground(new Color(0, 180, 0));
                add(Box.createVerticalStrut(5));
                add(strengthBar);

                passwordField.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) { update(); }
                    public void insertUpdate(DocumentEvent e) { update(); }
                    public void removeUpdate(DocumentEvent e) { update(); }

                    private void update() {
                        String pass = new String(passwordField.getPassword());
                        int strength = calculatePasswordStrength(pass);
                        strengthBar.setValue(strength);
                    }
                });
            }
        }

        private int calculatePasswordStrength(String pass) {
            int strength = 0;
            if(pass.length() >= 8) strength++;
            if(pass.matches(".*[A-Z].*")) strength++;
            if(pass.matches(".*\\d.*")) strength++;
            if(pass.matches(".*[^a-zA-Z0-9].*")) strength++;
            return strength;
        }

        public char[] getPassword() {
            return passwordField.getPassword();
        }
    }
    
        private void processPasswordChange(char[] currentPass, 
                                      char[] newPass, 
                                      char[] confirmPass, 
                                      JDialog dialog) {
        try {
            if (!userSettings.validateCurrentPassword(currentPass)) {
                JOptionPane.showMessageDialog(dialog,
                    "Invalid current password",
                    "Authentication Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            userSettings.validateNewPassword(newPass, confirmPass);
            userSettings.changePassword(newPass);

            JOptionPane.showMessageDialog(dialog,
                "Password updated successfully!\nPlease login again.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            dialog.dispose();
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(dialog,
                ex.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } finally {
            Arrays.fill(currentPass, '\0');
            Arrays.fill(newPass, '\0');
            Arrays.fill(confirmPass, '\0');
        }
    }
    
    private void configureColumnRenderers() {
        DefaultTableModel model = (DefaultTableModel) jTableReports.getModel();
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jTableReports.setRowSorter(sorter);
        
            // Set custom header renderer to prevent shifting
        JTableHeader header = jTableReports.getTableHeader();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Keep the default system colors
            setBackground(UIManager.getColor("TableHeader.background"));
            setForeground(UIManager.getColor("TableHeader.foreground"));
            setFont(UIManager.getFont("TableHeader.font"));

            // Align text to center
            setHorizontalAlignment(SwingConstants.CENTER);

            // Ensure a visible border
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));

            setOpaque(true); // Ensure background is painted properly
            return this;
        }
    };
    header.setDefaultRenderer(headerRenderer);
        
        for (int i = 0; i < model.getColumnCount(); i++) {
            TableColumn column = jTableReports.getColumnModel().getColumn(i);
            String colName = model.getColumnName(i).toLowerCase();
            switch (colName) {
                case "id":
                    column.setHeaderValue("ID");
                    column.setCellRenderer(new TableRightRenderer());
                    column.setPreferredWidth(50);
                    column.setMaxWidth(50);
                    column.setMinWidth(50);
                    break;
                case "patient":
                    column.setHeaderValue("PATIENT");
                    column.setPreferredWidth(200);
                    column.setMaxWidth(200);
                    column.setMinWidth(200);
                    break;
                case "relationship":
                    column.setPreferredWidth(80);
                    column.setMaxWidth(80);
                    column.setMinWidth(80);
                    break;
                case "hospitaladdress":
                    column.setHeaderValue("Hospital Address");
                    column.setPreferredWidth(250);
                    column.setMaxWidth(250);
                    column.setMinWidth(250);
                    break;
                case "maritalstatus":
                    column.setHeaderValue("Marital Status");
                    column.setPreferredWidth(90);
                    column.setMaxWidth(90);
                    column.setMinWidth(90);
                    break;
                case "parentguardian":
                    column.setHeaderValue("Parent");
                    column.setPreferredWidth(120);
                    column.setMaxWidth(200);
                    column.setMinWidth(200);
                    break;
                case "parentguardian2":
                    column.setHeaderValue("Parent");
                    column.setPreferredWidth(200);
                    column.setMaxWidth(200);
                    column.setMinWidth(200);
                    break;
                case "parentsexifsingle":
                    column.setPreferredWidth(0);
                    column.setMaxWidth(0);
                    column.setMinWidth(0);
                    column.setResizable(false);
                    break;
                case "certificationdate":
                    column.setHeaderValue("Certification Date");
                    column.setCellRenderer(new DateRenderer());
                    column.setPreferredWidth(120);  // mm-dd-yyyy format
                    column.setMaxWidth(120);
                    column.setMinWidth(120);
                    break;
                case "certificationtime":
                    column.setHeaderValue("Certification Time");
                    column.setCellRenderer(new TimeRenderer());
                    column.setPreferredWidth(100);  // hh:mm a format
                    column.setMaxWidth(120);
                    column.setMinWidth(120);
                    break;
                    
                case "parent2":
                    column.setHeaderValue("Parent");
                    column.setPreferredWidth(200);
                    column.setMaxWidth(200);
                    column.setMinWidth(200);
                    break;
                case "type":
                    column.setPreferredWidth(100);
                    column.setMaxWidth(100);
                    column.setMinWidth(100);
                    break;
                case "amountpaid":
                    column.setHeaderValue("Amount Paid");
                    column.setCellRenderer(new CurrencyRenderer());
                    column.setPreferredWidth(80);  // Currency needs space for symbols/commas
                    column.setMaxWidth(80);
                    column.setMinWidth(80);
                    break;
                case "receiptno":
                    column.setHeaderValue("Receipt No.");
                    column.setCellRenderer(new RedTextRenderer());
                    column.setPreferredWidth(80);  // Currency needs space for symbols/commas
                    column.setMaxWidth(80);
                    column.setMinWidth(80);
                    break;
                case "receiptdateissued":
                    column.setHeaderValue("Receipt Date Issued");
                    column.setCellRenderer(new DateRenderer());
                    column.setPreferredWidth(120);  // Currency needs space for symbols/commas
                    column.setMaxWidth(120);
                    column.setMinWidth(120);
                    break;
                case "placeissued":
                    column.setHeaderValue("Place Issued");
                    column.setPreferredWidth(120);  // Currency needs space for symbols/commas
                    column.setMaxWidth(120);
                    column.setMinWidth(120);
                    break;
                    
                default:
                    column.setPreferredWidth(200);
                    column.setMaxWidth(200);
                    column.setMinWidth(200);
            }
        }
        jTableReports.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableReports.getTableHeader().setReorderingAllowed(false);
        jTableReports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
public static void main(String args[]) {
    // Set up FlatLaf
    try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch(Exception ex) {
        System.err.println("Failed to initialize FlatLaf");
    }

    // Create and show login FIRST
    SwingUtilities.invokeLater(() -> {
        MainWindow window = new MainWindow();  // MainWindow instance needed for inner class
        LoginForm login = window.new LoginForm();
        login.setVisible(true);
        
        // This listener handles the login result
        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (login.isAuthenticated()) {
                    window.setVisible(true);  // Show main window
                } else {
                    System.exit(0);  // Exit if failed
                }
            }
        });
    });
}
    
    
    
    private void styleButtons(JButton... buttons) {
        for (JButton button : buttons) {
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!button.getModel().isPressed()) {
                        button.setForeground(new Color(0, 100, 200));
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!button.getModel().isPressed()) {
                        button.setForeground(Color.BLACK);
                    }
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    button.setForeground(Color.BLACK);
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    //Check if mouse is still over button after click
                    if (button.contains(e.getPoint())) {
                        button.setForeground(new Color(0, 100, 200));
                    } else {
                        button.setForeground(Color.BLACK);
                    }
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private javax.swing.JButton btnBailBond;
    private javax.swing.JButton btnHospitalization;
    private javax.swing.JButton btnNoLandHolding;
    private javax.swing.JButton btnPhilHealth;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnScholarship;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jMainPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jNoLandHoldingPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPrintReportPanel;
    private javax.swing.JPanel jReportPanel;
    private javax.swing.JScrollPane jReportScrollPane;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableReports;
    // End of variables declaration//GEN-END:variables
}
