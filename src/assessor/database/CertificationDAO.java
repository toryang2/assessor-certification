package assessor.database;

import assessor.util.ConfigHelper;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CertificationDAO {
    private static final String[] COLUMNS = {
        "id", "Patient", "Barangay", "MaritalStatus", "Type", 
        "ParentGuardian", "ParentSexIfSingle", "Relationship", 
        "Hospital", "HospitalAddress", "CertificationDate", 
        "CertificationTime", "AmountPaid", "ReceiptNo", 
        "ReceiptDateIssued", "PlaceIssued", "userInitials", "Signatory"
    };

    public List<Map<String, Object>> getCertificationRecords(String searchTerm) {
        List<Map<String, Object>> records = new ArrayList<>();
        String query = "SELECT * FROM records WHERE Patient LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(ConfigHelper.getDbUrl());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" + searchTerm + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (String col : COLUMNS) {
                        switch(col) {
                            case "CertificationDate":
                            case "ReceiptDateIssued":
                                row.put(col, rs.getDate(col));
                                break;
                            case "CertificationTime":
                                row.put(col, rs.getTime(col));
                                break;
                            case "id":
                            case "AmountPaid":
                                row.put(col, rs.getInt(col));
                                break;
                            default:
                                row.put(col, rs.getString(col));
                        }
                    }
                    records.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return records;
    }
}