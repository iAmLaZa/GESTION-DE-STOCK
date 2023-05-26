package classes;

import interfaces.Idetliv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class detailliv implements Idetliv {
    private static Connection con = BD.connect();
    private int idliv;
    private String codeprod,nomprod;
    private int qte;

    public detailliv(int idliv, String codeprod, String nomprod, int qte) {
        this.idliv = idliv;
        this.codeprod = codeprod;
        this.nomprod = nomprod;
        this.qte = qte;
    }

    public detailliv(String codeprod, String nomprod, int qte) {
        this.codeprod = codeprod;
        this.nomprod = nomprod;
        this.qte = qte;
    }

    public detailliv() {
        idliv=0;
    }

    public detailliv(int idliv) {
        this.idliv = idliv;
    }

    public int getIdliv() {
        return idliv;
    }

    public void setIdliv(int idliv) {
        this.idliv = idliv;
    }

    public String getCodeprod() {
        return codeprod;
    }

    public void setCodeprod(String codeprod) {
        this.codeprod = codeprod;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public boolean ajouterdetliv()  {
        try{
        PreparedStatement pr=con.prepareStatement("insert into detaillivraison values ("+this.getIdliv()+",'"+this.getCodeprod()+"',"+this.getQte()+")");
        return pr.execute();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        return true;
    }

    @Override
    public ObservableList<detailliv> listedetliv() throws SQLException {
        ObservableList<detailliv> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("   select * from detaillivraison inner join produit on detaillivraison.codeprod=produit.codeprod where idliv="+this.getIdliv());
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new detailliv(rs.getString("codeprod"),rs.getString("nomprod"),rs.getInt("qte")));
        }
        return liste;
    }
}
