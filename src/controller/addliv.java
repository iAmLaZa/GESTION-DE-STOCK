package controller;

import classes.LivRetrait;
import classes.Produit;
import classes.detailliv;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manipulation.outils;
import sample.BD;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class addliv  implements Initializable {
    private static Connection con = BD.connect();
    int i;
     Produit prd=new Produit();
     int totale=0;
    ObservableList<LivRetrait> liste;
    public void showinformation(ObservableList<LivRetrait> l){
        liste=l;
    }

    @FXML
    TextField searchprod;

    @FXML
    TableView<Produit> tableprodsearch;
    @FXML
    TableColumn<Produit, String> idprodsearch;
    @FXML
    TableColumn<Produit, String> nomprodsearch;
    @FXML
    TableColumn<Produit, Double> prixachatsearch;

    ObservableList<Produit> listesearch;

    public void searchprod(){
        outils.searchglobale(searchprod,listesearch,tableprodsearch);
    }
    @FXML
    TableView<detailliv> tabledetliv;
    @FXML
    TableColumn<detailliv, String> idprod;
    @FXML
    TableColumn<detailliv, String> nomprod;
    @FXML
    TableColumn<detailliv,Integer> qteprod;

    ObservableList<detailliv> listedetliv;

    @FXML
    TextField qtetxt;
    @FXML
    TextField prixachattxt;
    @FXML
    Label totaletxt;
    @FXML
    DatePicker date;
    @FXML
    Button confirmer;


    public void ajouter() throws SQLException {
        detailliv d=new detailliv(prd.getCodeprod(),prd.getNomprod(),Integer.valueOf(qtetxt.getText()));
        listedetliv.add(d);
        totale+=prd.prixachat(prd.getCodeprod())*Integer.valueOf(qtetxt.getText());
        totaletxt.setText(String.valueOf(totale)+" DA");
        qtetxt.setText("");
        prixachattxt.setText("");
        
    }
    public void confirmer(){
        LivRetrait livRetrait=new LivRetrait();
        livRetrait.setDate(date.getValue());
        livRetrait.setPrix(totale);
        totale=0;
        livRetrait.ajouterliv();
        int id=prd.lastid();
        livRetrait.setId(id);
        liste.add(livRetrait);
        for(int j=0;j<listedetliv.size();j++){
            listedetliv.get(j).setIdliv(id);
            listedetliv.get(j).ajouterdetliv();
            prd.updateqte(listedetliv.get(j).getCodeprod(),listedetliv.get(j).getQte());
        }
        Stage stage=(Stage) confirmer.getScene().getWindow();
        stage.close();
    }
    public void scan() throws SQLException {
        PreparedStatement pr=con.prepareStatement("select * from produit where codeprod= ?");
        pr.setString(1,searchprod.getText());
        ResultSet rs=pr.executeQuery();
        if(rs.next()){
            prd=new Produit(rs.getString("codeprod"),rs.getString("nomprod"),rs.getInt("qte"),rs.getDouble("prixachat"),rs.getDouble("prixvente"));
            prixachattxt.setText(String.valueOf(prd.getPrixachat()));
        }
    }
    public void selectprod(){
        prd=tableprodsearch.getSelectionModel().getSelectedItem();
        i=listesearch.indexOf(prd);
        prixachattxt.setText(String.valueOf(prd.getPrixachat()));
    }
    public void updateprix(){
        prd.setPrixachat(Double.valueOf(prixachattxt.getText()));
        prd.updateprixachat();
        listesearch.set(i,prd);
        searchprod.setText("");
        prixachattxt.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idprodsearch.setCellValueFactory(new PropertyValueFactory<Produit,String>("codeprod"));
        nomprodsearch.setCellValueFactory(new PropertyValueFactory<Produit,String>("nomprod"));
        prixachatsearch.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prixachat"));
        Produit p = new Produit();
        try {
            listesearch = p.listeprod();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableprodsearch.setItems(listesearch);
        tableprodsearch.setItems(listesearch);

        idprod.setCellValueFactory(new PropertyValueFactory<detailliv,String>("codeprod"));
        nomprod.setCellValueFactory(new PropertyValueFactory<detailliv,String>("nomprod"));
        qteprod.setCellValueFactory(new PropertyValueFactory<detailliv,Integer>("qte"));
        detailliv detailliv =new detailliv();
        try {
            listedetliv=detailliv.listedetliv();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tabledetliv.setItems(listedetliv);
        date.setValue(LocalDate.now());
        totaletxt.setText("00 DA");

    }
}
