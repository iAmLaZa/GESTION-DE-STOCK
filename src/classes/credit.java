/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import interfaces.Icredit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.BD;

/**
 *
 * @author User
 */
public class credit implements Icredit{
    
    private String nom,prenom;
    private Double prix;

    public credit(String nom, String prenom, Double prix) {
        this.nom = nom;
        this.prenom = prenom;
        this.prix = prix;
    }

    public credit() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
     private static Connection con = BD.connect();
    @Override
    public boolean ajoutercredit() {
        try{
            PreparedStatement pr=con.prepareStatement("insert into credit values('"+this.getNom()+"','"+this.getPrenom()+"',"+this.getPrix()+")");
            return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public ObservableList<credit> listecredit() throws SQLException {
        ObservableList<credit> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select * from credit");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new credit(rs.getString("nom"),rs.getString("prenom"),rs.getDouble("prix")));
        }
        return liste;
    }

    @Override
    public boolean modifiecredit() {
         try{
            PreparedStatement pr=con.prepareStatement("update credit set prix="+this.getPrix()+
                   " where nom='"+this.getNom()+"' and prenom='"+this.getPrenom()+"'");
            return !pr.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean suppcredit() {
        try{
            PreparedStatement pr=con.prepareStatement("delete from credit where nom='"+this.getNom()+"' and prenom='"+this.getPrenom()+"'");
            return !pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    
    
    
    
    
}
