package interfaces;

import classes.LivRetrait;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface Iliv {
    public boolean ajouterliv();
    public ObservableList<LivRetrait> listeliv()throws SQLException;

}
