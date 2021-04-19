package carsharing.stage1;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class InitDatabase {

    /** Collection of queries */
    private static class Queries {
        final private static String CREATE_COMPANY =
            "CREATE TABLE %s (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(%d) NOT NULL, " +
                "UNIQUE(NAME)" +
            ");";
        final private static String DROP_IF_EXISTS =
            "DROP TABLE IF EXISTS %s;";
        /*
        final private static String CREATE_DATABASE =
            "CREATE DATABASE %s;";*/

        /**
         * Builds query for creating table 'company':
         *  [nameLength] - maximal company name length.
         *  [tableName] - table name
         * 
         * @param int
         * @param String
         * 
         * @return String - generated query
        */
        static String createTableCompany(int nameLength, String tableName) {
            return String.format(CREATE_COMPANY, tableName, nameLength);
        }
        /**
         * Builds query: 'Drop if exists [tableName]'
         * @param String
        */
        static String dropTableIfExists(String tableName) {
            return String.format(DROP_IF_EXISTS, tableName);
        }
        /**
         * Builds query: 'create database [databaseName]'
         * @param String
         * 
         * @return String - generated query
        
        static String createDatabase(String databaseName) {
            return String.format(CREATE_DATABASE, databaseName);
        }*/
    }

    /** Database and connection config */
    private static class DbConfig {
        /** database URL */
        final static String dbLocation = "./carsharing/db";
        final static String dbUrl = "jdbc:h2:" + dbLocation + "/%s";
        /** default database name */
        final static String dbName = "carsharing";
        /** database user */
        final static String dbUser = "sa";
        /** database password */
        final static String dbPasswd = "";
    
        /** table 'company' real name*/
        final static String companyTableName = "company";
        /** Maximal length of company.name */
        final static int companyNameMaxLength = 62;
    }

    /**
     * Creaetes table 'company'
     * @throws SQLException
     * 
     * @param Statement
     * @param String
    */
    public static boolean createTableCompany(Statement stmt, String tableName) throws SQLException {
        boolean result = false;
        result = stmt.execute(Queries.dropTableIfExists(!tableName.isBlank() ? tableName : DbConfig.companyTableName));
        result = stmt.execute(Queries.createTableCompany(DbConfig.companyNameMaxLength, tableName));
        return result;
    }
    /**
     * Drops a table if it exists
     * @throws SQLExeption
     * 
     * @param Statement stmt
     * @param String tableName
    */
    public static boolean dropTableIfExists(Statement stmt, String tableName) throws SQLException {
        return stmt.execute(Queries.dropTableIfExists(tableName));
    }
    /**
     * Return new connection
     * @throws SQLException
     * 
     * @return Connection
    */
    public static Connection connect(String databaseName) throws SQLException {
        File dir = new File(DbConfig.dbLocation);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return DriverManager.getConnection(
            String.format(DbConfig.dbUrl, !databaseName.isBlank() ? databaseName : DbConfig.dbName),
            DbConfig.dbUser,
            DbConfig.dbPasswd);
    }
}
