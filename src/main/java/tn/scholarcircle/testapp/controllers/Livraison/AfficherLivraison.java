package tn.scholarcircle.testapp.controllers.Livraison;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.models.Livreur;
import tn.scholarcircle.testapp.services.LivraisonService;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AfficherLivraison {
    @FXML
    private TextField FilterField;

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
    private TableView<Livraison> tableView;

    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
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

    @FXML
    void pdfLivraison(ActionEvent event) {
        Livraison livraisonSelectionne = tableView.getSelectionModel().getSelectedItem();

        if (livraisonSelectionne != null) {
            // Générer le PDF pour le Livreur sélectionné
            generateLivraisonPDF(livraisonSelectionne);

            // Afficher une boîte de dialogue pour informer l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF généré");
            alert.setContentText("Le document PDF pour le livreur sélectionné a été généré avec succès !");
            alert.showAndWait();
        } else {
            // Afficher un message d'erreur si aucune ligne n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un livreur dans le tableau.");
            alert.showAndWait();
        }

    }
    private void generateLivraisonPDF(Livraison livraison) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            // Écrire les informations du Livreur dans le PDF
            writeText(contentStream, "Date: " + livraison.getDate());
            writeText(contentStream, "Adresse: " + livraison.getAdresse());
            writeText(contentStream, "Client: " + livraison.getClient());
            writeText(contentStream, "Status: " + livraison.getStatus());
            writeText(contentStream, "Quantite: " + livraison.getQuantite());
            writeText(contentStream, "Frais: " + livraison.getFrais());
            writeText(contentStream, "Livreur: " + livraison.getLivreur());

            contentStream.endText();
            contentStream.close();

            // Sauvegarde du document PDF
            File file = new File("Livraison" + livraison.getID() + ".pdf");
            document.save(file);
            document.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void writeText(PDPageContentStream contentStream, String text) throws IOException {
        contentStream.showText(text);
        contentStream.newLineAtOffset(0, -20); // Décalage pour la prochaine ligne
    }

    @FXML
    void rechercheLivraison(ActionEvent event) {
        String termeDeRecherche = FilterField.getText().toLowerCase(); // Convertit en minuscules

        // Recherche dans le TableView
        ObservableList<Livraison> livraisonsFiltrees = FXCollections.observableArrayList();
        for (Livraison livraison : tableView.getItems()) {
            if (livraison.getAdresse().toLowerCase().contains(termeDeRecherche)
                    || livraison.getClient().toLowerCase().contains(termeDeRecherche)
                    || livraison.getStatus().toLowerCase().contains(termeDeRecherche)
                    || livraison.getLivreur().toLowerCase().contains(termeDeRecherche)) { // Utilisez getNom() pour accéder au nom du livreur
                livraisonsFiltrees.add(livraison);
            }
        }
        tableView.setItems(livraisonsFiltrees);
    }


    @FXML
    void initialize() {
        // Configure la table avec les colonnes
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("Client"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        fraisCol.setCellValueFactory(new PropertyValueFactory<>("Frais"));
        livreurCol.setCellValueFactory(new PropertyValueFactory<>("Livreur"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        // Charge les données des livraisons dans la table
        LivraisonService livraisonService = new LivraisonService();
        try {
            tableView.setItems(FXCollections.observableList(livraisonService.recuperer()));
        } catch (SQLException e) {
            // Gère l'erreur de récupération des données
            e.printStackTrace();
        }

    }

}
