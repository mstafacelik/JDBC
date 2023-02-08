import java.sql.*;

public class JDBC02_Query {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "Mustafa4623.");
        Statement st = con.createStatement();


        // ORNEK 1: Id'si 1'den buyuk firmalarin namen ve kontakt bilgilerini namen ters sirali yazdirin.

        /*
        CREATE TABLE unternehmen
        (
        id INT,
        namen VARCHAR(20),
        kontakt VARCHAR(20),
        CONSTRAINT firmalar_pk PRIMARY KEY (id, isim)
        );

        INSERT INTO firmalar VALUES
        (1, 'ACB', 'Ali Can'),
        (2, 'RDB', 'Veli Gul'),
        (3, 'KMN', 'Ayse Gulmez');
         */


        String selectQuery = "select namen, kontakt " +   // " tirnaktan önceki bosuga dikkat ! Aksi halde calismaz. Tipki MySQL deki gibi olmali yazim
                "from unternehmen " +
                "where id >1 " +
                "order by namen desc";

        // Alternatif yazim

        String selectQuery2 = "select namen, kontakt from unternehmen where id >1 order by namen desc";

        ResultSet set = st.executeQuery(selectQuery);

        while (set.next()) {

            System.out.println(set.getString("namen") + " " + set.getString("kontakt"));


        }

        System.out.println("=================================================");


        //  ORNEK2: Iletisim isminde 'li' iceren firmalarin id'lerini ve namen'lerini id sirali yazdirin.

        String sorgu = "select id, namen from unternehmen where kontakt like '%li%' order by id";

        ResultSet selectSorgu = st.executeQuery(sorgu);


        // 1. yol

        //     while (selectSorgu.next()) {
        //     System.out.println(selectSorgu.getInt("id") + " " + selectSorgu.getString("namen"));
        //
        // }


        // 2.Yol

        // NOT1 : Sorgulama icin get ile istenirse sütun (field) ismini yazabilecegimiz gibi sutun index
        // (field olusturulma sirasina gore) yazilabilir.
        // NOT2 : Sorgumuzda SELECT'ten sonra sadece belli fieldlari dondurmesini istiyorsak
        // get ile cagirdigimiz field indexleri sorguda belirttigimiz sirayla ifade etmemiz gerekiyor


        while (selectSorgu.next()) {
            System.out.println(selectSorgu.getInt(1) + " " + selectSorgu.getString(2));

        }

        con.close();
        st.close();
        set.close();
        selectSorgu.close();

    }


}

