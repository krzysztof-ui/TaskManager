package pl.coderslab.mysql.javamysql;

import java.sql.*;

public class DbUtil {

    private static final String DB_URL_PRODUCTS = "jdbc:mysql://localhost:3306/products_ex";
    private static final String DB_URL_CINEMAS = "jdbc:mysql://localhost:3306/cinemas_ex";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "coderslab";

    public static Connection connectProducts() throws SQLException {
        return DriverManager.getConnection(DB_URL_PRODUCTS, DB_USER, DB_PASSWORD);
    }

    public static Connection connectCinemas() throws SQLException {
        return DriverManager.getConnection(DB_URL_CINEMAS, DB_USER, DB_PASSWORD);
    }

    public static void insert(Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                for (String columnName : columnNames) {
                    System.out.println(resultSet.getString(columnName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





//Zadanie 4
//Uzupełnij klasę DbUtil.java o poniższy kod:
//
//public static void printData(Connection conn, String query, String... columnNames) throws SQLException {
//
//    try (PreparedStatement statement = conn.prepareStatement(query);
//         ResultSet resultSet = statement.executeQuery();) {
//        while (resultSet.next()) {
//            for (String columnName : columnNames) {
//                System.out.println(resultSet.getString(columnName));
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}