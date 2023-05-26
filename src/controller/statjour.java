package controller;

import classes.Vente;
import classes.detailvente;
import java.io.InputStream;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import sample.BD;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class statjour implements Initializable {
     String date,window;
    public void showinformation(String d,String w){
        date=d;
        window=w;
        if(window=="menu")
            choixdate.setVisible(true);
        else {
            choixdate.setVisible(false);
            Vente v=new Vente();
            try{
                listevente=v.listevente(LocalDate.parse(date));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            tablestatjour.setItems(listevente);

            try {
                nbvente.setText(nbvente.getText()+"\n"+v.nbvente(date));
                beneficetxt.setText(beneficetxt.getText()+"\n"+v.bene(date));
                totaletxt.setText(totaletxt.getText()+"\n"+v.Totale(date));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    @FXML
    DatePicker choixdate;
    @FXML
    TableView<Vente> tablestatjour;
    @FXML
    TableColumn<Vente, LocalDate> datevente;
    @FXML
    TableColumn<Vente,Double> totale;
    @FXML
    TableColumn<Vente,Double> benefice;

    ObservableList<Vente> listevente;
    @FXML
    Label nbvente;
    @FXML
    Label totaletxt;
    @FXML
    Label beneficetxt;

    public void choixdate(){
        Vente v=new Vente();
        try{
            listevente=v.listevente(choixdate.getValue());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablestatjour.setItems(listevente);

        try {
            nbvente.setText("Nombre De Vente :"+"\n"+v.nbvente(choixdate.getValue().toString()));
            beneficetxt.setText("Bénéfices :"+"\n"+v.bene(choixdate.getValue().toString()));
            totaletxt.setText("Totale :"+"\n"+v.Totale(choixdate.getValue().toString()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    TableView<detailvente> tabledetail;
    @FXML
    TableColumn<detailvente,Integer> idvente;
    @FXML
    TableColumn<detailvente,String> codeprod;
    @FXML
    TableColumn<detailvente,Double> prix;
    @FXML
    TableColumn<detailvente,Integer> qte;
    ObservableList<detailvente> listedetail;

    Vente v=new Vente();
    public void infovente() throws SQLException {
        int i=tablestatjour.getSelectionModel().getFocusedIndex();
        listedetail=v.listedetailvente(listevente.get(i).getIdvente());
        tabledetail.setItems(listedetail);
    }
    private static Connection con = BD.connect();
   public void stat()  {
    try {

            InputStream path= getClass().getResourceAsStream("/sample/salim.jasper");
            JasperDesign jd= JRXmlLoader.load(path);
            
            String sql="select datevente,totale,benefice from vente where datevente like ('"+listevente.get(0).getDate().toString()+"') ";
            JRDesignQuery nq=new JRDesignQuery();
            nq.setText(sql);
            jd.setQuery(nq);
            
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datevente.setCellValueFactory(new PropertyValueFactory<Vente,LocalDate>("date"));
        totale.setCellValueFactory(new PropertyValueFactory<Vente,Double>("totale"));
        benefice.setCellValueFactory(new PropertyValueFactory<Vente,Double>("benefice"));

        idvente.setCellValueFactory(new PropertyValueFactory<detailvente,Integer>("idvente"));
        codeprod.setCellValueFactory(new PropertyValueFactory<detailvente,String>("codeprod"));
        prix.setCellValueFactory(new PropertyValueFactory<detailvente,Double>("prix"));
        qte.setCellValueFactory(new PropertyValueFactory<detailvente,Integer>("qte"));



    }
}
