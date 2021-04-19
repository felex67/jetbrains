package carsharing.stage1;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            System.out.print("Init Database ...");
            Connection conn = InitDatabase.connect(args.length > 1 ? args[1] : "");
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            InitDatabase.createTableCompany(stmt, "company");
            stmt.close();
            conn.close();
            System.out.println(" OK");
        } catch (SQLException e) {
            System.err.println("Exception: " + e.toString());
        }
    }
}
