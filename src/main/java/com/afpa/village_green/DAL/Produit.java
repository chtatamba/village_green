/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afpa.village_green.DAL;

import java.util.Date;

/**
 *
 * @author 80010-82-15
 */
public class Produit {
    public String picture, description, libelle;
    int idProduct;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int stock;
    public Double priceAchat, priceVente;
    public Date dateAjout, dateModif;

    public Double getPriceAchat() {
        return priceAchat;
    }

    public void setPriceAchat(Double priceAchat) {
        this.priceAchat = priceAchat;
    }
 
    
    public Produit(){};

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPriceVente() {
        return priceVente;
    }

    public void setPriceVente(Double priceVente) {
        this.priceVente = priceVente;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }

}
