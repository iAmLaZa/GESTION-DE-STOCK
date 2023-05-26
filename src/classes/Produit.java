package classes;

import interfaces.Iprod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Produit implements Iprod {

    private static Connection con = BD.connect();

    private String codeprod,nomprod;
    private int qte;
    private double prixachat,prixvente;

    public Produit(String codeprod, String nomprod, int qte, double prixachat, double prixvente) {
        this.codeprod = codeprod;
        this.nomprod = nomprod;
        this.qte = qte;
        this.prixachat = prixachat;
        this.prixvente = prixvente;
    }

    public Produit(String codeprod, double prixachat, double prixvente) {
        this.codeprod = codeprod;
        this.prixachat = prixachat;
        this.prixvente = prixvente;
    }
    public Produit(){};

    //Setter and getter :


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

    public String getCodeprod() {
        return codeprod;
    }

    public void setCodeprod(String codeprod) {
        this.codeprod = codeprod;
    }

    public double getPrixachat() {
        return prixachat;
    }

    public void setPrixachat(double prixachat) {
        this.prixachat = prixachat;
    }

    public double getPrixvente() {
        return prixvente;
    }

    public void setPrixvente(double prixvente) {
        this.prixvente = prixvente;
    }

    @Override
    public boolean ajouterprod() {
        try{
            PreparedStatement pr=con.prepareStatement("insert into produit values('"+this.getCodeprod()+"','"+this.getNomprod()+"',"+this.getQte()+","+this.getPrixachat()+","+this.getPrixvente()+")");
            return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public ObservableList<Produit> listeprod() throws SQLException {
        ObservableList<Produit> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select * from produit");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new Produit(rs.getString("codeprod"),rs.getString("nomprod"),rs.getInt("qte"),rs.getDouble("prixachat"),rs.getDouble("prixvente")));
        }
        return liste;
    }

    @Override
    public boolean modifieprod() {
        try{
            PreparedStatement pr=con.prepareStatement("update produit set codeprod='"+this.getCodeprod()+
                    "',nomprod='"+this.getNomprod()+
                    "',qte="+this.getQte()+
                    ",prixachat="+this.getPrixachat()+
                    ",prixvente="+this.getPrixvente()+" where codeprod='"+this.getCodeprod()+"'");
            return !pr.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprod() {
        try{
            PreparedStatement pr=con.prepareStatement("delete from produit where codeprod='"+this.getCodeprod()+"'");
            return !pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Double prixachat(String codeprod) throws SQLException {
        PreparedStatement pr=con.prepareStatement("select prixachat from produit where codeprod='"+codeprod+"'");
        ResultSet rs=pr.executeQuery();
        if(rs.next()){
            return rs.getDouble("prixachat");
        }
        return 0.0;
    }

    @Override
    public boolean updateqte(String codeprod,int qte) {
        try{
            PreparedStatement p=con.prepareStatement("select qte from produit where codeprod='"+codeprod+"'");
            ResultSet rs=p.executeQuery();
            if(rs.next())
                qte+=rs.getInt("qte");
            PreparedStatement pr=con.prepareStatement("update produit set qte="+qte+" where codeprod='"+codeprod+"'");
            return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateprixachat() {
        try{
            PreparedStatement pr=con.prepareStatement("update produit set prixachat="+this.getPrixachat()+" where codeprod='"+this.getCodeprod()+"'");
            return !pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public int lastid() {
        try {
            PreparedStatement pr = con.prepareStatement(" select last_insert_id();");
            ResultSet rs=pr.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int qteprod(String codeprod) {
                try{
            PreparedStatement p=con.prepareStatement("select qte from produit where codeprod='"+codeprod+"'");
            ResultSet rs=p.executeQuery();
            if(rs.next()){
                return rs.getInt("qte");
            }
                } catch (SQLException ex) {
            Logger.getLogger(Produit.class.getName()).log(Level.SEVERE, null, ex);
        }
                return 0;
    }
}
