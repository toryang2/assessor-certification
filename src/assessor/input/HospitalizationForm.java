/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package assessor.input;

import assessor.util.DatabaseSaveHelper;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Toryang
 */
public class HospitalizationForm extends javax.swing.JFrame {
    private Consumer<Boolean> saveCallback;
    /**
     * Creates new form Input
     */
    public HospitalizationForm() {
        initComponents();
        setupActions();
        setupAmountField();
        setTitle("Hospitalization");
        setIconImages( FlatSVGUtils.createWindowIconImages( "/assessor/ui/icons/certificate.svg" ) );
    }
    
    private void setupAmountField() {
        // Initial placeholder
        txtAmount.setText("₱0.00");
        
        // Focus listeners for formatting
        txtAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                handleAmountFocusGained();
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                handleAmountFocusLost();
            }
        });

        // Document filter for input validation
        ((AbstractDocument) txtAmount.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (isValid(newText)) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + currentText.substring(offset + length);
                if (isValid(newText)) {
                    super.remove(fb, offset, length);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (isValid(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValid(String text) {
                if (!text.startsWith("₱")) return false;
                String numericPart = text.substring(1);
                return numericPart.matches("^\\d*(\\.\\d*)?$");
            }
        });
    }
    
    private void handleAmountFocusGained() {
        String currentText = txtAmount.getText();
        if (currentText.equals("₱0.00")) {
            txtAmount.setText("₱");
            txtAmount.setCaretPosition(1);
        } else {
            try {
                double amount = parseDouble(currentText);
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                nf.setGroupingUsed(false);
                nf.setMaximumFractionDigits(10);
                String raw = nf.format(amount);
                
                // Trim unnecessary decimal parts
                if (raw.contains(".")) {
                    raw = raw.replaceAll("0+$", "");
                    if (raw.endsWith(".")) raw = raw.substring(0, raw.length() - 1);
                }
                txtAmount.setText("₱" + raw);
                txtAmount.setCaretPosition(txtAmount.getText().length());
            } catch (NumberFormatException ex) {
                txtAmount.setText("₱");
            }
        }
    }

    private void handleAmountFocusLost() {
        String currentText = txtAmount.getText().replace("₱", "");
        if (currentText.isEmpty() || currentText.equals("0.00")) {
            txtAmount.setText("₱0.00");
            return;
        }
        
        try {
            double amount = parseDouble(txtAmount.getText());
            txtAmount.setText(formatAmount(amount));
        } catch (NumberFormatException ex) {
            txtAmount.setText("₱0.00");
        }
    }
    
    private String formatAmount(double amount) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(true);
        return "₱" + nf.format(amount);
    }

    private double parseDouble(String text) {
        try {
            String cleaned = text.replace("₱", "")
                                .replaceAll(",", "")
                                .replaceAll("[^\\d.]", "");
            
            if (cleaned.isEmpty()) throw new NumberFormatException("Empty amount");
            if (cleaned.startsWith(".")) cleaned = "0" + cleaned;
            if (cleaned.endsWith(".")) cleaned += "0";
            
            return new BigDecimal(cleaned).doubleValue();
        } catch (NumberFormatException | ArithmeticException e) {
            showValidationError("Invalid amount format");
            throw new NumberFormatException();
        }
    }
    
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message,
            "Validation Error",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    public void setSaveCallback(Consumer<Boolean> callback) {
        this.saveCallback = callback;
    }
    private void setupActions() {
        btnSave.addActionListener(this::saveAction);
        btnCancel.addActionListener(e -> dispose());
    }
    
    private void saveAction(ActionEvent e) {
        if(validateInput()) {
            // Create report data map
            Map<String, Object> reportData = new HashMap<>();
            Object selected = comboParentSex.getSelectedItem();

            // Map form fields to database columns
            reportData.put("Patient", txtPatientStudent.getText());
            reportData.put("ParentGuardian", txtParentGuardian.getText());
            reportData.put("ParentGuardian2", txtParentGuardian2.getText());
            
            String parentSex = (selected != null) ? selected.toString() : "";
            reportData.put("ParentSexIfSingle", parentSex);
            reportData.put("Barangay", txtBarangay.getText());
            reportData.put("Hospital", txtHospital.getText());
            reportData.put("HospitalAddress", txtHospitalAddress.getText());
            reportData.put("CertificationDate", LocalDate.now());
            reportData.put("CertificationTime", LocalTime.now());
            reportData.put("AmountPaid", "₱" + String.format("%.2f", parseDouble(txtAmount.getText())));
//            reportData.put("userInitials", currentUser.getInitials());

            // Add other fields as needed
            // reportData.put("MaritalStatus", cmbMaritalStatus.getSelectedItem());
            // reportData.put("Barangay", txtBarangay.getText());

            try {
                boolean success = DatabaseSaveHelper.saveReport("Hospitalization", reportData);

                if(success) {
                    if(saveCallback != null) {
                        saveCallback.accept(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to save hospitalization record",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                handleSaveError(ex);
            }
        }
    }
    
    private void handleSaveError(Exception ex) {
        String errorMessage = "Save failed: " + ex.getMessage();
        if(ex.getCause() instanceof SQLException) {
            errorMessage += "\nSQL State: " + ((SQLException) ex.getCause()).getSQLState();
        }
        JOptionPane.showMessageDialog(this,
            errorMessage,
            "Save Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    // Update validation to match your actual form fields
    private boolean validateInput() {
        List<JTextField> requiredFields = List.of(
            txtParentGuardian,
            txtPatientStudent, 
            txtBarangay,
            txtHospital, 
            txtHospitalAddress
        );

        for (JTextField field : requiredFields) {
            if (field.getText().trim().isEmpty()) {
                showValidationError(field.getName() + " is required");
                field.requestFocus();
                return false;
            }
        }

        try {
            double amount = parseDouble(txtAmount.getText());
            if (amount <= 0) {
                showValidationError("Amount must be greater than zero");
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtParentGuardian = new javax.swing.JTextField();
        jLabelMandatory = new javax.swing.JLabel();
        comboParentSex = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtParentGuardian2 = new javax.swing.JTextField();
        checkBoxSingle = new javax.swing.JCheckBox();
        checkBoxMarried = new javax.swing.JCheckBox();
        checkBoxGuardian = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        txtPatientStudent = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBarangay = new javax.swing.JTextField();
        comboRelationship = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtHospital = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtHospitalAddress = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        receiptNoField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        dateIssuedField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        placeIssuedField = new javax.swing.JTextField();
        btnSave = new javax.swing.JToggleButton();
        jLabelMandatory2 = new javax.swing.JLabel();
        jLabelMandatory3 = new javax.swing.JLabel();
        jLabelMandatory4 = new javax.swing.JLabel();
        jLabelMandatory5 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();

        jLabel14.setText("jLabel14");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CLIENT INFORMATION");

        jLabel2.setText("Parent/ Guardian");

        txtParentGuardian.setToolTipText("");

        jLabelMandatory.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory.setText("*");

        comboParentSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jLabel4.setText("Parent/ Guardian");

        checkBoxSingle.setText("Single");

        checkBoxMarried.setText("Married");

        checkBoxGuardian.setText("Guardian");

        jLabel5.setText("Patient/ Student");

        jLabel6.setText("Relationship");

        comboRelationship.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "son", "daughter", "spouse" }));

        jLabel7.setText("Address");

        jLabel8.setText("Hospital");

        jLabel9.setText("Hospital Address");

        jLabel10.setText("Amount");

        jLabel11.setText("Receipt No.");

        jLabel12.setText("Date Issued");

        jLabel13.setText("Place Issued");

        btnSave.setText("Save");

        jLabelMandatory2.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory2.setText("*");

        jLabelMandatory3.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory3.setText("*");

        jLabelMandatory4.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory4.setText("*");

        jLabelMandatory5.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory5.setText("*");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel8)
                                .addComponent(jLabel5)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelMandatory2)
                                    .addComponent(jLabelMandatory4))
                                .addComponent(jLabelMandatory5)
                                .addComponent(jLabelMandatory))
                            .addComponent(jLabelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAmount)
                                    .addComponent(dateIssuedField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(4, 4, 4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(receiptNoField)
                                    .addComponent(placeIssuedField)))
                            .addComponent(txtHospitalAddress)
                            .addComponent(txtHospital)
                            .addComponent(txtPatientStudent, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtParentGuardian, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)
                                        .addComponent(comboParentSex, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtParentGuardian2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(22, 22, 22)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(checkBoxSingle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(checkBoxMarried, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(44, 44, 44)
                .addComponent(checkBoxGuardian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtParentGuardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory)
                    .addComponent(comboParentSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtParentGuardian2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxSingle)
                    .addComponent(checkBoxMarried)
                    .addComponent(checkBoxGuardian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPatientStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabelMandatory3)
                    .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtHospital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtHospitalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(receiptNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(dateIssuedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(placeIssuedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HospitalizationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HospitalizationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HospitalizationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HospitalizationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HospitalizationForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JToggleButton btnSave;
    private javax.swing.JCheckBox checkBoxGuardian;
    private javax.swing.JCheckBox checkBoxMarried;
    private javax.swing.JCheckBox checkBoxSingle;
    private javax.swing.JComboBox<String> comboParentSex;
    private javax.swing.JComboBox<String> comboRelationship;
    private javax.swing.JTextField dateIssuedField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMandatory;
    private javax.swing.JLabel jLabelMandatory2;
    private javax.swing.JLabel jLabelMandatory3;
    private javax.swing.JLabel jLabelMandatory4;
    private javax.swing.JLabel jLabelMandatory5;
    private javax.swing.JTextField placeIssuedField;
    private javax.swing.JTextField receiptNoField;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBarangay;
    private javax.swing.JTextField txtHospital;
    private javax.swing.JTextField txtHospitalAddress;
    private javax.swing.JTextField txtParentGuardian;
    private javax.swing.JTextField txtParentGuardian2;
    private javax.swing.JTextField txtPatientStudent;
    // End of variables declaration//GEN-END:variables
}