import java.sql.*;

public class TRacking_employes_work {

    private static final String URL = "jdbc:mysql://localhost:3306/tracking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main (String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver introuvable. Vérifier la présence du MySQL JDBC driver JAR");
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement()) {
                System.out.println("Connexion établie avec succès");

                    stmt.executeUpdate("DROP TABLE IF EXISTS TRA_DATA");
                    stmt.executeUpdate("CREATE TABLE TRA_DATA ("
                        + "Developpeur VARCHAR(50) NOT NULL, "
                        + "Jour VARCHAR(50) NOT NULL, "
                        + "NbScripts INT NOT NULL"
                        + ")"
                    );
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Amine', 'Lundi', 5)");
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Othman', 'Mardi', 2)");
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Ilyas', 'Lundi', 9)");
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Othman', 'Mercredi', 3)");
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Saad', 'Mardi', 4)");
                    stmt.executeUpdate("INSERT INTO TRA_DATA VALUES ('Amine', 'Mercredi', 1)");

                    System.out.println("Table créée et donnés insérées");

                    System.out.println(" --------Nb de scripts max par jour----------");
                    try ( ResultSet rs = stmt.executeQuery("SELECT Developpeur, Jour, MAX(NbScripts) AS MaxScripts "
                            + "FROM TRA_DATA GROUP BY Jour"
                    ) ) {
                        while (rs.next()) {
                            String jour = rs.getString("Jour");
                            String dev = rs.getString("Developpeur");
                            int max = rs.getInt("MaxScripts");
                            System.out.println(jour + "|" + dev + "|" + max );
                        }
                    }

                    System.out.println("--------Classements des developpeurs---------");
                    try ( ResultSet rs = stmt.executeQuery("SELECT Developpeur, SUM(NbScripts) As TotalScripts "
                            + "FROM TRA_DATA GROUP BY Developpeur ORDER BY TotalScripts DESC"
                    )) {
                        while (rs.next()) {
                            String dev = rs.getString("Developpeur");
                            int total = rs.getInt("TotalScripts");
                            System.out.println(dev + ":" + total);
                        }
                    }

                    System.out.println("--------Total de la semaine--------");
                    try ( ResultSet rs = stmt.executeQuery("SELECT SUM(NbScripts) As TotalSemaine FROM TRA_DATA "
                    )) {
                        if (rs.next()) {
                            int total = rs.getInt("TotalSemaine");
                            System.out.println("Total de la semaine est : " + total);
                        }
                    }

                    System.out.println("--------Total de script pour Amine--------");
                    String DevCherche = "Amine";

                    String sql = "SELECT SUM(NbScripts) As TotalDev FROM TRA_DATA WHERE Developpeur = ?";
                    try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, DevCherche);

                    try ( ResultSet rs = ps.executeQuery())  {
                        if (rs.next()) {
                            int total = rs.getInt("TotalDev");
                            System.out.println("Le nombre de script total pour " + DevCherche + " est : " + total);
                        }
                    }
                    }

            }catch (SQLException e) {
                System.out.println("Ton SQL est incorrect");
        }

    }
}
