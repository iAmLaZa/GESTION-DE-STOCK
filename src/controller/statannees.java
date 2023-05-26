package controller;

import classes.Vente;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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


public class statannees  implements Initializable {

    @FXML
    Button valider;
    @FXML
    TableView<Vente> tablestatannees;
    @FXML
    TableColumn<Vente, String> datevente;
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
    TextField annees;

    public void valider(){
        Vente v=new Vente();
        try{

            listevente=v.listeventeannees((annees.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablestatannees.setItems(listevente);

        try {
            nbvente.setText("Nombre De Vente :"+"\n"+v.nbvente(annees.getText()+"-%-%"));
            beneficetxt.setText("Bénéfices :"+"\n"+v.bene(annees.getText()+"-%-%"));
            totaletxt.setText("Totale :"+"\n"+v.Totale(annees.getText()+"-%-%"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void infomonth() throws IOException {
        int i=tablestatannees.getSelectionModel().getFocusedIndex();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/statmois.fxml"));
        Parent root= loader.load();
        statmois statmois=loader.getController();
        statmois.showinformation(String.valueOf(listevente.get(i).getDate().getMonthValue()), annees.getText(), "annees");
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("stat de mois");
        stage.show();
    }
    
         private static Connection con = BD.connect();
    public void stat()  {
    try {

           InputStream path= getClass().getResourceAsStream("/sample/salim.jrxml");
            JasperDesign jd= JRXmlLoader.load(path);
            
            String sql="select datevente,SUM(totale) as totale,SUM(benefice) as benefice from vente where datevente like  ('"+Integer.valueOf(annees.getText())+"-%-%')  group by month(datevente)";
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
        datevente.setCellValueFactory(new PropertyValueFactory<Vente,String>("month"));
        totale.setCellValueFactory(new PropertyValueFactory<Vente,Double>("totale"));
        benefice.setCellValueFactory(new PropertyValueFactory<Vente,Double>("benefice"));
    }
}
