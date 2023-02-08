import java.sql.*;

public class JDBC03_Execute_ExecuteUpdate {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
 	A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
 	   dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.

 	    1) execute() metodu - boolean dondurur.
 	    2) executeUpdate() metodu - int deger dondurur.

 	B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur.
 	   - execute(), Boolean bir deger dondurur. DDL islemlerinde false dondururken,
 	     DML islemlerinde true deger dondurur.
 	   - Ozellikle, hangi tip SQL ifadesine hangi metodun uygun oldugunun bilinemedigi
 	     durumlarda tercih edilmektedir.

 	C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
 	   - bu islemlerde islemden etkilenen satir sayisini dondurur.
 	   - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.
    */

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "Mustafa4623.");
        Statement st = con.createStatement();

        /*======================================================================
		  ORNEK1: arbeiter tablosunu siliniz.
 	    ========================================================================*/

        String dropQuery = "DROP TABLE arbeiter";

        // System.out.println(st.execute(dropQuery));

        if (!st.execute(dropQuery)) {
            System.out.println("arbeiter tablosu silindi!");
        }

        /*=======================================================================
          ORNEK2: Arbeiter adinda bir tablo olusturunuz id int,
          birim VARCHAR(10), maas int
	    ========================================================================*/

        String createTable = "CREATE TABLE arbeiter" +
                "(id INT, " +
                "birim VARCHAR(10), " +
                "maas INT)";

        if (!st.execute(createTable)) {
            System.out.println("arbeiter tablosu olusturuldu!");
        }

        /*=======================================================================
		  ORNEK3: arbeiter tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/

        String insertData = "INSERT INTO arbeiter VALUES(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));

        /*=======================================================================
	      ORNEK4: arbeiter tablosuna birden fazla yeni kayıt ekleyelim.

	        INSERT INTO arbeiter VALUES(70, 'HR', 5000)
            INSERT INTO arbeiter VALUES(60, 'LAB', 3000)
            INSERT INTO arbeiter VALUES(50, 'ARGE', 4000)
	     ========================================================================*/
        System.out.println("=============== 1. Yontem ==============");

        String[] queries = {"INSERT INTO arbeiter VALUES(70, 'HR', 5000)",
                "INSERT INTO arbeiter VALUES(60, 'LAB', 3000)",
                "INSERT INTO arbeiter VALUES(50, 'ARGE', 4000)"};

        int count = 0;
        for (String each : queries) {
            count += st.executeUpdate(each);
        }
        System.out.println(count + " satir eklendi!");

        // Ayri ayri sorgular ile veritabanina tekrar tekrar ulasmak islemlerin
        // yavas yapilmasina yol acar. 10000 tane veri kaydi yapildigi dusunuldugunde bu kotu bir yaklasimdir.

        System.out.println("=============== 2. Yontem ==============");

        // 2.YONTEM (addBatch ve executeBatch() metotlari ile)
        // ----------------------------------------------------
        // addBatch metodu ile SQL ifadeleri gruplandirilabilir ve executeBatch()
        // metodu ile veritabanina bir kere gonderilebilir.
        // executeBatch() metodu bir int [] dizi dondurur. Bu dizi her bir ifade sonucunda
        // etkilenen satir sayisini gosterir.

        String[] queries2 = {"INSERT INTO arbeiter VALUES(10, 'TEKNIK', 3000)",
                "INSERT INTO arbeiter VALUES(20, 'KANTIN', 2000)",
                "INSERT INTO arbeiter VALUES(30, 'ARGE', 5000)"};

        for (String each : queries2) { // Bu dongude her bir SQL komutunu torbaya atiyor
            st.addBatch(each);
        }

        st.executeBatch(); // Burada da tek seferde tum torbayi goturup Database'e isliyor

        System.out.println("Satirlar eklendi");

        /*=======================================================================
	      ORNEK5: arbeiter tablosuna goruntuleyin.
	     ========================================================================*/

        System.out.println("================ Arbeiter Tablosu ================");

        String selectQuery = "SELECT * FROM arbeiter";

        ResultSet arbeiterTablosu = st.executeQuery(selectQuery);

        while (arbeiterTablosu.next()) {
            System.out.println(arbeiterTablosu.getInt(1) + " " +
                    arbeiterTablosu.getString(2) + " " +
                    arbeiterTablosu.getInt(3));
        }

        /*=======================================================================
		  ORNEK 6: Arbeiter tablosundaki maasi 5000'den az olan arbeiterin maasina
		   %10 zam yapiniz.
		========================================================================*/

        String updateQuery = "UPDATE arbeiter SET maas=maas*1.1 WHERE maas<5000";

        int satir = st.executeUpdate(updateQuery);

        System.out.println(satir + " satir guncellendi!");

        /*=======================================================================
	      ORNEK7: arbeiter tablosunun son halini goruntuleyin.
	     ========================================================================*/

        System.out.println("================ Arbeiter Tablosu Maas Zamlari ================");

        ResultSet arbeiterTablosu2 = st.executeQuery(selectQuery);

        while (arbeiterTablosu2.next()) {
            System.out.println(arbeiterTablosu2.getInt(1) + " " +
                    arbeiterTablosu2.getString(2) + " " +
                    arbeiterTablosu2.getInt(3));
        }

       /*=======================================================================
        ORNEK8: Isciler tablosundan birimi 'ARGE' olan iscileri siliniz.
        ========================================================================*/

        String delete= "delete from arbeiter where birim='ARGE'";


        int deletedRow=st.executeUpdate(delete);
        System.out.println(deletedRow + " adet satir silindi");


        /*=======================================================================
	      ORNEK 9: Arbeiter tablosunun son halini goruntuleyin.
	     ========================================================================*/

        System.out.println("================ Arbeiter Tablosu Son Durum ================");

        ResultSet arbeiterTablosu3 = st.executeQuery(selectQuery);

        while (arbeiterTablosu.next()) {
            System.out.println(arbeiterTablosu3.getInt(1) + " " +
                               arbeiterTablosu3.getString(2) + " " +
                               arbeiterTablosu3.getInt(3));
        }


    }
}
