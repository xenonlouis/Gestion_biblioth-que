package com.library.util;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASS = "sbaxi";
    private static boolean tablesInitialized = false;

    // Map Java types to SQL types
    private static final Map<Class<?>, String> TYPE_MAPPING = new HashMap<>();
    static {
        TYPE_MAPPING.put(String.class, "VARCHAR(255)");
        TYPE_MAPPING.put(Integer.class, "INT");
        TYPE_MAPPING.put(int.class, "INT");
        TYPE_MAPPING.put(Date.class, "DATE");
        TYPE_MAPPING.put(Long.class, "BIGINT");
        TYPE_MAPPING.put(long.class, "BIGINT");
        TYPE_MAPPING.put(Double.class, "DOUBLE");
        TYPE_MAPPING.put(double.class, "DOUBLE");
        TYPE_MAPPING.put(Boolean.class, "BOOLEAN");
        TYPE_MAPPING.put(boolean.class, "BOOLEAN");
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            if (!tablesInitialized) {
                checkAndInitializeTables(connection);
                tablesInitialized = true;
            }
            
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found: " + e.getMessage());
            throw new SQLException("Error connecting to database", e);
        }
    }

    private static void checkAndInitializeTables(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        Class<?>[] modelClasses = {Book.class, Student.class, Borrow.class};
        
        for (Class<?> modelClass : modelClasses) {
            String tableName = modelClass.getSimpleName().toLowerCase();
            ResultSet tables = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
            
            if (!tables.next()) {
                // Table doesn't exist, create it
                System.out.println("Table " + tableName + " does not exist. Creating...");
                createTableFromClass(connection, modelClass);
            } else {
                // Table exists, check for missing columns
                System.out.println("Table " + tableName + " exists. Checking columns...");
                updateTableColumns(connection, modelClass);
            }
            tables.close();
        }
    }

    private static void updateTableColumns(Connection connection, Class<?> clazz) throws SQLException {
        String tableName = clazz.getSimpleName().toLowerCase();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        
        Map<String, Boolean> existingColumns = new HashMap<>();
        while (columns.next()) {
            existingColumns.put(columns.getString("COLUMN_NAME").toLowerCase(), true);
        }
        columns.close();

        // Check each field in the class
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName().toLowerCase();
            if (!existingColumns.containsKey(fieldName)) {
                // Add missing column
                String sqlType = TYPE_MAPPING.get(field.getType());
                if (sqlType == null) {
                    sqlType = "VARCHAR(255)";
                }
                
                String alterSQL = String.format("ALTER TABLE %s ADD COLUMN %s %s", 
                    tableName, fieldName, sqlType);
                
                if (!field.getType().isPrimitive() && !fieldName.equals("returndate")) {
                    alterSQL += " NOT NULL";
                }
                
                try (Statement statement = connection.createStatement()) {
                    System.out.println("Adding missing column: " + alterSQL);
                    statement.execute(alterSQL);
                }
            }
        }
    }

    private static void createTableFromClass(Connection connection, Class<?> clazz) throws SQLException {
        StringBuilder createTableSQL = new StringBuilder();
        String tableName = clazz.getSimpleName().toLowerCase();
        
        createTableSQL.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");
        
        Field[] fields = clazz.getDeclaredFields();
        boolean firstField = true;
        
        for (Field field : fields) {
            if (!firstField) {
                createTableSQL.append(", ");
            }
            
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            
            createTableSQL.append(fieldName).append(" ");
            
            if (fieldName.equals("id")) {
                createTableSQL.append("INT AUTO_INCREMENT PRIMARY KEY");
            } else {
                String sqlType = TYPE_MAPPING.get(fieldType);
                if (sqlType == null) {
                    sqlType = "VARCHAR(255)"; // default type for unknown Java types
                }
                createTableSQL.append(sqlType);
                
                // Add NOT NULL constraint for non-nullable fields
                if (!field.getType().isPrimitive() && !fieldName.equals("returnDate")) {
                    createTableSQL.append(" NOT NULL");
                }
            }
            
            firstField = false;
        }
        
        createTableSQL.append(")");
        
        try (Statement statement = connection.createStatement()) {
            System.out.println("Creating table with SQL: " + createTableSQL);
            statement.execute(createTableSQL.toString());
        } catch (SQLException e) {
            System.err.println("Error creating table " + tableName + ": " + e.getMessage());
            throw e;
        }
    }
}