package com.afpa.village_green.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.afpa.village_green.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 80010-82-15
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txt_login;
    @FXML
    private TextField txt_pwd;
    @FXML
    private Label error_login;
    @FXML
    private Label error_pwd;
    @FXML
    private Button btn_valider;
    @FXML
    private Button btn_annuler;
    private int nb_errors = 0;
    ResultSet result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_login.setText("christian");
        txt_pwd.setText("christian");

    }

    @FXML
    private void signin(ActionEvent event) throws IOException {
        /*
        *vérificataion du champ login
         */
        // si le champ est vide 
        if ("".equals(txt_login.getText())) {
            champ_vide(error_login, txt_login);
            nb_errors++;
        } // si le champ est rempli [A-Za-z, ]
        else {
            // test de la regex
            if (txt_login.getText().matches("([A-Za-z, ]{1,50})")) { // si regex ok
                // si n'existe pas en bd
                if (!exists(txt_login.getText())) {
                    existe_bd(error_login, txt_login);
                    nb_errors++;
                } else { // si existe en bd cad tout est ok
                    champ_ok(error_login, txt_login);
                }
            } else { // si champ rempli, regex ok, et nom inexistant en bd 
                nb_errors++;
                format_incorrect(txt_login, error_login);
            }
        }
        /*
        *vérificataion du champ mot de passe
         */
        // si le champ est vide 
        if ("".equals(txt_pwd.getText())) {
            champ_vide(error_pwd, txt_pwd);
            nb_errors++;
        } // si le champ est rempli [A-Za-z, ]
        else {
            if (!txt_pwd.getText().matches("([A-Za-z ÁÀÂÄÃÅÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝÆÇáàâäãåéèêëíìîïñóòôöõúùûüýÿæç'0-9]{1,10}+$)")) {
                format_incorrect(txt_pwd, error_pwd);
            } else {
                champ_ok(error_pwd, txt_pwd);
                // redirection sur la page d'accueil 
                App.setRoot("views/Accueil");
            }
        }

    }

    @FXML
    private void annuler(ActionEvent event) {
        clear();
    }

    private void clear() {
        txt_login.clear();
        txt_pwd.clear();
    }

    /*
    *  style si champ vide
     */
    private void champ_vide(Label label, TextField txt) {
        txt.setStyle("-fx-border-color: red");
        label.setStyle("-fx-text-fill: red");
        label.setText("Champ obligatoire");
    }

    /*
    *  style si format invalide
     */
    private void format_incorrect(TextField txt, Label label) {
        txt.setStyle("-fx-border-color: red");
        label.setStyle("-fx-text-fill: red");
        label.setText("format incorrect");
    }

    /*
    *  style si format existe en bd
     */
    private void existe_bd(Label label, TextField txt) {
        txt.setStyle("-fx-border-color: red");
        label.setStyle("-fx-text-fill: red");
        label.setText("N'existe pas!");
    }

    /**
     * Verifie si login existe en bd
     */
    private boolean exists(String login) {
        try {
            //établissement de la connexion au lien JDBC
            String chemin = "jdbc:mysql://localhost:3306/village_green?serverTimezone=UTC";
            String user = "root";
            String pwd = "";
            Connection connexion = DriverManager.getConnection(chemin, user, pwd);
            PreparedStatement stm = connexion.prepareStatement("select * from users where login=?");
            stm.setString(1, txt_login.getText());
            result = stm.executeQuery();
            if (result.next()) { // si y a pas de resultat
                System.out.println("ce login existe bien en base");
                return true;
            } else {
                System.out.println("ce login n'existe pas en base");
                return false;
            }
        } catch (Exception e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Erreur de connexion à la base de données.\n Veuillez contacter l'administrateur");
            alert.showAndWait();
            System.out.println("echec de la connexion");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /*
    *  style si tout es ok
    */
    private void champ_ok(Label label, TextField txt) {
        txt.setStyle("-fx-border-color: none");
        label.setStyle("-fx-text-fill: none");
        label.setText("");
    }

}
