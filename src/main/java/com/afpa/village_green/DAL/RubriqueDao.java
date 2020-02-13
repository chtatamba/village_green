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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 80010-82-15
 */
public class RubriqueDao {

    /*
    *affichage liste des rubriques
    */
    public List<Rubrique> Liste_rubrique() {
        List<Rubrique> resultat = new ArrayList<Rubrique>();
        try {
            String url = "jdbc:mysql://localhost:3306/village_green?serverTimezone=UTC";
             Connection con = DriverManager.getConnection(url, "root", "");
                Statement stm = con.createStatement();
                ResultSet result = stm.executeQuery("SELECT * FROM rubric Where idRubric_1 is NULL");
                while (result.next()) {
                    Rubrique r = new Rubrique(result.getInt("idRubric"),result.getString("nameRubric"),result.getInt("idRubric_1"));
                    resultat.add(r);
                    
                }
                System.out.println("éxecution de la requête résussie");
                stm.close();
                result.close();
            
        } catch (SQLException e) {
            System.out.println("erreur d'éxécution de la requête");
            System.out.println(e);
        }

        return resultat;
    }
    
    /*
    *Affiche la liste des sous rubriques
    */
     public List<Rubrique> Liste_SousRubriques() {
        List<Rubrique> resultat1 = new ArrayList<Rubrique>();
        try {
            String url = "jdbc:mysql://localhost:3306/village_green?serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "");
            PreparedStatement stm = con.prepareStatement("SELECT * FROM rubric as cat Join rubric as sous_cat on sous_cat.idRubric_1=cat.idRubric");
            ResultSet result = stm.executeQuery();
            System.out.println(stm.executeQuery());
                while (result.next()) {
                    Rubrique r1 = new Rubrique(result.getInt("idRubric"),result.getString("nameRubric"),result.getInt("idRubric_1"));
                    resultat1.add(r1);
                    
                }
                System.out.println("éxecution de la requête résussie");
                stm.close();
                result.close();
            
        } catch (SQLException e) {
            System.out.println("erreur d'éxécution de la requête");
            System.out.println(e);
        }

        return resultat1;
    }
}
