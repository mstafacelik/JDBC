import java.sql.*;

public class JDBC01_Query {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // Step 1 :Ilgili driver i yuklemeliyiz. My SQL i kullandigimizi bildiriyoruz
        // Driver i bulamama ihtimaline karsi forName methodu icin ClassNotFoundException gerekli

        Class.forName("com.mysql.cj.jdbc.Driver");


        // Step 2 : Baglantiyi olusturmak icin username ve password u girmeliyiz
        // Burada da username ve password un yanlis olma ohtomaline karsi SQLException gerekli

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "Mustafa4623.");


        // Step 3 : SQL query leri icin bir Statement objesi olusturup, javada sorgularimiz icin bir alan olusturacagiz

        Statement st = con.createStatement();


        // Step 4 : SQL query lerimizi calistirabiliriz

        ResultSet result = st.executeQuery("select*from emekciler");


        // Step 5 : Sonuclari görmek icin Set icerisindeki elemanlari while döngüsü ile yazdiriyoruz

        while (result.next()) {

            System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3)
                    + " " + result.getString(4) + " " + result.getString(5));

        }

        // Step 6 : Olusturulan baglantiyi kapatiyoruz

        con.close();
        st.close();
        result.close();

    }
}
