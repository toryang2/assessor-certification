/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package assessor.input;

import assessor.util.DatabaseSaveHelper;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import java.awt.Component;
import java.awt.Container;
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
import java.util.Set;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import net.miginfocom.swing.MigLayout;
import raven.datetime.DatePicker;
import raven.datetime.DateSelectionAble;


/**
 *
 * @author Toryang
 */
public class HospitalizationForm extends javax.swing.JFrame {
    private Consumer<Boolean> saveCallback;
    private DatePicker datePicker;
    
    @Override
    public void dispose(){
        if (datePicker !=null) {
            datePicker.closePopup();
        }
        super.dispose();
    }
//    private DatePicker receiptDateIssuedPickerUI;
    /**
     * Creates new form Input
     */
    public HospitalizationForm() {
        initComponents();
        setupActions();
        setupAmountField();
        setTitle("Hospitalization");
        setIconImages( FlatSVGUtils.createWindowIconImages( "/assessor/ui/icons/certificate.svg" ) );
        datePicker = new DatePicker();
        JFormattedTextField receiptDateIssuedPicker = new JFormattedTextField();
        datePicker.setEditor(receiptDateIssuedPicker);
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
        datePicker.setDateFormat("MM/dd/yyyy");
        datePicker.setUsePanelOption(true);
        datePicker.setCloseAfterSelected(true);
        datePicker.setDateSelectionAble(new DateSelectionAble() {
            @Override
            public boolean isDateSelectedAble(LocalDate localDate) {
                return !localDate.isAfter(LocalDate.now());
            }
        });
        
        // Clear existing layout
    Container contentPane = getContentPane();
    contentPane.removeAll();
    
    // Set up MigLayout
    contentPane.setLayout(new MigLayout(
        "insets 20 30 20 30, gap 10 15",
        // 6-column layout: [labels][field1][field2][labels][field3][fill]
        "[left][50:50:50][100:100:100][left][100:100:100]", 
        "[][][][][][][][][][][]"
    ));

    // Row 0: Title
        contentPane.add(labelTitle, "span 5, center, wrap");

        // Row 1: Marital Status
        JPanel maritalPanel = new JPanel(new MigLayout("gap 15, insets 0, align center"));
        maritalPanel.add(checkBoxSingle);
        maritalPanel.add(checkBoxMarried);
        maritalPanel.add(checkBoxGuardian);
        contentPane.add(maritalPanel, "span 5, center, wrap");

        // Row 2: Parent/Guardian
        contentPane.add(labelParentGuardian, "cell 0 2");
        contentPane.add(txtParentGuardian, "cell 1 2 3 1, growx, pushx, w 100%");
        contentPane.add(comboParentSex, "cell 4 2, growx, pushx, w 100%, wrap");

        // Row 3: Parent/Guardian 2
        contentPane.add(labelParentGuardian2, "cell 0 3");
        contentPane.add(txtParentGuardian2, "cell 1 3 4 1, growx, pushx, w 100%, wrap");

        // Row 4: Patient/Student
        contentPane.add(labelPatientStudent);
        contentPane.add(txtPatientStudent, "cell 1 4 4 1, growx, pushx, w 100%, wrap");

        // Row 5: Address
        contentPane.add(labelAddress);
        contentPane.add(txtBarangay, "cell 1 5 2 1, growx, pushx, w 100%, wrap");
        contentPane.add(labelRelationship, "cell 3 5");
        contentPane.add(comboRelationship, "cell 4 5, growx, wrap");

        // Row 6: Hospital
        contentPane.add(labelHospital, "cell 0 6");
        contentPane.add(txtHospital, "cell 1 6 4 1, growx, pushx, w 100%, wrap");

        // Row 7: Hospital Address
        contentPane.add(labelHospitalAddress, "cell 0 7");
        contentPane.add(txtHospitalAddress, "cell 1 7 4 1, growx, pushx, w 100%, wrap");

        // Row 8: Amount & Receipt
        contentPane.add(labelAmount, "cell 0 8");
        contentPane.add(txtAmount, "cell 1 8 2 1, growx");
        contentPane.add(labelReceiptNo, "cell 3 8");
        contentPane.add(receiptNoField, "cell 4 8, growx, wrap");

        // Row 9: Date & Place
        contentPane.add(labelDateIssued, "cell 0 9");
        contentPane.add(receiptDateIssuedPicker, "cell 1 9 2 1, growx"); // Using DatePicker
        contentPane.add(labelPlaceIssued, "cell 3 9");
        contentPane.add(placeIssuedField, "cell 4 9, growx, wrap");

        // Row 10: Buttons
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, align right"));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        contentPane.add(buttonPanel, "span 5, right");

        pack();
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
                super.insertString(fb, offset, text, attr);
                SwingUtilities.invokeLater(() -> reformatAmount(fb.getDocument()));
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) 
                throws BadLocationException {
                super.remove(fb, offset, length);
                SwingUtilities.invokeLater(() -> reformatAmount(fb.getDocument()));
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                throws BadLocationException {
                super.replace(fb, offset, length, text, attrs);
                SwingUtilities.invokeLater(() -> reformatAmount(fb.getDocument()));
            }

            private boolean isValid(String text) {
                if (!text.startsWith("₱")) return false;
                String numericPart = text.substring(1).replace(",", ""); // Allow commas by ignoring them in validation
                return numericPart.matches("^\\d*(\\.\\d*)?$");
            }
            
            private void reformatAmount(Document doc) {
                try {
                    String originalText = doc.getText(0, doc.getLength());
                    String text = originalText.replace("₱", "").replaceAll(",", "");

                    // Handle empty case
                    if (text.isEmpty()) {
                        txtAmount.setText("₱");
                        
                        return;
                    }

                    // Handle multiple decimal points
                    int decimalIndex = text.indexOf('.');
                    String integerPart = decimalIndex != -1 ? text.substring(0, decimalIndex) : text;
                    String decimalPart = decimalIndex != -1 ? 
                        "." + text.substring(decimalIndex + 1).replaceAll("\\.00", "") : "";

                    // Format integer part with commas
                    if (!integerPart.isEmpty()) {
                        try {
                            long num = Long.parseLong(integerPart);
                            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                            integerPart = nf.format(num);
                        } catch (NumberFormatException e) {
                            // Fallback to unformatted if invalid number
                            integerPart = text.split("\\.")[0];
                        }
                    }

                    int originalCaretPos = txtAmount.getCaretPosition();

                    // Build new formatted text
                    String newText = "₱" + integerPart + decimalPart;
                    txtAmount.setText(newText);

                    // Calculate new caret position
                    String cleanOriginal = originalText.replaceAll("[^0-9.]", "");
                    String cleanNew = newText.substring(1).replaceAll("[^0-9.]", "");

                    int newCaretPos = originalCaretPos;
                    if (originalCaretPos > 0) {
                        // Count valid characters before original caret
                        int validCharsBefore = 0;
                        for (int i = 0; i < originalCaretPos && i < originalText.length(); i++) {
                            char c = originalText.charAt(i);
                            if (Character.isDigit(c) || c == '.') validCharsBefore++;
                        }

                        // Find equivalent position in new text
                        int currentValidCount = 0;
                        newCaretPos = 1; // Start after currency symbol
                        while (newCaretPos < newText.length() && currentValidCount < validCharsBefore) {
                            char c = newText.charAt(newCaretPos);
                            if (Character.isDigit(c) || c == '.') currentValidCount++;
                            newCaretPos++;
                        }
                    }

                    // Ensure valid position
                    newCaretPos = Math.min(Math.max(newCaretPos, 1), newText.length());
                    txtAmount.setCaretPosition(newCaretPos);

                } catch (BadLocationException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
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
    }
    
    private void handleAmountFocusGained() {
        if (txtAmount.getText().equals("₱0.00")) {
            txtAmount.setText("₱.00");
            txtAmount.setCaretPosition(1);
        }
    }

    private void handleAmountFocusLost() {
        try {
            double amount = parseDouble(txtAmount.getText());
            txtAmount.setText(formatAmount(amount));
        } catch (NumberFormatException ex) {
            txtAmount.setText("₱0.00");
        }
    }
    
    private String formatAmount(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return "₱" + nf.format(amount);
    }

    private double parseDouble(String text) throws NumberFormatException {
        try {
            String cleaned = text.replace("₱", "")
                                .replaceAll(",", "") // Remove commas before parsing
                                .replaceAll("[^\\d.]", "");
            
            if (cleaned.isEmpty()) throw new NumberFormatException("Empty amount");
            if (cleaned.startsWith(".")) cleaned = "0" + cleaned;
            if (cleaned.endsWith(".")) cleaned += "0";
            
            return new BigDecimal(cleaned).doubleValue();
        } catch (NumberFormatException | ArithmeticException e) {
//            showValidationError("Invalid amount format");
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
        handleAmountFocusLost();
        if(validateInput()) {
            // Create report data map
            Map<String, Object> reportData = new HashMap<>();
            Object selected = comboParentSex.getSelectedItem();
            if(selected != null) {
                reportData.put("ParentSexIfSingle", selected.toString());
            }

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
            double amount = parseDouble(txtAmount.getText());
            reportData.put("AmountPaid", formatAmount(amount));
            LocalDate receiptDate = datePicker.getSelectedDate();
            if (receiptDate != null) {
                reportData.put("ReceiptDateIssued", receiptDate);
            }
            
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
        labelTitle = new javax.swing.JLabel();
        labelParentGuardian = new javax.swing.JLabel();
        txtParentGuardian = new javax.swing.JTextField();
        jLabelMandatory = new javax.swing.JLabel();
        comboParentSex = new javax.swing.JComboBox<>();
        labelParentGuardian2 = new javax.swing.JLabel();
        txtParentGuardian2 = new javax.swing.JTextField();
        checkBoxSingle = new javax.swing.JCheckBox();
        checkBoxMarried = new javax.swing.JCheckBox();
        checkBoxGuardian = new javax.swing.JCheckBox();
        labelPatientStudent = new javax.swing.JLabel();
        txtPatientStudent = new javax.swing.JTextField();
        labelRelationship = new javax.swing.JLabel();
        txtBarangay = new javax.swing.JTextField();
        comboRelationship = new javax.swing.JComboBox<>();
        labelAddress = new javax.swing.JLabel();
        labelHospital = new javax.swing.JLabel();
        txtHospital = new javax.swing.JTextField();
        labelHospitalAddress = new javax.swing.JLabel();
        txtHospitalAddress = new javax.swing.JTextField();
        labelAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        labelReceiptNo = new javax.swing.JLabel();
        receiptNoField = new javax.swing.JTextField();
        labelDateIssued = new javax.swing.JLabel();
        labelPlaceIssued = new javax.swing.JLabel();
        placeIssuedField = new javax.swing.JTextField();
        btnSave = new javax.swing.JToggleButton();
        jLabelMandatory2 = new javax.swing.JLabel();
        jLabelMandatory3 = new javax.swing.JLabel();
        jLabelMandatory4 = new javax.swing.JLabel();
        jLabelMandatory5 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        receiptDateIssuedPicker = new javax.swing.JTextField();

        jLabel14.setText("jLabel14");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        labelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("CLIENT INFORMATION");

        labelParentGuardian.setText("Parent/ Guardian");

        txtParentGuardian.setToolTipText("");

        jLabelMandatory.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMandatory.setText("*");

        comboParentSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        labelParentGuardian2.setText("Parent/ Guardian");

        checkBoxSingle.setText("Single");

        checkBoxMarried.setText("Married");

        checkBoxGuardian.setText("Guardian");

        labelPatientStudent.setText("Patient/ Student");

        labelRelationship.setText("Relationship");

        comboRelationship.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "son", "daughter", "spouse" }));
        comboRelationship.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRelationshipActionPerformed(evt);
            }
        });

        labelAddress.setText("Address");

        labelHospital.setText("Hospital");

        labelHospitalAddress.setText("Hospital Address");

        labelAmount.setText("Amount");

        labelReceiptNo.setText("Receipt No.");

        labelDateIssued.setText("Date Issued");

        labelPlaceIssued.setText("Place Issued");

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
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDateIssued)
                            .addComponent(labelHospitalAddress)
                            .addComponent(labelAmount)
                            .addComponent(labelHospital)
                            .addComponent(labelPatientStudent)
                            .addComponent(labelAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelMandatory2)
                                    .addComponent(jLabelMandatory4))
                                .addComponent(jLabelMandatory5))
                            .addComponent(jLabelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHospitalAddress)
                            .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHospital)
                            .addComponent(txtPatientStudent, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(197, 197, 197)
                                .addComponent(labelRelationship)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboRelationship, 0, 1, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(receiptDateIssuedPicker))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(labelReceiptNo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelPlaceIssued)
                                        .addGap(4, 4, 4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(receiptNoField)
                                    .addComponent(placeIssuedField))))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelParentGuardian2)
                            .addComponent(labelParentGuardian))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelMandatory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxSingle)
                                .addGap(18, 18, 18)
                                .addComponent(checkBoxMarried)
                                .addGap(18, 18, 18)
                                .addComponent(checkBoxGuardian))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtParentGuardian2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtParentGuardian, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(17, 17, 17)
                                    .addComponent(comboParentSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkBoxSingle)
                    .addComponent(checkBoxMarried)
                    .addComponent(checkBoxGuardian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelParentGuardian)
                    .addComponent(txtParentGuardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory)
                    .addComponent(comboParentSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelParentGuardian2)
                    .addComponent(txtParentGuardian2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPatientStudent)
                    .addComponent(txtPatientStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRelationship)
                    .addComponent(comboRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAddress)
                    .addComponent(jLabelMandatory3)
                    .addComponent(txtBarangay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHospital)
                    .addComponent(txtHospital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHospitalAddress)
                    .addComponent(txtHospitalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMandatory5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAmount)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelReceiptNo)
                    .addComponent(receiptNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDateIssued)
                    .addComponent(labelPlaceIssued)
                    .addComponent(placeIssuedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(receiptDateIssuedPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboRelationshipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRelationshipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRelationshipActionPerformed

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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabelMandatory;
    private javax.swing.JLabel jLabelMandatory2;
    private javax.swing.JLabel jLabelMandatory3;
    private javax.swing.JLabel jLabelMandatory4;
    private javax.swing.JLabel jLabelMandatory5;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelAmount;
    private javax.swing.JLabel labelDateIssued;
    private javax.swing.JLabel labelHospital;
    private javax.swing.JLabel labelHospitalAddress;
    private javax.swing.JLabel labelParentGuardian;
    private javax.swing.JLabel labelParentGuardian2;
    private javax.swing.JLabel labelPatientStudent;
    private javax.swing.JLabel labelPlaceIssued;
    private javax.swing.JLabel labelReceiptNo;
    private javax.swing.JLabel labelRelationship;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTextField placeIssuedField;
    private javax.swing.JTextField receiptDateIssuedPicker;
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