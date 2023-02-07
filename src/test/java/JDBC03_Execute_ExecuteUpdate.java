import java.sql.*;

public class JDBC03_Execute_ExecuteUpdate {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


/*
    A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
       dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.

        1) execute() metodu -> boolean dondurur
        2) executeUpdate() metodu.-- int dondurur


    B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur ve
       - execute(), Boolean bir deger dondurur:
                                              -- > DDL (drop, alter gibi tablonun dis yapisina ait) islemlerinde false dondururken,
                                              -- > DML ( insert, delete, update gibi tablonun ic yapisina ait) islemlerinde true deger dondurur.
       - Ozellikle, hangi tip SQL ifadesine hangi methodun uygun oldugunun belli olmadigi, bilenemedigi durumlarda tercih edilmektedir.

    C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
       - Bu islemlerde islemden etkilenen satir sayisini dondurur.
       - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.
 */

        // Soru 1 : arbeiter tablosunu siliniz

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "Mustafa4623.");
        Statement st = con.createStatement();

        String dropQuery = "drop table arbeiter";

        // 1. Yol--> System.out.println(st.execute(dropQuery))

        // 2. Yol

        if (!st.execute(dropQuery)) {

            System.out.println("arbeiter tablosu silindi");

        }


        // Soru 2 : arbeiter adinda bir tablo olusturunuz id int, birim VARCHAR(10), maas int

        String createTable = "create table arbeiter (id INT, birim VARCHAR(10), maas INT)";

        if (!st.execute(createTable)) {

            System.out.println("arbeiter tablosu olusturuldu");
        }


        // Soru 3 : arbeiter tablosuna yeni bir kayit (80, 'ARGE', 4000) ekleyelim.

        String insertData = "insert arbeiter values(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));


        /* Soru 4 :     arbeiter tablosuna birden fazla yeni kayıt ekleyelim.

                        INSERT INTO arbeiter VALUES (70, 'HR', 5000),
                        INSERT INTO arbeiter VALUES (60, 'LAB', 3000),
                        INSERT INTO arbeiter VALUES (50, 'ARGE', 4000)
        */

        String[] yeniKayitlar = {"INSERT INTO arbeiter VALUES (70, 'HR', 5000)",
                "INSERT INTO arbeiter VALUES (60, 'LAB', 3000)",
                "INSERT INTO arbeiter VALUES (50, 'ARGE', 4000)"};

        int count = 0;
        for (String each : yeniKayitlar) {

            count += st.executeUpdate(each);


        }
        System.out.println(count + " satir eklendi!");


        // Ayri ayri sorgular ile veritabanina tekrar tekrar ulasmak islemlerin yavas yapilmasina yol acar.
        // 10000 tane veri kaydi yapildigi dusunuldugunde bu kotu bir yaklasimdir.

        // Soru 5 : Arbeiter tablosunu goruntuleyiniz

        System.out.println("=============  Arbeiter Table  ====================");

        String selectTablo = "select* from arbeiter";

        ResultSet set = st.executeQuery(selectTablo);

        while (set.next()) {

            System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3));


        }

    }
}
