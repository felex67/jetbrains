package carsharing.stage1;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String dbname = "carsharing";
            if (args.length > 1 && 0 == args[1].compareTo("-databaseFileName")) {
                dbname = args[2];
            }
            //System.out.print("Init Database '" + dbname + "' ... ");
            Connection conn = InitDatabase.connect(dbname);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            InitDatabase.createTableCompany(stmt, "company");
            stmt.close();
            conn.close();
            //System.out.println("OK");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.toString());
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
        }
    }
}
