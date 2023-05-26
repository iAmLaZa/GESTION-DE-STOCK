package classes;

import interfaces.Iliv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class LivRetrait implements Iliv {

    private static Connection con = BD.connect();

    private int id;
    private LocalDate date;
    private double prix;


    public LivRetrait(int id, LocalDate date, double prix) {
        this.id = id;
        this.date = date;
        this.prix = prix;

    }

    public LivRetrait() {
    }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        LivRetrait.con = con;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }



    @Override
    public boolean ajouterliv() {
        try{
            PreparedStatement pr=con.prepareStatement("insert into livraison (dateliv,prix) values ('"+ this.getDate() +"',"+this.getPrix()+")");
            return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public ObservableList<LivRetrait> listeliv() throws SQLException {
        ObservableList<LivRetrait> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select * from livraison");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new LivRetrait(rs.getInt("idliv"),rs.getDate("dateliv").toLocalDate(),rs.getDouble("prix")));
        }
        return liste;
    }


}


