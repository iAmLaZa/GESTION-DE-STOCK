package interfaces;

import classes.Produit;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface Iprod {
    public boolean ajouterprod();
    public ObservableList<Produit> listeprod()throws SQLException;
    public boolean modifieprod();
    public boolean supprod();
    public Double prixachat(String codeprod ) throws SQLException;
    public boolean updateqte(String codeprod,int qte);
    public boolean updateprixachat();
    public int lastid();
    public int qteprod(String codeprod);

}
