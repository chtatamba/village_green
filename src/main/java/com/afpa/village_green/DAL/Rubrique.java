/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afpa.village_green.DAL;

/**
 *
 * @author 80010-82-15
 */
public class Rubrique {
    private int idRubric;
    private String nameRubric;
    private int idRubric_1;

    public Rubrique(int idRubric, String nameRubric, int idRubric_1) {
        this.idRubric = idRubric;
        this.nameRubric = nameRubric;
        this.idRubric_1 = idRubric_1;
    }

    public Rubrique() {
    }

    public int getIdRubric() {
        return idRubric;
    }

    public void setIdRubric(int idRubric) {
        this.idRubric = idRubric;
    }

    public String getNameRubric() {
        return nameRubric;
    }

    public void setNameRubric(String nameRubric) {
        this.nameRubric = nameRubric;
    }

    public int getIdRubric_1() {
        return idRubric_1;
    }

    public void setIdRubric_1(int idRubric_1) {
        this.idRubric_1 = idRubric_1;
    }
    // surcharge de méthode pour récuperer la liste en string
    @Override
    public String toString() {
      return nameRubric; //To change body of generated methods, choose Tools | Templates.
    }
}
