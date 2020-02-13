/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afpa.village_green.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author 80010-82-15
 */
public class ProduitDao {

    Connection con;

    /*
    *connexion à la base de donnée
    */
    public void loadatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/village_green?serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Erreur de connexion à la base de données.\n Veuillez contacter l'administrateur");
        alert.showAndWait();
            System.out.println(e);
            System.out.println("echec de la connexion");

        }
    }

    /*
    * Création nouveau produit
     */
    public boolean insert(Produit prod) {
        boolean inserted = false;
        loadatabase();
        try {
            PreparedStatement stm = con.prepareStatement("INSERT INTO product (libelle, description, stock, priceAchat, priceVente, picture, dateAjout) VALUES (?, ?, ?, ?,?,?,?)");
            stm.setString(1, prod.getLibelle());
            stm.setString(2, prod.getDescription());
            stm.setInt(3, prod.getStock());
            stm.setDouble(4, prod.getPriceAchat());
            stm.setDouble(5, prod.getPriceVente());
            stm.setString(6, prod.getPicture());
            stm.setString(7, today());
            if (stm.executeUpdate()>0) { // mieux à utiliser vu qu'il retroune un int
                inserted = true;
                System.out.println("ajout réussi");
            }
            stm.close();
            con.close();
        } catch (Exception e) {

            System.out.println("erreur d'exécution de la requête d'insertion");
            System.out.println(e.getMessage());
        }
        return inserted;
    }

    /*
    *Modification infos produit
     */
    public boolean update(Produit prod) {
        boolean updated = false;
        loadatabase();
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE  product set description=?, stock=?, priceAchat=?, priceVente=?, dateModif=? where libelle=? ");
            stm.setString(1, prod.getDescription());
            stm.setInt(2, prod.getStock());
            stm.setDouble(3, prod.getPriceAchat());
            stm.setDouble(4, prod.getPriceVente());
            stm.setString(5, today());
            stm.setString(6, prod.getLibelle());
            System.out.println(stm.execute());
            if (stm.executeUpdate()>0) {
                System.out.println(updated);
                updated = true;
            }
            stm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("erreur de modificaion");
            System.out.println(e.getMessage());
        }
        return updated;

    }

    /*
    * suppression de produit
     */
    public boolean delete(Produit prod) {
        boolean deleted = false;
        loadatabase();
        try {
            PreparedStatement stm = con.prepareStatement("DELETE from  product WHERE libelle=?");
            stm.setString(1, prod.getLibelle());
            if (stm.executeUpdate()>0) {
                deleted = true;
            }
            System.out.println(stm);
            stm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("erreur de suppression");
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    /*
    *affichage liste des produits
     */
    public List<Produit> Liste() {
        List<Produit> resultat = new ArrayList<Produit>();
        loadatabase();
        try {
            Statement stm = con.createStatement();
            ResultSet result = stm.executeQuery("SELECT * FROM product");

            while (result.next()) {
                Produit c = new Produit();
                c.setPicture(result.getString("picture"));
                c.setLibelle(result.getString("libelle"));
                c.setDescription(result.getString("description"));
                c.setStock(result.getInt("stock"));
                c.setPriceAchat(result.getDouble("priceAchat"));
                c.setPriceVente(result.getDouble("priceVente"));
                c.setDateAjout(result.getDate("dateAjout"));
                c.setDateModif(result.getDate("dateModif"));
                resultat.add(c);
            }
            System.out.println("éxecution de la requête réussie");
            stm.close();
            result.close();
            con.close();
        } catch (Exception e) {
            System.out.println("erreur d'éxécution de la requête");
            System.out.println(e);
        }

        return resultat;
    }

    /*
    *  obtenir la date et heure du jour
     */
    private String today() {
        // modèle souhaité pour la date et heure
        DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        // objet calendar en appelant la méthode getInstance
        Calendar calobj = Calendar.getInstance();
        // récupération de date et heure sous forme de string
        String format = df.format(calobj.getTime());
        System.out.println("Date et heure du jour: " + format);
        return format;
    }
}
