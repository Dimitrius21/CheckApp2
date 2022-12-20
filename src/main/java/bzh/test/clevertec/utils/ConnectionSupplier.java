package bzh.test.clevertec.utils;

import bzh.test.clevertec.CheckRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionSupplier {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionSupplier.class);
    private static final String MAIN_PROPERTIES_FILE = "application.properties";
    private static Connection con;

    private static void createConnection() throws SQLException {
        String url = CheckRunner.getAppProperty("jdbc.url");
        String username = CheckRunner.getAppProperty("jdbc.username");
        String password = CheckRunner.getAppProperty("jdbc.password");
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            logger.error("Error of Connection creating");
            throw new SQLException("Error of Connection creating", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null) {
            createConnection();
        }
        return con;
    }

    public static void close() {
        try {
            con.close();
        } catch (SQLException e) {
            logger.error("Error of Connection close");
            throw new RuntimeException(e);
        }
    }
}
