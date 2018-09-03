package voucher;

import java.io.InputStream;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.util.Properties;  
import java.io.FileWriter;  
import java.io.PrintWriter;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  

class DBUtils {  
    private static Properties prop = new Properties();  
    static{  
        ClassLoader loader = DBUtils.class.getClassLoader();  
        InputStream in = loader.getResourceAsStream("config.properties");  
        try {  
            prop.load(in);  
            Class.forName(prop.getProperty("driverClassName"));  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
      
    public static Connection getConnection() throws Exception{  
        return DriverManager.getConnection(prop.getProperty("jdbc_url"),prop.getProperty("jdbc_username"),prop.getProperty("jdbc_password"));  
    }  
      
    public static void close(Connection conn){  
        if(conn != null){  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
       
}  
  
public class GenEntityTool {  
    private String[] colNames;//列名数组  
    private String[] colType;//列名类型数组  
    private int[] colSize;//列名大小数组  
    private boolean f_util = false;//是否需要导入java.util.*  
    private boolean f_sql = false;//是否需要导入java.sql.*  
      
    public GenEntityTool(){}  
    public GenEntityTool(String tabName){  
          
        Connection conn = null;  
        String sql = "select * from " + tabName;  
        try {  
            conn = DBUtils.getConnection();  
            PreparedStatement prep = conn.prepareStatement(sql);  
            ResultSetMetaData rsmd = prep.getMetaData();  
            int size = rsmd.getColumnCount();//共有多少列  
            colNames = new String[size];  
            colType = new String[size];  
            colSize = new int[size];  
            for(int i=0;i<rsmd.getColumnCount();i++){  
                colNames[i] = rsmd.getColumnName(i+1);  
                colType[i] = rsmd.getColumnTypeName(i+1);  
                if(colType[i].equalsIgnoreCase("date")){  
                    f_util = true;  
                }  
                if(colType[i].equalsIgnoreCase("text") || colType[i].equalsIgnoreCase("image")){  
                    f_sql = true;  
                }  
                colSize[i] = rsmd.getColumnDisplaySize(i+1);  
            }  
            String content = parse(tabName, colNames, colType, colSize);  
            FileWriter fw = new FileWriter("C:\\Users\\WangJing\\Desktop\\bb\\"+initCap(tabName)+".java");  
            PrintWriter pw = new PrintWriter(fw);  
            pw.println(content);  
            pw.flush();  
            pw.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
            DBUtils.close(conn);  
        }  
    }  
      
    /* 
     * 解析处理，生成java实体类主体代码 
     */  
    private String parse(String tabName,String[] colNames,String[] colType,int[] colSize){  
        StringBuffer sb = new StringBuffer();  
        if(f_util){  
            sb.append("import java.util.Date;\r\n");  
        }  
        if(f_sql){  
            sb.append("import java.sql.*;\r\n\r\n\r\n");  
        }  
        sb.append("public class "+initCap(tabName)+"{\r\n");  
        processAllAttrs(sb);  
        processAllMethod(sb);  
        sb.append("}\r\n");  
        return sb.toString();  
    }  
      
    /* 
     * 生成所有的方法 
     */  
    private void processAllMethod(StringBuffer sb){  
        for(int i=0;i<colNames.length;i++){  
            sb.append("\tpublic void set"+initCap(colNames[i])+"("+sqlType2JavaType(colType[i])+" "+colNames[i]+"){\r\n");  
            sb.append("\t\tthis."+colNames[i]+" = "+colNames[i]+";\r\n");  
            sb.append("\t}\r\n");  
              
            sb.append("\tpublic "+sqlType2JavaType(colType[i])+" get"+initCap(colNames[i])+"(){\r\n");  
            sb.append("\t\treturn "+colNames[i]+";\r\n");  
            sb.append("\t}\r\n");  
        }  
    }  
      
    /* 
     * 解析输出属性 
     *  
     * @return 
     */  
    private void processAllAttrs(StringBuffer sb){  
        for(int i=0;i<colNames.length;i++){  
            sb.append("\tprivate "+sqlType2JavaType(colType[i])+" "+colNames[i]+";\r\n");  
        }  
    }  
      
    /* 
     * 把输入字符串的首字母变成大写 
     *  
     * @param str 
     * @return 
     *  
     */  
    private String initCap(String str){  
        char[] ch = str.toCharArray();  
        if(ch[0] >= 'a' && ch[0] <= 'z'){  
            ch[0] = (char) (ch[0] - 32);  
        }  
        return new String(ch);  
    }  
      
    private String sqlType2JavaType(String sqlType) {    
        if (sqlType.equalsIgnoreCase("bit")) {  
            return "boolean";  
        } else if (sqlType.equalsIgnoreCase("tinyint")) {  
            return "byte";  
        } else if (sqlType.equalsIgnoreCase("smallint")) {  
            return "short";  
        } else if (sqlType.equalsIgnoreCase("int")) {  
            return "int";  
        } else if (sqlType.equalsIgnoreCase("bigint")) {  
            return "long";  
        } else if (sqlType.equalsIgnoreCase("float")) {  
            return "float";  
        } else if (sqlType.equalsIgnoreCase("decimal")  
                || sqlType.equalsIgnoreCase("numeric")  
                || sqlType.equalsIgnoreCase("double")  
                || sqlType.equalsIgnoreCase("real")) {  
            return "double";  
        } else if (sqlType.equalsIgnoreCase("money")  
                || sqlType.equalsIgnoreCase("smallmoney")) {  
            return "double";  
        } else if (sqlType.equalsIgnoreCase("varchar")  
                || sqlType.equalsIgnoreCase("char")  
                || sqlType.equalsIgnoreCase("nvarchar")  
                || sqlType.equalsIgnoreCase("nchar")) {  
            return "String";  
        } else if (sqlType.equalsIgnoreCase("datetime")  
                || sqlType.equalsIgnoreCase("date")) {  
            return "Date";  
        }  
  
        else if (sqlType.equalsIgnoreCase("image")) {  
            return "Blob";  
        } else if (sqlType.equalsIgnoreCase("text")) {  
            return "Clob";  
        }  
        return null;  
    }    
      
    /* 
     * 读取数据库中的表名 
     * @return表名的String数组 
     *  
     */  
    private static String[] getTabNames(){  
        Connection conn = null;  
        String sql = "show tables";  
        String[] tabNames = null;  
          
        try {  
            conn = DBUtils.getConnection();  
            PreparedStatement  prep = conn.prepareStatement(sql);  
            ResultSet rs = prep.executeQuery();  
            rs.last();  
            int size = rs.getRow();  
            tabNames = new String[size];  
            rs.beforeFirst();  
            int i=0;  
            while(rs.next() && i < size){  
                tabNames[i]=rs.getString(1);  
                i++;  
            }  
              
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
            DBUtils.close(conn);  
        }  
        return tabNames;  
    }  
      
      
    public static void main(String[] args){  
        String[] tabNames = getTabNames();  
        for(int i=0;i<tabNames.length;i++){  
            new GenEntityTool(tabNames[i]);  
        }  
    }  
}  


