package interfaces;


import classes.detailliv;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface Idetliv {
    public boolean ajouterdetliv() throws SQLException;
    public ObservableList<detailliv> listedetliv()throws SQLException;

}
