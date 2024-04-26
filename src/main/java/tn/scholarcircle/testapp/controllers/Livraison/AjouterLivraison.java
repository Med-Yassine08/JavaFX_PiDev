package tn.scholarcircle.testapp.controllers.Livraison;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.services.LivraisonService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterLivraison {

    @FXML
    private DatePicker dateTF;

    @FXML
    private TextField adresseTF;

    @FXML
    private TextField clientTF;

    @FXML
    private TextField fraisTF;

    @FXML
    private TextField livreurTF;

    @FXML
    private TextField quantiteTF;

    @FXML
    private ChoiceBox<String> statusTF;


    @FXML
    void ajouterlivraison(ActionEvent event) {
        // Vérifier si les champs obligatoires sont vides
        if (adresseTF.getText().isEmpty() || clientTF.getText().isEmpty() || fraisTF.getText().isEmpty() || quantiteTF.getText().isEmpty() || statusTF.getValue() == null ||
                livreurTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Valider les entrées numériques pour frais et quantité
        double frais;
        int quantite;
        try {
            frais = Double.parseDouble(fraisTF.getText());
            quantite = Integer.parseInt(quantiteTF.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir des valeurs numériques pour les frais et la quantité.");
            alert.showAndWait();
            return;
        }

        // Ajouter la livraison si la validation réussit
        LivraisonService livraisonService = new LivraisonService();
        Livraison livraison = new Livraison();
        livraison.setAdresse(adresseTF.getText());
        livraison.setClient(clientTF.getText());
        livraison.setFrais(frais);
        livraison.setQuantite(quantite);
        livraison.setStatus(statusTF.getValue());
        livraison.setLivreur(livreurTF.getText());

        // Récupérer la date actuelle
        LocalDate currentDate = LocalDate.now();
        livraison.setDate(currentDate); // Supposons que votre modèle Livraison ait un champ 'date'

        try {
            livraisonService.ajouter(livraison);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Livraison créée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la création de la livraison : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void acceuilPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivraison/Statique.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void cettedate(ActionEvent event) {
        // Définir la date d'aujourd'hui comme valeur du DatePicker
        dateTF.setValue(LocalDate.now());
    }

    @FXML
    private void initialize() {
        // Remplir la ChoiceBox avec les options de statut
        statusTF.getItems().addAll("En cours", "En attente", "Livré");

    }

}




