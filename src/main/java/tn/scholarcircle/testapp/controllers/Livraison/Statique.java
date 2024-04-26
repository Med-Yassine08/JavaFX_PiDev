package tn.scholarcircle.testapp.controllers.Livraison;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.services.LivraisonService;

import java.io.IOException;
import java.sql.SQLException;


public class Statique {
    @FXML
    private BarChart<String, Number> StatLivraison;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void initialize() {
        try {
            // Récupérer les données des frais et de la quantité depuis la base de données
            LivraisonService livraisonService = new LivraisonService();
            ObservableList<Livraison> livraisons = FXCollections.observableArrayList(livraisonService.recuperer());

            // Créer les séries de données pour le graphique à barres
            XYChart.Series<String, Number> seriesFrais = new XYChart.Series<>();
            XYChart.Series<String, Number> seriesQuantite = new XYChart.Series<>();

            // Ajouter les données des frais et de la quantité aux séries de données
            for (Livraison livraison : livraisons) {
                seriesFrais.getData().add(new XYChart.Data<>(livraison.getClient(), livraison.getFrais()));
                seriesQuantite.getData().add(new XYChart.Data<>(livraison.getClient(), livraison.getQuantite()));
            }

            // Définir les noms des axes
            xAxis.setLabel("Client");
            yAxis.setLabel("Montant");

            // Ajouter les séries de données au graphique à barres
            StatLivraison.getData().addAll(seriesFrais, seriesQuantite);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur ici, par exemple en affichant une boîte de dialogue d'erreur
        }
    }
    @FXML
    void EditLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivraison/EditLivraison.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void FermePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/Home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivraison/AfficherLivraison.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivraison/AjouterLivraison.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



