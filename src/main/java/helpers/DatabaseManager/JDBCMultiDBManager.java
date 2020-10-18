package helpers.DatabaseManager;

import java.sql.*;


public class JDBCMultiDBManager {
    public static Connection connection;
    public static Statement stmt;
    public static ResultSet rs;
    public static PreparedStatement ps;


    // This method is to establish connnection to any of the databases upon databaseType parameter//
    public static void establishConnection(String databaseType) {
        try {
            //Establishing the Connection
            connection = DBUtil.getConnection(databaseType);
            System.out.println("Connection is establised" + connection);
        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);
        }
    }

    // This method is to pass the whole query as parameter from the test case//
    public static void insertDataintoDB(String insertquery) {
        try {

            stmt = connection.createStatement();
            stmt.executeUpdate(insertquery);
            System.out.println("Inserted Record Successfully" + insertquery);

        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);


        }
    }

    // This method uses data provider for tablename,insertColumns,inVal parameters//
    public static void insertDataintoDBNew(String tableName, String insertColumns, String inVal) throws SQLException {
        try {

            String insertSql = "INSERT INTO " + tableName + " (" + insertColumns + ") values(" + inVal + ")";
            ps = connection.prepareStatement(insertSql);

            ps.execute();
        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);
        }
    }


    // This method is to update the data in the table-accepts all parameters from test case//
    public static void updateDataintoDB(String tableName, String column, String updateValue, String refColumn, String referencevalue) {
        try {

            String updatequery = "update " + tableName + " set " + column + " = ? where " + refColumn + " = ?";
            ps = connection.prepareStatement(updatequery);
            ps.setString(1, updateValue);
            ps.setString(2, referencevalue);
            int updateResult = ps.executeUpdate();
            System.out.println("Updated number of rows" + updateResult);

        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);


        }
    }

    //This method fetches data from database table //
    public static void fetchDatafromDB(String tableName) {
        try {
            String fetchdataquery = "Select * from $tablename";
            String query = fetchdataquery.replace("$tablename", tableName);
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = stmt.executeQuery(query);

            //To get the total number of Columns//
            int columnCount = rs.getMetaData().getColumnCount();
            System.out.println("Column count is" + columnCount);

            //To get the total number of rows//
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            System.out.println("Row count is" + rowCount);

            String[][] result = new String[rowCount][columnCount];
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < columnCount; j++) {
                    result[i][j] = rs.getString(j + 1);
                    System.out.println("Result is-" + result[i][j]);
                }
                i = i + 1;
            }
        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);
        }
    }

    //This method is to check if any table exists in the database and creates one if table not exists//
    public static void checkandCreateTableinDB(String tableName) throws SQLException {
        try {

            ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null);
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    System.out.println("Table exists in the database");
                } else {
                    stmt = connection.createStatement();
                    String sql = "CREATE TABLE " + tableName + "" +
                            "(id INTEGER not NULL, " +
                            " first VARCHAR(255), " +
                            " last VARCHAR(255), " +
                            " age INTEGER, " +
                            " PRIMARY KEY ( id ))";
                    stmt.executeUpdate(sql);
                    System.out.println("Table created successfully");
                }
            }

        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);

        }
    }

    //This method is to close the database connection//
    public static void closeConnection() throws SQLException {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            DBUtil.exceptionDetails(e);
        }
    }


}



