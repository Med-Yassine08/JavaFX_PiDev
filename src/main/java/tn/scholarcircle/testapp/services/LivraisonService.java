package tn.scholarcircle.testapp.services;

import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.models.Livreur;
import tn.scholarcircle.testapp.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements IService<Livraison> {
    private Connection connection;

    public LivraisonService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Livraison livraison) throws SQLException {
        String req = "INSERT INTO livraison (Date, Adresse, Client, Status, Quantite, Frais, Livreur) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setDate(1, Date.valueOf(livraison.getDate()));
        ps.setString(2, livraison.getAdresse());
        ps.setString(3, livraison.getClient());
        ps.setString(4, livraison.getStatus());
        ps.setInt(5, livraison.getQuantite());
        ps.setDouble(6, livraison.getFrais());
        ps.setString(7, livraison.getLivreur());
        ps.executeUpdate();
    }



    @Override
    public void modifier(Livraison livraison) throws SQLException {
        String req = "UPDATE livraison SET Date = ?, Adresse = ?, Client = ?, Status = ?, Quantite = ?, Frais = ?, Livreur = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setDate(1, Date.valueOf(livraison.getDate()));
        ps.setString(2, livraison.getAdresse());
        ps.setString(3, livraison.getClient());
        ps.setString(4, livraison.getStatus());
        ps.setInt(5, livraison.getQuantite());
        ps.setDouble(6, livraison.getFrais());
        ps.setString(7, livraison.getLivreur());
        ps.setInt(8, livraison.getID());
        ps.executeUpdate();
    }




    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM livraison WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Livraison> recuperer() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String req = "SELECT * FROM livraison";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Livraison livraison = new Livraison();
            livraison.setID(rs.getInt("ID"));
            String dateString = rs.getString("Date");
            if (dateString != null && !dateString.isEmpty()) {
                livraison.setDate(LocalDate.parse(dateString));
            } else {
                continue;
            }
            livraison.setAdresse(rs.getString("Adresse"));
            livraison.setClient(rs.getString("Client"));
            livraison.setStatus(rs.getString("Status"));
            livraison.setQuantite(rs.getInt("Quantite"));
            livraison.setFrais(rs.getDouble("Frais"));
            livraison.setLivreur(rs.getString("Livreur"));
            livraisons.add(livraison);
        }
        return livraisons;
    }


}
