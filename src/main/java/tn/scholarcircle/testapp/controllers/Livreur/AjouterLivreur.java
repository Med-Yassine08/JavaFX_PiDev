package tn.scholarcircle.testapp.controllers.Livreur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.scholarcircle.testapp.models.Livreur;
import tn.scholarcircle.testapp.services.LivreurService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterLivreur {

    @FXML
    private TextField adresseTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField modeTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField numtelTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField zoneTF;

    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void acceuilPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivreur/LivreurPage.fxml"));
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
    void ajouterlivraison(ActionEvent event) {
        // Vérifier si les champs obligatoires sont vides
        if (adresseTF.getText().isEmpty() || emailTF.getText().isEmpty() || modeTF.getText().isEmpty() ||
                nomTF.getText().isEmpty() || numtelTF.getText().isEmpty() || prenomTF.getText().isEmpty() ||
                zoneTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Valider l'email et le numéro de téléphone
        String emailPattern = "^[a-zA-Z0-9_+&*-]+@(SHCircle\\.com|gmail\\.com|outlook\\.com|hotmail\\.com)$";
        String phonePattern = "^[0-9]{8}$";

        if (!emailTF.getText().matches(emailPattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse email avec une extension valide.");
            alert.showAndWait();
            return;
        }

        if (!numtelTF.getText().matches(phonePattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres).");
            alert.showAndWait();
            return;
        }

        // Ajouter la livraison si la validation réussit
        LivreurService livreurService = new LivreurService();
        Livreur livreur = new Livreur();
        livreur.setAdresse(adresseTF.getText());
        livreur.setEmail(emailTF.getText());
        livreur.setModeTransport(modeTF.getText());
        livreur.setNom(nomTF.getText());
        livreur.setNumTel(Integer.valueOf(numtelTF.getText()));
        livreur.setPrenom(prenomTF.getText());
        livreur.setZone(zoneTF.getText());

        try {
            livreurService.ajouter(livreur);
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
    void cettedate(ActionEvent event) {

    }
}
