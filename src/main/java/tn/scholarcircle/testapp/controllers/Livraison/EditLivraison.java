package tn.scholarcircle.testapp.controllers.Livraison;

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
import tn.scholarcircle.testapp.services.LivraisonService;
import tn.scholarcircle.testapp.services.LivreurService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class  EditLivraison {
    @FXML
    private TableColumn<Livraison, String> adresseCol;
    @FXML
    private TableColumn<Livraison, String> clientCol;
    @FXML
    private TableColumn<Livraison, LocalDate> dateCol;
    @FXML
    private TableColumn<Livraison, Double> fraisCol;

    @FXML
    private TableColumn<Livraison, String> livreurCol;
    @FXML
    private TableColumn<Livraison, Integer> quantiteCol;
    @FXML
    private TableColumn<Livraison, String> statusCol;

    @FXML
    private TextField adresseTFF;

    @FXML
    private TextField clientTFF;

    @FXML
    private DatePicker dateTFF;

    @FXML
    private TextField fraisTFF;

    @FXML
    private TextField livreurTFF;

    @FXML
    private TextField quantiteTFF;

    @FXML
    private TextField statusTFF;

    @FXML
    private TableView<Livraison> tableView2;

    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) tableView2.getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifierLivraison(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();
        Livraison livraison = tableView2.getSelectionModel().getSelectedItem(); // Récupère la livraison sélectionnée dans la table
        if (livraison == null) {
            // Aucune livraison sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une livraison à modifier.");
            alert.showAndWait();
            return;
        }

        // Vérifier si les champs de texte sont vides
        if (adresseTFF.getText().isEmpty() || clientTFF.getText().isEmpty() || dateTFF.getValue() == null ||
                fraisTFF.getText().isEmpty() || livreurTFF.getText().isEmpty() || quantiteTFF.getText().isEmpty() ||
                statusTFF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Vérifier si les valeurs peuvent être converties en types requis
        LocalDate dateLivraison = dateTFF.getValue();
        Double fraisLivraison;
        try {
            fraisLivraison = Double.parseDouble(fraisTFF.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez entrer un nombre valide pour les frais de livraison.");
            alert.showAndWait();
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteTFF.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez entrer un nombre entier valide pour la quantité.");
            alert.showAndWait();
            return;
        }

        // Met à jour les informations de la livraison avec les valeurs des champs de texte
        livraison.setAdresse(adresseTFF.getText());
        livraison.setClient(clientTFF.getText());
        livraison.setDate(dateTFF.getValue());
        livraison.setFrais(fraisLivraison.doubleValue());
        livraison.setLivreur(livreurTFF.getText());
        livraison.setQuantite(quantite);
        livraison.setStatus(statusTFF.getText());

        try {
            livraisonService.modifier(livraison);
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

        // Met à jour la table avec les nouvelles données
        List<Livraison> livraisons;
        try {
            livraisons = livraisonService.recuperer();
            ObservableList<Livraison> observableList = FXCollections.observableList(livraisons);
            tableView2.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    @FXML
    void initialize() {
        LivraisonService livraisonService = new LivraisonService();
        try {
            List<Livraison> livraisons = livraisonService.recuperer();
            ObservableList<Livraison> observableList = FXCollections.observableList(livraisons);
            tableView2.setItems(observableList);
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            fraisCol.setCellValueFactory(new PropertyValueFactory<>("frais"));
            livreurCol.setCellValueFactory(new PropertyValueFactory<>("livreur"));
            quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            tableView2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplir les TextField avec les données de la ligne sélectionnée
                    adresseTFF.setText(newSelection.getAdresse());
                    clientTFF.setText(newSelection.getClient());
                    dateTFF.setValue(newSelection.getDate());
                    fraisTFF.setText(String.valueOf(newSelection.getFrais()));
                    // Assurez-vous que le livreur n'est pas null avant de le définir dans le TextField

                    quantiteTFF.setText(String.valueOf(newSelection.getQuantite()));
                    statusTFF.setText(newSelection.getStatus());
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des livraisons : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void supprimerLivraison(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();
        Livraison livraison = tableView2.getSelectionModel().getSelectedItem(); // Récupère la livraison sélectionnée dans la table
        if (livraison == null) {
            // Aucune livraison sélectionnée, afficher un message d'erreur
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
                livraisonService.supprimer(livraison.getID());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Livraison supprimée avec succès !");
                alert.showAndWait();

                // Met à jour la liste observable utilisée par la table après la suppression
                List<Livraison> livraisons = livraisonService.recuperer();
                ObservableList<Livraison> observableList = FXCollections.observableList(livraisons);
                tableView2.setItems(observableList);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void pageAcceuil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/scholarcircle/testapp/FxmlLivraison/Statique.fxml"));
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
