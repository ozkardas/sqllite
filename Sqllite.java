
package sqllite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
 
 
public class Sqllite{
     
    static int sec=0;
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/test.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("Sürücü ismi " + meta.getDriverName());
                System.out.println("Yeni Bir Veritabanı Oluşturuldu");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS kisibilgi (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	adi text NOT NULL,\n"
                + "	soyadi text NOT NULL,\n"
                + "	tc text NOT NULL,\n"
                + "	para real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
        
    public static void update(int id, String adi, String soyadi,String tc,double para) {
        String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "UPDATE kisibilgi SET adi = ? , "
                + "soyadi = ? ,"
                + "tc = ? ,"
                + "para = ? "
                + "WHERE id = ?";
 
        try (Connection conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(5, id);
            pstmt.setString(1, adi);
            pstmt.setString(2, soyadi);
            pstmt.setString(3, tc);
            pstmt.setDouble(4, para);
            
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     
     public static void delete(int id) {
         String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "DELETE FROM kisibilgi WHERE id = ?";
 
        try (Connection conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public static void insert(String adi,String soyadi, String tc,double para) {
          
         String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "INSERT INTO kisibilgi(adi,soyadi,tc,para) VALUES(?,?,?,?)";
 
        try (Connection conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, adi);
            pstmt.setString(2, soyadi);
            pstmt.setString(3, tc);
            pstmt.setDouble(4, para);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
      public static void selectAll(){
          String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "SELECT id, adi,soyadi,tc, para FROM kisibilgi";
        
        try (Connection conn = DriverManager.getConnection(url);
             var stmt  = conn.createStatement();
             var rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println("------------------------------------------------------");
                System.out.println(rs.getInt("id") + "\t" + 
                                   rs.getString("adi") + "\t" +
                                   rs.getString("soyadi") + "\t" +
                                   rs.getString("tc")+ "\t" +
                                   rs.getDouble("para"));
                
            }   System.out.println("------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
      public static void paracok(double para){
          String url = "jdbc:sqlite:C:/sqlite/db/test.db";
               String sql = "SELECT id, adi, soyadi,tc,para "
                          + "FROM kisibilgi WHERE para > ?";
        
        try (Connection conn = DriverManager.getConnection(url);
             var pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setDouble(1,para);
            //
            var rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
     System.out.println(rs.getInt("id") +  "\t" +rs.getString("adi") + "\t" +rs.getString("soyadi")+ "\t" + 
             rs.getString("tc") + "\t" +rs.getDouble("para"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 
      
    public static void menu(){
        
     Scanner klavye= new Scanner(System.in);
              
        System.out.println("1- Kayıt");
        System.out.println("2- Güncelle");
        System.out.println("3- Listele");
        System.out.println("4- Sil");
        System.out.println("5- Çıkış");
        System.out.print("İşlemi Seçiniz..: "); int c= klavye.nextInt();
        
        switch(c)
        {
            case 1:  System.out.print("Ad Gir :");String ad= klavye.next();
                     System.out.print("Soyad Gir :");String soyad= klavye.next();
                     System.out.print("Tc Gir :");String tc= klavye.next();
                     System.out.print("Para Gir :");int para= klavye.nextInt();
        
                     insert(ad,soyad,tc, para);  
                     break;
            case 2:  System.out.print("İd Gir :");int id1= klavye.nextInt();
                     System.out.print("Ad Gir :");String ad1= klavye.next();
                     System.out.print("Soyad Gir :");String soyad1= klavye.next();
                     System.out.print("Tc Gir :");String tc1= klavye.next();
                     System.out.print("Para Gir :");int para1= klavye.nextInt();
        
                     update(id1, ad1,soyad1,tc1, para1);  
                     break;
            case 3:  selectAll();  
                     break;
            case 4:  
                     System.out.print("İd Gir :");  
                     delete(klavye.nextInt());  
                     break;
            case 5:  System.out.print("Çıkıldı");sec=1; 
                     break;                    
        }   
        
        
        
    }  
      
    public static void main(String[] args) {
        connect();
        createNewDatabase("test.db");
        createNewTable();

        do {
            menu(); 
            
        }
        while (sec < 1);
     
    }  
       // paracok(4000);
}
