package tn.scholarcircle.testapp.models;

import java.time.LocalDate;

public class Livraison {
    private Integer ID;
    private LocalDate Date;
    private String Adresse;
    private String Client;
    private String Status;
    private Integer Quantite;
    private Double Frais;
    private String Livreur; // Mettez à jour le type de l'attribut Livreur

    public Livraison(Integer ID, LocalDate date, String adresse, String client, String status, Integer quantite, Double frais, String livreur) {
        this.ID = ID;
        Date = date;
        Adresse = adresse;
        Client = client;
        Status = status;
        Quantite = quantite;
        Frais = frais;
        Livreur = livreur;
    }

    public Livraison(LocalDate date, String adresse, String client, String status, Integer quantite, Double frais, String livreur) {
        Date = date;
        Adresse = adresse;
        Client = client;
        Status = status;
        Quantite = quantite;
        Frais = frais;
        Livreur = livreur;
    }

    public Livraison() {

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getQuantite() {
        return Quantite;
    }

    public void setQuantite(Integer quantite) {
        Quantite = quantite;
    }

    public Double getFrais() {
        return Frais;
    }

    public void setFrais(Double frais) {
        Frais = frais;
    }

    public String getLivreur() {
        return Livreur;
    }

    public void setLivreur(String livreur) {
        Livreur = livreur;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "ID=" + ID +
                ", Date=" + Date +
                ", Adresse='" + Adresse + '\'' +
                ", Client='" + Client + '\'' +
                ", Status='" + Status + '\'' +
                ", Quantite=" + Quantite +
                ", Frais=" + Frais +
                ", Livreur=" + Livreur +
                '}';
    }
}


