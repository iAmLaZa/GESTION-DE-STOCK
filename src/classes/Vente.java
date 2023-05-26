package classes;

import interfaces.Ivente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vente implements Ivente {

    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM");
    private static Connection con = BD.connect();
    private int idvente;
    private LocalDate date;
    private String month;
    private Double totale,benefice;

    public Vente(int idvente, LocalDate date, Double totale, Double benefice) {
        this.idvente = idvente;
        this.date = date;
        this.totale = totale;
        this.benefice = benefice;
    }

    public Vente(LocalDate date, Double totale, Double benefice) {
        this.date = date;
        this.totale = totale;
        this.benefice = benefice;
    }


    public Vente(LocalDate date,String month, Double totale, Double benefice) {
        this.date = date;
        this.month = month;
        this.totale = totale;
        this.benefice = benefice;
    }

    public Vente() {
        this.date=LocalDate.now();

    }

    public Vente(LocalDate date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public int getIdvente() {
        return idvente;
    }

    public void setIdvente(int idvente) {
        this.idvente = idvente;
    }

    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public Double getBenefice() {
        return benefice;
    }

    public void setBenefice(Double benefice) {
        this.benefice = benefice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }




    @Override
    public boolean ajoutervente() {
        try{
            PreparedStatement pr=con.prepareStatement("insert into vente(datevente,totale,benefice) values('"+this.getDate()+"',"+this.getTotale()+","+this.getBenefice()+")");
             return pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public ObservableList<Vente> listevente(LocalDate l) throws SQLException {
        ObservableList<Vente> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select * from vente where datevente='"+l+"'");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new Vente(rs.getInt("idvente"),rs.getDate("datevente").toLocalDate(),rs.getDouble("totale"),
                    rs.getDouble("benefice")));
        }
        return liste;
    }

    @Override
    public double Totale( String l) throws SQLException {
        PreparedStatement pr=con.prepareStatement("select sum(totale) from vente where datevente like ('"+l+"')");
        ResultSet rs=pr.executeQuery();
        if(rs.next())
            return rs.getDouble(1);
        return 0;
    }

    @Override
    public double bene(String l) throws SQLException {
        PreparedStatement pr=con.prepareStatement("select sum(benefice) from vente where datevente like ('"+l+"')");
        ResultSet rs=pr.executeQuery();
        if(rs.next())
            return rs.getDouble(1);
        return 0;
    }

    @Override
    public int nbvente(String l) throws SQLException {
        PreparedStatement pr=con.prepareStatement("select count(benefice) from vente where datevente like ('"+l+"')");
        ResultSet rs=pr.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }

    @Override
    public ObservableList<Vente> listeventemios(String annees,String mois) throws SQLException {
        ObservableList<Vente> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select datevente,sum(totale),sum(benefice) from vente where datevente like ('"+annees+"-"+mois+"-%')  group by day(datevente)");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new Vente(rs.getDate("datevente").toLocalDate(),rs.getDouble("sum(totale)"),
                    rs.getDouble("sum(benefice)")));
        }
        return liste;
    }

    @Override
    public ObservableList<Vente> listeventeannees(String annees) throws SQLException {
        ObservableList<Vente> liste= FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select datevente ,sum(totale),sum(benefice) from vente where datevente like ('"+annees+"-%-%') group by month(datevente)");
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new Vente(rs.getDate("datevente").toLocalDate(),rs.getDate("datevente").toLocalDate().getMonth().toString(),rs.getDouble("sum(totale)"),
                    rs.getDouble("sum(benefice)")));
        }
        return liste;
    }

    @Override
    public ObservableList<detailvente> listedetailvente(int id) throws SQLException {
        ObservableList<detailvente> liste=FXCollections.observableArrayList();
        PreparedStatement pr=con.prepareStatement("select *from detailvente where idvente="+id);
        ResultSet rs=pr.executeQuery();
        while(rs.next()){
            liste.add(new detailvente(rs.getInt("idvente"),rs.getString("codeprod"),rs.getDouble("prix"),rs.getInt("qte")));
        }
        return liste;
    }
}
