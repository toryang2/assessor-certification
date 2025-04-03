package assessor.util;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSaveHelper {
    private static final Logger logger = Logger.getLogger(DatabaseSaveHelper.class.getName());
    private static final String TABLE_NAME = "reports";
    
    // Map Java types to SQL type constants
    private static final Map<Class<?>, Integer> SQL_TYPES = new HashMap<>();
    static {
        SQL_TYPES.put(String.class, Types.VARCHAR);
        SQL_TYPES.put(Integer.class, Types.INTEGER);
        SQL_TYPES.put(Double.class, Types.DECIMAL);
        SQL_TYPES.put(LocalDate.class, Types.DATE);
        SQL_TYPES.put(LocalTime.class, Types.TIME);
    }

    public static boolean saveReport(String reportType, Map<String, Object> data) {
        try (Connection conn = DriverManager.getConnection(
                ConfigHelper.getDbUrl(),
                ConfigHelper.getDbUser(),
                ConfigHelper.getDbPassword())) {
            
            // Add report type to data
            data.put("Type", reportType);
            
            String sql = buildInsertQuery(data.keySet());
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                setParameters(pstmt, data);
                pstmt.executeUpdate();
                return true;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to save {0} report: {1}", 
                new Object[]{reportType, e.getMessage()});
            return false;
        }
    }

    private static String buildInsertQuery(java.util.Set<String> columns) {
        StringBuilder columnsPart = new StringBuilder();
        StringBuilder valuesPart = new StringBuilder();
        
        for (String column : columns) {
            columnsPart.append(column).append(",");
            valuesPart.append("?,");
        }
        
        // Remove trailing commas
        if (columnsPart.length() > 0) {
            columnsPart.setLength(columnsPart.length() - 1);
            valuesPart.setLength(valuesPart.length() - 1);
        }
        
        return "INSERT INTO " + TABLE_NAME + " (" + columnsPart + ") " +
               "VALUES (" + valuesPart + ")";
    }

    private static void setParameters(PreparedStatement pstmt, Map<String, Object> data) 
        throws SQLException {
        
        int index = 1;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            Object value = entry.getValue();
            Class<?> valueType = value.getClass();
            
            // Handle special types
            if (value instanceof LocalDate) {
                pstmt.setDate(index, Date.valueOf((LocalDate) value));
            } else if (value instanceof LocalTime) {
                pstmt.setTime(index, Time.valueOf((LocalTime) value));
            } else {
                // Use generic type mapping
                pstmt.setObject(index, value, SQL_TYPES.get(valueType));
            }
            
            index++;
        }
    }
}
