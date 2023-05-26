package controller;

import classes.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sample.BD;

public class statmois implements Initializable {
    
    String Annees,window,Mois;
    public void showinformation(String m,String a,String w){
        Annees=a;
        Mois=m;
        if(Integer.valueOf(Mois)<10)Mois="0"+Mois;
        window=w;
        if(!window.equals("menu")){
            moisvente.setVisible(false);
            annees.setVisible(false);
            valider.setVisible(false);
            Vente v=new Vente();
            try{
                listevente=v.listeventemios((Annees),Mois);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            tablestatmois.setItems(listevente);

            try {
                nbvente.setText(nbvente.getText()+"\n"+v.nbvente(Annees+"-"+Mois+"-%"));
                beneficetxt.setText(beneficetxt.getText()+"\n"+v.bene(Annees+"-"+Mois+"-%"));
                totaletxt.setText(totaletxt.getText()+"\n"+v.Totale(Annees+"-"+Mois+"-%"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    @FXML
    Button valider;
    @FXML
    TableView<Vente> tablestatmois;
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
    @FXML
    ChoiceBox<String> moisvente;
    @FXML
    TextField annees;

    public void valider(){
        Vente v=new Vente();
        try{

            listevente=v.listeventemios((annees.getText()),moisvente.getValue());
            Annees=annees.getText();
            Mois=moisvente.getValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablestatmois.setItems(listevente);

        try {
            nbvente.setText("Nombre De Vente :"+"\n"+v.nbvente(annees.getText()+"-"+String.valueOf(moisvente.getValue())+"-%"));
            beneficetxt.setText("Bénéfices :"+"\n"+v.bene(annees.getText()+"-"+String.valueOf(moisvente.getValue())+"-%"));
            totaletxt.setText("Totale :"+"\n"+v.Totale(annees.getText()+"-"+String.valueOf(moisvente.getValue())+"-%"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void infojour() throws IOException {
        int i=tablestatmois.getSelectionModel().getFocusedIndex();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/statjour.fxml"));
        Parent root= loader.load();
        statjour statjour=loader.getController();
        statjour.showinformation(listevente.get(i).getDate().toString(),"mois");
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("stat de jour");
        stage.show();
    }
    
     private static Connection con = BD.connect();
    public void stat()  {
    try {

            InputStream path= getClass().getResourceAsStream("/sample/salim.jrxml");
            JasperDesign jd= JRXmlLoader.load(path);
            
            String sql="select datevente,SUM(totale) as totale,SUM(benefice) as benefice from vente where datevente like ('"+Integer.valueOf(Annees)+"-"+Mois+"-%')  group by day(datevente)";
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

        ObservableList l= FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
        moisvente.setItems(l);

    }
}
