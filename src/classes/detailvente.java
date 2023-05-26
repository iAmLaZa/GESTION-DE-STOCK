package classes;

import interfaces.Idetvente;
import sample.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class detailvente  implements Idetvente {
    private static Connection con = BD.connect();
    private int idvente;
    private String codeprod,nomprod;
    private double prix;
    private int qte;

    public detailvente(int idvente, String codeprod, double prix, int qte) {
        this.idvente = idvente;
        this.codeprod = codeprod;
        this.prix = prix;
        this.qte = qte;
    }



    public detailvente(String codeprod, double prix, int qte) {
        this.codeprod = codeprod;
        this.prix = prix;
        this.qte = qte;
    }

    public detailvente() {
    }

    public int getIdvente() {
        return idvente;
    }

    public void setIdvente(int idvente) {
        this.idvente = idvente;
    }

    public String getCodeprod() {
        return codeprod;
    }

    public void setCodeprod(String codeprod) {
        this.codeprod = codeprod;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public boolean ajouterdetvente() throws SQLException {
        try{
            PreparedStatement pr=con.prepareStatement("insert into detailvente values ("+this.getIdvente()+",'"+this.getCodeprod()+"',"+this.getPrix()+","+this.getQte()+")");
            return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
