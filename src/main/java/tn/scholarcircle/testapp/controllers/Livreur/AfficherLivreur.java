package tn.scholarcircle.testapp.controllers.Livreur;

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
import tn.scholarcircle.testapp.services.LivreurService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherLivreur {

    @FXML
    private TextField FilterField;

    @FXML
    private TableColumn<Livreur, String> adresseCol;

    @FXML
    private TableColumn<Livreur, String> emailCol;

    @FXML
    private TableColumn<Livreur, String> modetransportCol;

    @FXML
    private TableColumn<Livreur, String> nomCol;

    @FXML
    private TableColumn<Livreur, Integer> numtelCol;

    @FXML
    private TableColumn<Livreur, String> prenomCol;

    @FXML
    private TableColumn<Livreur, String> zoneCol;



    @FXML
    private TableView<Livreur> tableViewLiv;

    @FXML
    void FermePage(ActionEvent event) {
        Stage stage = (Stage) tableViewLiv.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pageAcceuil(ActionEvent event) {
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
    void rechercheLivreur(ActionEvent event) {
        String termeDeRecherche = FilterField.getText().toLowerCase(); // Convertit en minuscules

        // Recherche dans le TableView
        ObservableList<Livreur> livreurFiltrees = FXCollections.observableArrayList();
        for (Livreur livreur : tableViewLiv.getItems()) {
            if (livreur.getAdresse().toLowerCase().contains(termeDeRecherche)
                    || livreur.getNom().toLowerCase().contains(termeDeRecherche)
                    || livreur.getPrenom().toLowerCase().contains(termeDeRecherche)
                    || livreur.getEmail().toLowerCase().contains(termeDeRecherche)
                    || livreur.getZone().toLowerCase().contains(termeDeRecherche)) {
                livreurFiltrees.add(livreur);
            }
        }
        tableViewLiv.setItems(livreurFiltrees);
    }

    @FXML
    void initialize() {

        LivreurService livreurService = new LivreurService();
        try {
            List<Livreur> livreurs = livreurService.recuperer();
            ObservableList<Livreur> observableList = FXCollections.observableList(livreurs);
            tableViewLiv.setItems(observableList);
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
            modetransportCol.setCellValueFactory(new PropertyValueFactory<>("ModeTransport"));
            numtelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
            zoneCol.setCellValueFactory(new PropertyValueFactory<>("Zone"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des livraisons : " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void pdfLivreur(ActionEvent event) {
        Livreur livreurSelectionne = tableViewLiv.getSelectionModel().getSelectedItem();

        if (livreurSelectionne != null) {
            // Générer le PDF pour le Livreur sélectionné
            generateLivreurPDF(livreurSelectionne);

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

    private void generateLivreurPDF(Livreur livreur) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            // Écrire les informations du Livreur dans le PDF
            writeText(contentStream, "Nom: " + livreur.getNom());
            writeText(contentStream, "Prenom: " + livreur.getPrenom());
            writeText(contentStream, "Adresse: " + livreur.getAdresse());
            writeText(contentStream, "Email: " + livreur.getEmail());
            writeText(contentStream, "NumTel: " + livreur.getNumTel());
            writeText(contentStream, "Zone: " + livreur.getZone());
            writeText(contentStream, "ModeTransport: " + livreur.getModeTransport());

            contentStream.endText();
            contentStream.close();

            // Sauvegarde du document PDF
            File file = new File("Livreur" + livreur.getID() + ".pdf");
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

}

