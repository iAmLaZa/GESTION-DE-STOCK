package controller;

import classes.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manipulation.outils;
import sample.BD;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class menu  implements Initializable {
    private static Connection con = BD.connect();
    int i;
    @FXML
    Button menubtnproduit;
    @FXML
    Button menubtncaisse;
    @FXML
    Button menubtnfournisseur;
    @FXML
    Button menubtnstat;
    @FXML
    Button menucredit;
    @FXML
    Button logout;

    @FXML
    AnchorPane anchorprod;
    @FXML
    AnchorPane anchorcredit;

    @FXML
    AnchorPane anchorfournisseur;

    @FXML
    AnchorPane anchorstat;

    @FXML
    AnchorPane anchorstatcode;
    @FXML
    AnchorPane anchorprodcode;

    @FXML
    AnchorPane anchorcaisse;
    public void menuprod(){
        anchorprod.setVisible(false);
        anchorfournisseur.setVisible(false);
        anchorcaisse.setVisible(false);
        anchorstat.setVisible(false);
        anchorstatcode.setVisible(false);
        anchorprodcode.setVisible(true);
        anchorcredit.setVisible(false);
        

        Produit p = new Produit();
        try {
            listeprod = p.listeprod();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableprod.setItems(listeprod);
    }
    public void logout(){
        Stage stage=(Stage) logout.getScene().getWindow();
        stage.close();
    }

    @FXML
    TableView<Produit> tableprod;
    @FXML
    TableColumn<Produit, String> codeprod;
    @FXML
    TableColumn<Produit, Double> prixachat;
    @FXML
    TableColumn<Produit, Double> prixvente;
    @FXML
    TableColumn<Produit, String> nomprod;
    @FXML
    TableColumn<Produit,Integer> qteprod;
    ObservableList<Produit> listeprod;

    @FXML
    TextField search;

    public void searchprod() {
        outils.searchglobale(search, listeprod, tableprod);
    }
    public void addprod() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/addprod.fxml"));
        Parent root= loader.load();

        addprod addprod=loader.getController();
        addprod.showinformation(listeprod);
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Ajouter un produit");

        stage.show();
    }

    public void infoprod() throws IOException {
        search.setText("");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/addprod.fxml"));
        Parent root= loader.load();
        Produit pr=tableprod.getSelectionModel().getSelectedItem();
        i=listeprod.indexOf(pr);
        addprod addprod=loader.getController();
        addprod.updateinformation(listeprod,i);
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("modifie/supprimer un produit");

        stage.show();
    }
    public void menucredit(){
        anchorprod.setVisible(false);
        anchorfournisseur.setVisible(false);
        anchorcaisse.setVisible(false);
        anchorstat.setVisible(false);
        anchorstatcode.setVisible(false);
        anchorcredit.setVisible(true);
        anchorprodcode.setVisible(false);
    }
    @FXML
    TableView<credit> tablecredit;
    @FXML
    TableColumn<credit,String> nomcredit;
    @FXML
    TableColumn<credit,String> prenomcredit;
    @FXML
    TableColumn<credit,Double> prixcredit;
    
    @FXML
    TextField searchcredit;
    
    ObservableList<credit> listecredit;
    public void searchcredit() {
        outils.searchglobale(searchcredit, listecredit , tablecredit);
    }
    @FXML
    TextField nomtxt,prenomtxt,prixajout;
    @FXML
    Button ajoutercredit;
    public void addcredit(){
        nomtxt.setVisible(true);
        prenomtxt.setVisible(true);
        prixajout.setVisible(true);
        ajoutercredit.setVisible(true);
        modifiecredit.setVisible(false);
        supcredit.setVisible(false);
        credittxt.setVisible(false);
        prixcreditm.setVisible(false);
    }
    public void ajoutercredit(){
        credit crd=new credit(nomtxt.getText(),prenomtxt.getText(),Double.valueOf(prixajout.getText()));
        crd.ajoutercredit();
        listecredit.add(crd);
        nomtxt.setText("");
        prenomtxt.setText("");
        prixajout.setText("");
        nomtxt.setVisible(false);
        prenomtxt.setVisible(false);
        prixajout.setVisible(false);
        ajoutercredit.setVisible(false);
    }
    @FXML
    Label credittxt;
    @FXML
    TextField prixcreditm;
    @FXML
    Button modifiecredit,supcredit;
    credit c;
    public void infocredit(){
        c=tablecredit.getSelectionModel().getSelectedItem();
        i=listecredit.indexOf(c);
        
       // c=listecredit.get(i);
        modifiecredit.setVisible(true);
        supcredit.setVisible(true);
        credittxt.setVisible(true);
        prixcreditm.setVisible(true);
        nomtxt.setVisible(false);
        prenomtxt.setVisible(false);
        prixajout.setVisible(false);
        ajoutercredit.setVisible(false);
    }
    public void supcredit(){
        
         c.suppcredit();
         listecredit.remove(i);
         modifiecredit.setVisible(false);
        supcredit.setVisible(false);
        credittxt.setVisible(false);
        prixcreditm.setVisible(false);
        searchcredit.setText("");
    }
    public void modifiecredit(){
        
        c.setPrix(c.getPrix()-Double.valueOf(prixcreditm.getText()));
        if(c.getPrix()==0){
            c.suppcredit();
            listecredit.remove(i);
        }
        else{
            c.modifiecredit();
            listecredit.set(i, c);
        }
        modifiecredit.setVisible(false);
        supcredit.setVisible(false);
        credittxt.setVisible(false);
        prixcreditm.setText("");
        prixcreditm.setVisible(false);
        searchcredit.setText("");
    }
    
    
    public void menucaisse(){
        anchorprod.setVisible(false);
        anchorfournisseur.setVisible(false);
        anchorcaisse.setVisible(true);
        anchorstat.setVisible(false);
        anchorstatcode.setVisible(false);
        anchorcredit.setVisible(false);
         anchorprodcode.setVisible(false);

        Produit p = new Produit();
        try {
            listeprod = p.listeprod();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tablecaisseprod.setItems(listeprod);


    }

    @FXML
    TableView<Produit> tablecaisseprod;
    @FXML
    TableColumn<Produit,String> codeprodcaisse;
    @FXML
    TableColumn<Produit,String> nomprodcaisse;
    @FXML
    TableColumn<Produit,Double> prixprodcaisse;
    @FXML
    TextField searchprodcaisse;
    public void searchprodcaisse() {
        outils.searchglobale(searchprodcaisse,listeprod, tablecaisseprod);
    }

    public void scanbarecode() throws SQLException {
        PreparedStatement pr=con.prepareStatement("select * from produit where codeprod= ?");
        pr.setString(1,searchprodcaisse.getText());
        ResultSet rs=pr.executeQuery();
        if(rs.next()){
            Produit p=new Produit(rs.getString("codeprod"),rs.getString("nomprod"),rs.getInt("qte"),rs.getDouble("prixachat"),rs.getDouble("prixvente"));
            choixprix.setText(String.valueOf(p.getPrixvente()));
            choixqte.setText("1");
            prod=p.getCodeprod();
        }
    }



    @FXML
    TextField choixprix;
    @FXML
    TextField choixqte;

    String prod;
    String nomp;

    public void infoprodcaisse(){
        Produit b=tablecaisseprod.getSelectionModel().getSelectedItem();
        prod=b.getCodeprod();
        nomp=b.getNomprod();
        choixprix.setText(String.valueOf(b.getPrixvente()));
        choixqte.setText(String.valueOf(1));
        
    }
    
    public void reset() throws SQLException{
        Vente v=new Vente();
        prod="";
        nomp="";
        choixqte.setText("");
        choixprix.setText("");
        tabledetailvente.setItems(listedetailvente=v.listedetailvente(0));
        totalevente.setText("00");
        adddv.setVisible(true);
        modifiedv.setVisible(false);
        supdv.setVisible(false);
        totale=0.0;
        benefice=0.0;
        
    }

    @FXML
    TableView<detailvente> tabledetailvente;
    @FXML
    TableColumn<detailvente,String> codeproddetailvente;
    @FXML
    TableColumn<detailvente,Integer> qtedetailvente;
    @FXML
    TableColumn<detailvente,Double> prixdetailvente;

    @FXML
     Label totalevente;

    ObservableList<detailvente> listedetailvente;
    Double totale=0.0,benefice=0.0;

    public void ajouterdetailvente() throws SQLException {
        Produit p=new Produit();
        int qteprod=p.qteprod(prod);
        if(qteprod==0){
            outils.showerroronmessage("تحذير", "كمية المنتوج معدومة");
        }
        else{
            if(qteprod>=Integer.valueOf(choixqte.getText())){
                listedetailvente.add(new detailvente(prod,Double.valueOf(choixprix.getText()),Integer.valueOf(choixqte.getText())));
                totale+=Integer.valueOf(choixqte.getText())*Double.valueOf(choixprix.getText());
                totalevente.setText(String.valueOf(totale));
                benefice+=(Double.valueOf(choixprix.getText())-p.prixachat(prod))*Integer.valueOf(choixqte.getText());
                if((qteprod-Integer.valueOf(choixqte.getText()))<10)
                outils.showerroronmessage("تحذير",nomp+"\n"+ "كميته اقل من  10 ");
                choixqte.setText("");
                choixprix.setText("");
                searchprodcaisse.setText(""); 
            }
            else{
                outils.showerroronmessage("تحذير",  "كمية المنتوج غير متوفرة");
            }
           
            
        
        }
    }
    @FXML
    Button modifiedv,supdv,adddv;
    detailvente dv;
    public void infovente(){
        i=tabledetailvente.getSelectionModel().getFocusedIndex();
        dv=listedetailvente.get(i);
        choixprix.setText(String.valueOf(dv.getPrix()));
        choixprix.setEditable(false);
        choixqte.setText(String.valueOf(dv.getQte()));
        modifiedv.setVisible(true);
        supdv.setVisible(true);
        adddv.setVisible(false);
        
    }
    public void modifiedv() throws SQLException{
        Produit p=new Produit();
        
        totale+=(Integer.valueOf(choixqte.getText())-dv.getQte())*dv.getPrix();
        totalevente.setText(String.valueOf(totale));
        benefice+=((dv.getPrix())-p.prixachat(dv.getCodeprod()))*(Integer.valueOf(choixqte.getText())-dv.getQte());
        dv.setQte(Integer.valueOf(choixqte.getText()));
        listedetailvente.set(i, dv);
        choixprix.setText("");
        choixqte.setText("");
        modifiedv.setVisible(false);
        supdv.setVisible(false);
        adddv.setVisible(true);
    }
    public void supdv() throws SQLException{
          Produit p=new Produit();
        
        totale-=dv.getQte()*dv.getPrix();
        totalevente.setText(String.valueOf(totale));
        benefice-=((dv.getPrix())-p.prixachat(dv.getCodeprod()))*(dv.getQte());
        listedetailvente.remove(i);
        choixprix.setText("");
        choixqte.setText("");
        modifiedv.setVisible(false);
        supdv.setVisible(false);
        adddv.setVisible(true);
    }

    public void ajouterventecaisse() throws SQLException {
        Produit p=new Produit();
        Vente v=new Vente();
        v.setTotale(totale);
        v.setBenefice(benefice);
        totale=0.0;benefice=0.0;
        v.ajoutervente();
        int id=p.lastid();
        for(int i=0;i<listedetailvente.size();i++){
            listedetailvente.get(i).setIdvente(id);
            listedetailvente.get(i).ajouterdetvente();
            p.updateqte(listedetailvente.get(i).getCodeprod(),(listedetailvente.get(i).getQte())*-1);
        }
        tabledetailvente.setItems(listedetailvente=v.listedetailvente(0));
        totalevente.setText("00");

    }


    public void menufournisseur(){
        anchorprod.setVisible(false);
        anchorfournisseur.setVisible(true);
        anchorcaisse.setVisible(false);
        anchorstat.setVisible(false);
        anchorstatcode.setVisible(false);
        anchorcredit.setVisible(false);
        anchorprodcode.setVisible(false);

    }
    @FXML
    TextField searchliv;
    public void searchliv(){
        outils.searchglobale(searchliv,listeliv,tableliv);
    }

    @FXML
    TableView<LivRetrait> tableliv;
    @FXML
    TableColumn<LivRetrait,Integer> idliv;
    @FXML
    TableColumn<LivRetrait, LocalDate> dateliv;
    @FXML
    TableColumn<LivRetrait,Double> prixliv;

    ObservableList<LivRetrait> listeliv;

    @FXML
    TableView<detailliv> tabledetailliv;
    @FXML
    TableColumn<detailliv,Integer> qtedetail;
    @FXML
    TableColumn<detailliv,String> codeproddetail;
    @FXML
    TableColumn<detailliv,String> nomproddetail;

    ObservableList<detailliv> listedetailliv;

    public void ajouterliv() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/addlivraison.fxml"));
        Parent root= loader.load();
        addliv addliv=loader.getController();
        addliv.showinformation(listeliv);
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("ajouter livraison");
        stage.show();

    }


    @FXML
    Button Ajouterliv;


    public void infoliv() throws SQLException {
         LivRetrait lr=tableliv.getSelectionModel().getSelectedItem();
         detailliv d=new detailliv(lr.getId());
         listedetailliv=d.listedetliv();
         tabledetailliv.setItems(listedetailliv);

    }




    public void menustat(){
        anchorprod.setVisible(false);
        anchorfournisseur.setVisible(false);
        anchorcaisse.setVisible(false);
        anchorstat.setVisible(false);
        anchorstatcode.setVisible(true);
        anchorcredit.setVisible(false);
        anchorprodcode.setVisible(false);

        Vente v=new Vente();

        try {
            nbvente.setText("Nombre De Vente :"+"\n"+v.nbvente("%-%-%"));
            beneficetxt.setText("Bénéfices :"+"\n"+v.bene("%-%-%"));
            totaletxt.setText("Totale :"+"\n"+v.Totale("%-%-%"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    Label nbvente;
    @FXML
    Label totaletxt;
    @FXML
    Label beneficetxt;
    @FXML
    Label motdepassincorrect;
    @FXML
    TextField passwordstat;
    @FXML
    TextField passwordprod;

    public void statlogin() throws SQLException {

        if(passwordstat.getText().equals(outils.getCode())) {
            anchorstatcode.setVisible(false);
            anchorstat.setVisible(true);
            motdepassincorrect.setVisible(false);
        }
        else{
            motdepassincorrect.setVisible(true);
        }
        passwordstat.setText("");


    }
      public void prodlogin() throws SQLException {

        if(passwordprod.getText().equals(outils.getCode())) {
            anchorprodcode.setVisible(false);
            anchorprod.setVisible(true);
            
        }
        else{
           
        }
        passwordprod.setText("");


    }
    @FXML
    TextField oldpass,newpass;
    @FXML
    Button ok;
    public void changermotpass(){
        oldpass.setVisible(true);
        newpass.setVisible(true);
        ok.setVisible(true);
        motdepassincorrect.setVisible(false);
        passwordstat.setText("");

    }
    public void changer() throws SQLException {
        if(oldpass.getText().equals(outils.getCode())) {
            outils.setCode(newpass.getText());
            oldpass.setText("");
            newpass.setText("");
            oldpass.setVisible(false);
            newpass.setVisible(false);
            ok.setVisible(false);
            motdepassincorrect.setVisible(false);
        }
        else {
            motdepassincorrect.setVisible(true);
            oldpass.setText("");
            newpass.setText("");
        }
    }



    public void parjour() throws IOException {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/statjour.fxml"));
        Parent root= loader.load();
        statjour statjour=loader.getController();
        statjour.showinformation("0000-00-00","menu");
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("stat de jour");
        stage.show();
    }
    public void parmois() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/statmois.fxml"));
        Parent root= loader.load();
        statmois statmois=loader.getController();
        statmois.showinformation("0","0","menu");
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("stat de jour");
        stage.show();

    }
    public void parannees() throws IOException {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/statannees.fxml"));
        Parent root= loader.load();
        statannees statannees=loader.getController();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("stat d'Années");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        codeprod.setCellValueFactory(new PropertyValueFactory<Produit, String>("codeprod"));
        prixachat.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixachat"));
        prixvente.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixvente"));
        nomprod.setCellValueFactory(new PropertyValueFactory<Produit, String>("nomprod"));
        qteprod.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qte"));



        codeprodcaisse.setCellValueFactory(new PropertyValueFactory<Produit, String>("codeprod"));
        nomprodcaisse.setCellValueFactory(new PropertyValueFactory<Produit, String>("nomprod"));
        prixprodcaisse.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prixvente"));
        
        
        
        nomcredit.setCellValueFactory(new PropertyValueFactory<credit, String>("nom"));
        prenomcredit.setCellValueFactory(new PropertyValueFactory<credit, String>("prenom"));
        prixcredit.setCellValueFactory(new PropertyValueFactory<credit,Double>("prix"));
        credit c=new credit();
        try{
            listecredit=c.listecredit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tablecredit.setItems(listecredit);
        
        
        Produit p = new Produit();
        try {
            listeprod = p.listeprod();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableprod.setItems(listeprod);
        tablecaisseprod.setItems(listeprod);


        idliv.setCellValueFactory(new PropertyValueFactory<LivRetrait,Integer>("id"));
        dateliv.setCellValueFactory(new PropertyValueFactory<LivRetrait,LocalDate>("date"));
        prixliv.setCellValueFactory(new PropertyValueFactory<LivRetrait,Double>("prix"));
        LivRetrait livRetrait=new LivRetrait();
        try{
            listeliv=livRetrait.listeliv();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableliv.setItems(listeliv);


        codeproddetail.setCellValueFactory(new PropertyValueFactory<detailliv,String>("codeprod"));
        nomproddetail.setCellValueFactory(new PropertyValueFactory<detailliv,String>("nomprod"));
        qtedetail.setCellValueFactory(new PropertyValueFactory<detailliv,Integer>("qte"));
        detailliv detailliv =new detailliv();
        try {
            listedetailliv=detailliv.listedetliv();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tabledetailliv.setItems(listedetailliv);


        codeproddetailvente.setCellValueFactory(new PropertyValueFactory<detailvente,String>("codeprod"));
        qtedetailvente.setCellValueFactory(new PropertyValueFactory<detailvente,Integer>("qte"));
        prixdetailvente.setCellValueFactory(new PropertyValueFactory<detailvente,Double>("prix"));


        Vente v=new Vente();
        try{

            listedetailvente=v.listedetailvente(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tabledetailvente.setItems(listedetailvente);


        try {
            nbvente.setText(nbvente.getText()+"\n"+v.nbvente("%-%-%"));
            beneficetxt.setText(beneficetxt.getText()+"\n"+v.bene("%-%-%"));
            totaletxt.setText(totaletxt.getText()+"\n"+v.Totale("%-%-%"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




    }
}