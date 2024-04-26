package tn.scholarcircle.testapp.test;

import tn.scholarcircle.testapp.models.Livraison;
import tn.scholarcircle.testapp.services.LivraisonService;
import tn.scholarcircle.testapp.utils.MyDatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        LivraisonService ls = new LivraisonService();

        try {
            System.out.println(ls.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
}
