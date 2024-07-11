package titov.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@UtilityClass
@Slf4j
public class DataBaseConnection {
    private final String username = PropertiesLoader.getValue("db.username");
    private final String password = PropertiesLoader.getValue("db.password");
    private final String url = PropertiesLoader.getValue("db.url");

    public Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                log.debug("Connected to the database!");
            } else {
                log.debug("Failed to make connection!");
            }
            return conn;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTables() throws FileNotFoundException {
        InputStream is = new FileInputStream("src/main/resources/schema.sql"); // Открываем файл.
        try (Scanner scanner = new Scanner(is).useDelimiter(";")) {
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {
                while(scanner.hasNext()) {
                    String sqlStmt = scanner.next() + ";";
                    if (!sqlStmt.trim().isEmpty()) {
                        stmt.executeUpdate(sqlStmt);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}