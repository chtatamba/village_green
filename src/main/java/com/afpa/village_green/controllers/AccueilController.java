/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afpa.village_green.controllers;

import com.afpa.village_green.DAL.Produit;
import com.afpa.village_green.DAL.ProduitDao;
import com.afpa.village_green.DAL.Rubrique;
import com.afpa.village_green.DAL.RubriqueDao;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 80010-82-15
 */
public class AccueilController implements Initializable {

    @FXML
    private Tab gestion_produits;
    @FXML
    private TableView<Produit> tableView;
    @FXML
    private TableColumn<Produit, String> col_picture;
    @FXML
    private TableColumn<Produit, String> col_libelle;
    @FXML
    private TableColumn<Produit, Integer> col_stock;
    @FXML
    private TableColumn<Produit, Double> col_prix;
    // initialisation d'une observable list obligatoire
    ObservableList<Produit> list = FXCollections.observableArrayList();
    @FXML
    private Pane case_detail;
    @FXML
    private TextField detail_libelle;
    @FXML
    private TextArea detail_description;
    @FXML
    private TextField detail_stock;
    @FXML
    private TextField detail_achat;
    @FXML
    private TextField detail_vente;
    @FXML
    private TextField detail_dteajout;
    @FXML
    private TextField detail_dtemodif;
    @FXML
    private Button close;
    @FXML
    private Button delete;
    @FXML
    private Button update;
    Alert alertbox;
    private int identifiant;
    @FXML
    private TextField modif_libelle;
    @FXML
    private TextArea modif_description;
    @FXML
    private TextField modif_stock;
    @FXML
    private TextField modif_prixAchat;
    @FXML
    private TextField modif_prixVente;
    @FXML
    private Button ok_update;
    @FXML
    private Button btn_annuler;
    @FXML
    private Pane case_modifier;
    @FXML
    private AnchorPane caca;
    @FXML
    private Button add_product;
    @FXML
    private Button ok_ajouter;
    @FXML
    private Button btn_annuler1;
    @FXML
    private Pane case_ajouter;
    @FXML
    private TextField ajout_libelle;
    @FXML
    private TextArea ajout_description;
    @FXML
    private TextField ajout_stock;
    @FXML
    private TextField ajout_prixAchat;
    @FXML
    private TextField ajout_prixVente;
    @FXML
    private Label lbl_libelle;
    @FXML
    private Label lbl_description;
    @FXML
    private Label lbl_sotck;
    @FXML
    private Label lbl_achat;
    @FXML
    private Label lbl_vente;
    @FXML
    private Label lbl_modifLibelle;
    @FXML
    private Label lbl_modifDescription;
    @FXML
    private Label lbl_modifStock;
    @FXML
    private Label lbl_modifAchat;
    @FXML
    private Label lbl_modifVente;
    // initialisation d'une observable list catégorie
    ObservableList<Rubrique> list1 = FXCollections.observableArrayList();
    // ibservable list sous categorie
    ObservableList<Rubrique> list2 = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Rubrique> ajout_categorie;
    @FXML
    private ComboBox<Rubrique> sous_cat;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // création objet avec clientdao
        ProduitDao repo = new ProduitDao();
        // Jonction du tableau avec les données
        col_picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
        col_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("priceVente"));
        // ajout des données à l'observable list
        list.addAll(repo.Liste());
        // On indique au TableView quelle modèle observer pour trouver les données
        tableView.setItems(list);
        // btn ajouter visible
        add_product.setVisible(true);
        // gestion des rubriques
        RubriqueDao rub = new RubriqueDao();
        list1.addAll(rub.Liste_rubrique());
        ajout_categorie.setItems(list1);
        // selection du premier element de la combox
        ajout_categorie.getSelectionModel().select(0);    
        
    }

    /*
    * affiche les détails du produit
     */
    @FXML
    private void choix_produit(MouseEvent event) {
        show_form(case_detail);
        update.setVisible(true);
        delete.setVisible(true);
        case_modifier.setVisible(false);
        // récuperation des infos et affichage des détails
        detail_libelle.setText(tableView.getSelectionModel().getSelectedItem().getLibelle());
        detail_description.setText(tableView.getSelectionModel().getSelectedItem().getDescription());
        detail_stock.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getStock()));
        detail_achat.setText(tableView.getSelectionModel().getSelectedItem().getPriceAchat().toString());
        detail_vente.setText(tableView.getSelectionModel().getSelectedItem().getPriceVente().toString());
        detail_dteajout.setText(DateToString(tableView.getSelectionModel().getSelectedItem().getDateAjout()));
        detail_dtemodif.setText(DateToString(tableView.getSelectionModel().getSelectedItem().getDateModif()));
    }

    /*
    * show form pour détecter le formulaire actif
     */
    private void show_form(Pane form) {
        case_detail.setVisible(false);
        case_modifier.setVisible(false);
        case_ajouter.setVisible(false);
        form.setVisible(true);
        add_product.setVisible(true);
        add_product.setLayoutX(1363);
        add_product.setLayoutY(134);
    }

    /*
    * ferme le formulaire de détail
     */
    @FXML
    private void click_close(ActionEvent event) {
        case_detail.setVisible(false);
        update.setVisible(false);
        delete.setVisible(false);
        clear_saisie_ajout();
    }

    /*
    * clique sur le bouton supprimer
     */
    @FXML
    private void click_supprimer(ActionEvent event) {
        showConfirmation("Message", "Etes vous sur de vouloir supprimer?");
        if (alertbox.getResult() == ButtonType.OK) {
            Produit p = tableView.getSelectionModel().getSelectedItem();
            ProduitDao repo = new ProduitDao();
            if (repo.delete(p)) {
                identifiant = tableView.getSelectionModel().getSelectedIndex();
                list.get(identifiant);
                list.remove(identifiant);
                case_detail.setVisible(false);
            success("Message", "le produit a bien été supprimé");
            }
        } else {
            alertbox.close();
        }

    }

    /*
    * clique sur le bouton modifier
     */
    @FXML
    private void click_modifier(ActionEvent event) {
        //afficher le formulaire de modif
        show_form(case_modifier);
        // récuperation des infos
        modif_libelle.setText(tableView.getSelectionModel().getSelectedItem().getLibelle());
        modif_description.setText(tableView.getSelectionModel().getSelectedItem().getDescription());
        modif_stock.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getStock()));
        modif_prixAchat.setText(tableView.getSelectionModel().getSelectedItem().getPriceAchat().toString());
        modif_prixVente.setText(tableView.getSelectionModel().getSelectedItem().getPriceVente().toString());
    }

    /*
    * message de succès
     */
    public void success(String titre, String texte) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // titre
        alert.setTitle(titre);
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(texte);
        alert.showAndWait();
    }

    /*
    * message de demande de confirmation
     */
    public void showConfirmation(String titre, String texte) {

        alertbox = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", ButtonType.OK, ButtonType.CANCEL);
        // titre
        alertbox.setTitle(titre);
        // Header Text: null
        alertbox.setHeaderText(null);
        alertbox.setContentText(texte);
        alertbox.showAndWait();
    }

    /*
    * message d'echec
     */
    public void failed(String titre, String texte) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(texte);
        alert.showAndWait();
    }

    /*
    * Convertir une date en string
     */
    private String DateToString(Date result) {
        Date date = result;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        return strDate;

    }

    /*
    *Validation de la modification
     */
    @FXML
    private void valider_update(ActionEvent event) {
        int nb_errors = 0;
        // vérification du champ description
        if ("".equals(modif_description.getText())) { // champ vide
            nb_errors++;
            modif_description.setStyle("-fx-border-color: red");
            lbl_modifDescription.setStyle("-fx-text-fill: red");
            lbl_modifDescription.setText("Champ obligatoire");
        } else {
            if (!modif_description.getText().matches("^[A-Za-z ÁÀÂÄÃÅÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝÆÇáàâäãåéèêëíìîïñóòôöõúùûüýÿæç\\\\'0-9,.\\-_\"]+$")) { // si regex ok
                modif_description.setStyle("-fx-border-color: red");
                lbl_modifDescription.setStyle("-fx-text-fill: red");
                lbl_modifDescription.setText("format invalide");
                nb_errors ++;
            } else { // si regex ne matche pas
                modif_description.setStyle("-fx-border-color: none");
                lbl_modifDescription.setStyle("-fx-text-fill: none");
                lbl_modifDescription.setText("");
                nb_errors=0;
            }
        }

        // vérification du champ stock
        if ("".equals(modif_stock.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_modifStock, modif_stock);
        } else {
            if (modif_stock.getText().matches("^[0-9]{1,4}")) { // si regex ok
                champ_ok(lbl_modifStock, modif_stock);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(modif_stock, lbl_modifStock);
                nb_errors++;
            }
        }
        // vérification du champ prix d'achat
        if ("".equals(modif_prixAchat.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_modifAchat, modif_prixAchat);
        } else {
            if (modif_prixAchat.getText().matches("^[0-9]{1,4}(\\.[0-9]{1,2})?")) { // si regex ok
                champ_ok(lbl_modifAchat, modif_prixAchat);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(modif_prixAchat, lbl_modifAchat);
                nb_errors++;
            }
        }

        // vérification du champ prix de vente
        if ("".equals(modif_prixVente.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_modifVente, modif_prixVente);
        } else {
            if (modif_prixVente.getText().matches("^[0-9]{1,4}(\\.[0-9]{1,2})?")) { // si regex ok
                champ_ok(lbl_modifVente, modif_prixVente);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(modif_prixVente, lbl_modifVente);
                nb_errors++;
            }
        }
        // si pas d'erreur dans le formulaire 
        if (nb_errors == 0) {

            Produit c = tableView.getSelectionModel().getSelectedItem();
            c.libelle = modif_libelle.getText();
            c.description = modif_description.getText();
            c.stock = Integer.parseInt(modif_stock.getText());
            c.priceAchat = Double.parseDouble(modif_prixAchat.getText());
            c.priceVente = Double.parseDouble(modif_prixVente.getText());
            ProduitDao repo = new ProduitDao();
            if (repo.update(c)) {
                // formulaire ajout invisible
                show_form(case_modifier);
                // ajout des infos à la table views
                list.set(tableView.getSelectionModel().getSelectedIndex(), c);
                case_modifier.setVisible(false);
                success("Message", "la modification a bien été prise en compte");
            } else {
                failed("Message", "Erreur lors de la modification des informations du produit");
            }
        }
    }

    /*
    * annule la modification
     */
    @FXML
    private void annuler_saisie(ActionEvent event) {
        show_form(case_detail);
    }

    /*
    * clique sur le bouton ajouter
     */
    @FXML
    private void click_ajouter(ActionEvent event) {
        show_form(case_ajouter);
        add_product.setVisible(false);
        update.setVisible(false);
        delete.setVisible(false);
    }

    /*
    * validation de l'ajout du produit
     */
    @FXML
    private void valider_ajouter(ActionEvent event) {
        int nb_errors = 0;
        // vérification du champ libelle
        if ("".equals(ajout_libelle.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_libelle, ajout_libelle);
        } else {
            if (ajout_libelle.getText().matches("([A-Za-z]{2}[0-9]{1,10}+$)")) { // si regex ok
                if (exists(ajout_libelle.getText())) { // si existe en  base
                    existe_bd(lbl_libelle, ajout_libelle);
                    nb_errors++;
                } else { // si existe en bd cad tout est ok
                    champ_ok(lbl_libelle, ajout_libelle);
                    nb_errors = 0;
                }
            } else { // si regex ne matche pas
                format_incorrect(ajout_libelle, lbl_libelle);
                nb_errors++;
            }
        }

        // vérification du champ description
        if ("".equals(ajout_description.getText())) { // champ vide
            nb_errors++;
            ajout_description.setStyle("-fx-border-color: red");
            lbl_description.setStyle("-fx-text-fill: red");
            lbl_description.setText("Champ obligatoire");
        } else {
            if (!ajout_description.getText().matches("^[A-Za-z ÁÀÂÄÃÅÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝÆÇáàâäãåéèêëíìîïñóòôöõúùûüýÿæç\\\\'0-9,.\\-_\"]+$")) { // si regex ok
                ajout_description.setStyle("-fx-border-color: red");
                lbl_description.setStyle("-fx-text-fill: red");
                lbl_description.setText("format invalide");
                nb_errors++;
            } else { // si regex ne matche pas
                ajout_description.setStyle("-fx-border-color: none");
                lbl_description.setStyle("-fx-text-fill: none");
                lbl_description.setText("");
                nb_errors=0;
            }
        }

        // vérification du champ stock
        if ("".equals(ajout_stock.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_sotck, ajout_stock);
        } else {
            if (ajout_stock.getText().matches("^[0-9]{1,4}")) { // si regex ok
                champ_ok(lbl_sotck, ajout_stock);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(ajout_stock, lbl_sotck);
                nb_errors++;
            }
        }

        // vérification du champ prix d'achat
        if ("".equals(ajout_prixAchat.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_achat, ajout_prixAchat);
        } else {
            if (ajout_prixAchat.getText().matches("^[0-9]{1,4}(\\.[0-9]{1,2})?")) { // si regex ok
                champ_ok(lbl_achat, ajout_prixAchat);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(ajout_prixAchat, lbl_achat);
                nb_errors++;
            }
        }

        // vérification du champ prix de vente
        if ("".equals(ajout_prixVente.getText())) { // champ vide
            nb_errors++;
            champ_vide(lbl_vente, ajout_prixVente);
        } else {
            if (ajout_prixVente.getText().matches("^[0-9]{1,4}(\\.[0-9]{1,2})?")) { // si regex ok
                champ_ok(lbl_vente, ajout_prixVente);
                nb_errors = 0;
            } else { // si regex ne matche pas
                format_incorrect(ajout_prixVente, lbl_vente);
                nb_errors++;
            }
        }
        // si pas d'erreur dans le formulaire 
        if (nb_errors == 0) {
            Produit c = new Produit();
            c.libelle = ajout_libelle.getText();
            c.description = ajout_description.getText();
            c.stock = Integer.parseInt(ajout_stock.getText());
            c.priceAchat = Double.parseDouble(ajout_prixAchat.getText());
            c.priceVente = Double.parseDouble(ajout_prixVente.getText());
            c.picture = "jpg";
            ProduitDao repo = new ProduitDao();
            if (repo.insert(c)) {
                list.add(c);
                case_ajouter.setVisible(false);
                success("Message", "Le produit a été ajouté avec succès");   
            } else {
                failed("Message", "erreur lors de l'ajout du produit");
            }
        }

    }

    /*
    * annule l'ajout du produit
     */
    @FXML
    private void annuler_ajout(ActionEvent event) {
        case_ajouter.setVisible(false);
        add_product.setVisible(true);
        clear_saisie_ajout();
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
    *  style si tout es ok
     */
    private void champ_ok(Label label, TextField txt) {
        txt.setStyle("-fx-border-color: none");
        label.setStyle("-fx-text-fill: none");
        label.setText("");
    }

    /*
    *  style si format existe en bd
     */
    private void existe_bd(Label label, TextField txt) {
        txt.setStyle("-fx-border-color: red");
        label.setStyle("-fx-text-fill: red");
        label.setText("Existe déja");
    }

    /*
    * remet le formulaire d'ajout vide
     */
    private void clear_saisie_ajout() {
        add_product.setLayoutX(703);
        add_product.setLayoutY(51);
        ajout_description.clear();
        ajout_libelle.clear();
        ajout_prixAchat.clear();
        ajout_prixVente.clear();
        ajout_stock.clear();
        champ_ok(lbl_libelle, ajout_libelle);
        champ_ok(lbl_sotck, ajout_stock);
        champ_ok(lbl_achat, ajout_prixAchat);
        champ_ok(lbl_vente, ajout_prixVente);
        ajout_description.setStyle("-fx-border-color: none");
        lbl_description.setStyle("-fx-text-fill: none");
        lbl_description.setText("format invalide");

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
            PreparedStatement stm = connexion.prepareStatement("select * from product where libelle=?");
            stm.setString(1, ajout_libelle.getText());
            System.out.println(stm);
            ResultSet result = stm.executeQuery();
            if (result.next()) { // si y a pas de resultat
                System.out.println("existe déja");
                return true;
            } else {
                System.out.println("existe déjà");
                return false;
            }
        } catch (Exception e) {
            System.out.println("echec de la connexion");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @FXML
    private void click_cat(MouseEvent event) {
        sous_cat.setDisable(false);
        // récuperer le model de la catégorie
//        Rubrique rub = ajout_categorie.getSelectionModel().getSelectedItem();
        // gestion des sous rubriques
        RubriqueDao sous_rub = new RubriqueDao();
        
        sous_cat.setItems(list2);
        // selection du premier element de la combox
        sous_cat.getSelectionModel().select(0); 
    }
    
}
