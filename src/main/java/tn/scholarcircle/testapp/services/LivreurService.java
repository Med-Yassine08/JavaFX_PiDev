package tn.scholarcircle.testapp.services;

import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.models.Livreur;
import tn.scholarcircle.testapp.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreurService implements IService<Livreur>{
    private Connection connection;

    public LivreurService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Livreur livreur) throws SQLException {
        String req = "INSERT INTO livreur (Nom,Prenom,Adresse,Email,NumTel,Zone,ModeTransport ) VALUES ('"+livreur.getNom()+"','"+livreur.getPrenom()+"','"+livreur.getAdresse()+"','"+livreur.getEmail()+"','"+livreur.getNumTel()+"','"+livreur.getZone()+"','"+livreur.getModeTransport()+"')";
        Statement st =connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Livreur livreur) throws SQLException {
        String req = "UPDATE livreur SET Nom = ?,Prenom = ?,Adresse = ?,Email = ?,NumTel = ?,Zone = ?,ModeTransport = ? WHERE ID = ? ";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, livreur.getNom());
        ps.setString(2, livreur.getPrenom());
        ps.setString(3, livreur.getAdresse());
        ps.setString(4, livreur.getEmail());
        ps.setInt(5, livreur.getNumTel());
        ps.setString(6, livreur.getZone());
        ps.setString(7, livreur.getModeTransport());
        ps.setInt(8, livreur.getID());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM livreur WHERE ID = ?";
        PreparedStatement ps =connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Livreur> recuperer() throws SQLException {
        List<Livreur> livreurs = new ArrayList<>();
        String req = "SELECT * FROM livreur";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Livreur livreur = new Livreur();
            livreur.setID(rs.getInt("ID"));
            livreur.setNom(rs.getString("Nom"));
            livreur.setPrenom(rs.getString("Prenom"));
            livreur.setAdresse(rs.getString("Adresse"));
            livreur.setEmail(rs.getString("Email"));
            livreur.setNumTel(rs.getInt("NumTel"));
            livreur.setZone(rs.getString("Zone"));
            livreur.setModeTransport(rs.getString("ModeTransport"));
            livreurs.add(livreur);
        }
        return livreurs;
    }



}
