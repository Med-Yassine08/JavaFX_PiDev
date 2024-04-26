package tn.scholarcircle.testapp.models;

public class Livreur {

    private Integer ID;
    private String Nom;
    private String Prenom;
    private String Adresse;
    private  String Email;
    private Integer NumTel;
    private String Zone;
    private String ModeTransport;



    public Livreur(String nom, String prenom, String adresse, String email, Integer numTel, String zone, String modeTransport) {
        Nom = nom;
        Prenom = prenom;
        Adresse = adresse;
        Email = email;
        NumTel = numTel;
        Zone = zone;
        ModeTransport = modeTransport;
    }

    public Livreur(Integer ID, String nom, String prenom, String adresse, String email, Integer numTel, String zone, String modeTransport) {
        this.ID = ID;
        Nom = nom;
        Prenom = prenom;
        Adresse = adresse;
        Email = email;
        NumTel = numTel;
        Zone = zone;
        ModeTransport = modeTransport;
    }

    public Livreur() {

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getNumTel() {
        return NumTel;
    }

    public void setNumTel(Integer numTel) {
        NumTel = numTel;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getModeTransport() {
        return ModeTransport;
    }

    public void setModeTransport(String modeTransport) {
        ModeTransport = modeTransport;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "ID=" + ID +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Email='" + Email + '\'' +
                ", NumTel=" + NumTel +
                ", Zone='" + Zone + '\'' +
                ", ModeTransport='" + ModeTransport + '\'' +
                '}';
    }
}
