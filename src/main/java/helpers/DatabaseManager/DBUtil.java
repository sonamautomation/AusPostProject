package helpers.DatabaseManager;

import java.sql.*;

public class DBUtil {
    private static final String oracledbusername = "c##scott";
    private static final String oracledbpassword = "tiger";
    private static final String oracledburl = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String mysqlusername = "ramya";
    private static final String mysqlpassword = "happy*2015";
    private static final String mysqlurl = "jdbc:mysql://localhost/sample";
    private static final String pgdbusername = "postgres";
    private static final String pgdbpassword = "pgpassword";
    private static final String pgdburl = "jdbc:postgresql://localhost/sample";

    public enum DBType{MYSQLDB,ORACLEDB,POSTGRESQL}


    public static Connection getConnection(String databaseType) throws SQLException {
        DBType dbtype = DBType.valueOf(databaseType);
        switch(dbtype){
            case MYSQLDB:
                return DriverManager.getConnection(mysqlurl,mysqlusername,mysqlpassword);
            case ORACLEDB:
                return DriverManager.getConnection(oracledburl,oracledbusername,oracledbpassword);
            case POSTGRESQL:
                return DriverManager.getConnection(pgdburl,pgdbusername,pgdbpassword);
            default:
                return null;
        }
    }
    public static void exceptionDetails(SQLException e)  {
        System.err.println("Error message is" +e.getMessage() );
        System.err.println("Error code is"  +e.getErrorCode());
        System.err.println("SQL state is" +e.getSQLState() );

    }


}

