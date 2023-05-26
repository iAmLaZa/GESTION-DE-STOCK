package interfaces;

import classes.Vente;
import classes.detailvente;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public interface Ivente {

    public boolean ajoutervente();
    public ObservableList<Vente> listevente(LocalDate l) throws SQLException;
    public double Totale(String l) throws SQLException;
    public double bene(String l) throws SQLException;
    public int nbvente(String l) throws SQLException;
    public ObservableList<Vente> listeventemios(String annees,String mois) throws SQLException;
    public ObservableList<Vente> listeventeannees(String annees ) throws SQLException;
    public ObservableList<detailvente> listedetailvente(int id) throws SQLException;
}
