package connection;

import java.sql.*;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_bd?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "*8blackops8*";

    public static java.sql.Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("A conexão falhou.", e);
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stat) {
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
        }

        closeConnection(con);
    }

}