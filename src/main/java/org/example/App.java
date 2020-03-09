package org.example;

import java.sql.*;

/**
 * Embedded DB
 * https://www.sqlitetutorial.net/sqlite-java/
 */
public class App 
{

    static Connection conn;

    public static void main( String[] args ) throws SQLException {
        //createNewDatabase("speicher.db");
        //createNewTable();

        // insert three new rows
        insert("Safes", 500);


        selectAll();


    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection con = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Datenbankverbindung herstellen
    public static Connection connect() {
        Connection con = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:speicher.db";
            // create a connection to the database
            con = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return con;
    }

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:speicher.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param capacity
     */
    public static void insert(String name, double capacity) throws SQLException {
        String url = "jdbc:sqlite:speicher.db";
        // create a connection to the database
        Connection con = DriverManager.getConnection(url);

        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try {
             PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, capacity);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * select all rows in the warehouses table
     */
    public static void selectAll() throws SQLException {
        String url = "jdbc:sqlite:speicher.db";
        // create a connection to the database
        Connection con = DriverManager.getConnection(url);

        String sql = "SELECT * FROM warehouses";

        try {
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                            rs.getString("name") + "\t" +
                            rs.getDouble("capacity"));
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
