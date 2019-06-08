package connection;

import java.sql.*;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_bd";
    private static final String USER = "root";
    private static final String PASSWORD = "*8blackops8*";

    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("A conexão falhou.", e);
        }
    }

    public static void closeConnection (Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
        }
    }

    public static void closeConnection (Connection con, PreparedStatement stat){
        if(stat != null){
            try {
                stat.close();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
        }

        closeConnection(con);
    }

    public static void closeConnection (Connection con, PreparedStatement stat, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
        }

        closeConnection(con, stat);
    }
}
