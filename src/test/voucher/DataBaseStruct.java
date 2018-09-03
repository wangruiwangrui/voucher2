package voucher;

import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
  
public class DataBaseStruct {  
  
//  private final String DRIVER =  "oracle.jdbc.driver.OracleDriver";  
//  private final String URI = "jdbc:oracle:thin:@192.168.1.6:1521:orcl";  
//  private final String NAME = "xcww";  
//  private final String PASSWORD = "1";  
    private Connection conn = null;  
    private PreparedStatement pstmt = null;  
    private ResultSet rs = null;  
    private ResultSetMetaData rsm = null;  
  
    public static void main(String[] args) {  
        DataBaseStruct dbs = new DataBaseStruct();  
        dbs.getTableStruct("t_user");  
    }  
  
    public void getTableStruct(String tableName) {  
        try {  
//           Class.forName(DRIVER);  
//           conn = DriverManager.getConnection(URI,NAME,PASSWORD);  
//            conn = Conn.conn();  
            pstmt = (PreparedStatement) conn.prepareStatement("select * from " + tableName);  
            pstmt.execute();  
            rsm = (ResultSetMetaData) pstmt.getMetaData();  
  
            StringBuilder insert = new StringBuilder();  
            insert.append("INSERT INTO " + tableName + "(");  
            for (int i = 1; i < rsm.getColumnCount() + 1; i++) {  
                insert.append("'").append(rsm.getColumnName(i)).append("',");  
            }  
            insert.append(") values (");  
            for (int i = 1; i < rsm.getColumnCount() + 1; i++) {  
                insert.append("'").append(rsm.getColumnName(i)).append("',");  
            }  
            insert.append(");");  
            System.out.println(insert.toString().replace(",)", ")").toUpperCase());  
  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                pstmt.close();  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  