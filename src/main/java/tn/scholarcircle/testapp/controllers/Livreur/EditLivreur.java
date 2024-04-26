package tn.scholarcircle.testapp.controllers.Livreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.models.Livreur;
import tn.scholarcircle.testapp.services.LivraisonService;
import tn.scholarcircle.testapp.services.LivreurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EditLivreur {

    @FXML
    private TableColumn<Livreur, String> adresseCol;

    @FXML
    private TextField adresseTF;

    @FXML
    private TableColumn<Livreur, String> emailCol;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField modeTF;

    @FXML
    private TableColumn<Livreur, String> modetransportCol;

    @FXML
    private TableColumn<Livreur, String> nomCol;

    @FXML
    private TextField nomTF;

    @FXML
    private TableColumn<Livreur, Integer> numtelCol;

    @FXML
    private TextField numtelTF;

    @FXML
    private TableColumn<Livreur, String> prenomCol;

    @FXML
    private TextField prenomTF;

    @FXML
    private TableView<Livreur> tableViewLiv2;

    @FXML
    private TableColumn<Livreur, String> zoneCol;

    @FXML
    private TextField zoneTF;

    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) tableViewLiv2.getScene().getWindow();
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
            e.printStackTrace();
        }
    }

    @FXML
    void modifierLivreur(ActionEvent event) {
        LivreurService livreurService = new LivreurService();
        Livreur livreur = (Livreur) tableViewLiv2.getSelectionModel().getSelectedItem(); // Récupère la livraison sélectionnée dans la table
        if (livreur == null) {
            // Aucune livraison sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une livraison à modifier.");
            alert.showAndWait();
            return;
        }
        // Met à jour les informations de la livraison avec les valeurs des champs de texte
        livreur.setAdresse(adresseTF.getText());
        livreur.setEmail(emailTF.getText());
        livreur.setModeTransport(modeTF.getText());
        livreur.setNom((nomTF.getText()));
        livreur.setNumTel(Integer.parseInt(numtelTF.getText()));
        livreur.setPrenom(prenomTF.getText());
        livreur.setZone(zoneTF.getText());

        try {
            livreurService.modifier(livreur);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Livraison modifiée avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        List<Livreur> livreurs = null;
        try {
            livreurs = livreurService.recuperer();
            ObservableList<Livreur> observableList = FXCollections.observableList(livreurs);
            tableViewLiv2.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void supprimerLivreur(ActionEvent event) {
        LivreurService livreurService = new LivreurService();
        Livreur livreur = tableViewLiv2.getSelectionModel().getSelectedItem(); // Récupère la livraison sélectionnée dans la table
        if (livreur == null) {
            // Aucune livreur sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une livraison à supprimer.");
            alert.showAndWait();
            return;
        }

        // Demande de confirmation de suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette livraison ?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                livreurService.supprimer(livreur.getID());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Livraison supprimée avec succès !");
                alert.showAndWait();

                // Met à jour la liste observable utilisée par la table après la suppression
                List<Livreur> livreurs = livreurService.recuperer();
                ObservableList<Livreur> observableList = FXCollections.observableList(livreurs);
                tableViewLiv2.setItems(observableList);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }
    @FXML
    void initialize() {
        LivreurService livreurService = new LivreurService();
        try {
            List<Livreur> livreurs = livreurService.recuperer();
            ObservableList<Livreur> observableList = FXCollections.observableList(livreurs);
            tableViewLiv2.setItems(observableList);
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
            modetransportCol.setCellValueFactory(new PropertyValueFactory<>("ModeTransport"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            numtelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
            zoneCol.setCellValueFactory(new PropertyValueFactory<>("Zone"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));

            tableViewLiv2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplir les TextField avec les données de la ligne sélectionnée
                    adresseTF.setText(newSelection.getAdresse());
                    emailTF.setText(newSelection.getEmail());
                    modeTF.setText(newSelection.getModeTransport());
                    nomTF.setText(newSelection.getNom());
                    numtelTF.setText(String.valueOf(newSelection.getNumTel()));
                    zoneTF.setText(newSelection.getZone());
                    prenomTF.setText(newSelection.getPrenom());
                }
            });

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des livraisons : " + e.getMessage());
            alert.showAndWait();
        }
    }

}
