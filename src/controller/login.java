package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import manipulation.outils;
import sample.BD;


import java.io.IOException;
import java.sql.*;

public class login {
    private static Connection con = BD.connect();
    private static Statement statement;

    static {
        try {
            statement = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ResultSet rs = null;
    @FXML
    private Label error;
    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwordtxt;

    @FXML
    TextField oldpass;
    @FXML
    TextField newpass;
    @FXML
    TextField username;
    @FXML
    Button ok;


    public void updatepassword(){
        username.setVisible(true);
        oldpass.setVisible(true);
        newpass.setVisible(true);
        ok.setVisible(true);
    }

    public void changer() throws SQLException {
        String sql="select password from chef where email='"+username.getText()+"'";
        rs=statement.executeQuery(sql);

        if(rs.next()){
            if(oldpass.getText().equals(rs.getString("password"))){
                PreparedStatement pr=con.prepareStatement("update chef set password='"+newpass.getText()+"' where email='"+username.getText()+"'");
                pr.execute();
                username.setVisible(false);
                oldpass.setVisible(false);
                newpass.setVisible(false);
                ok.setVisible(false);
                error.setText("");
            }
            else{
                error.setText("password incorret");
                username.setText("");
                oldpass.setText("");
                newpass.setText("");
            }

        }else{
            error.setText("user n'existe pas");
            username.setText("");
            oldpass.setText("");
            newpass.setText("");
        }
    }


    public void getlogin(ActionEvent actionEvent) throws SQLException, IOException {
        error.setText("");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");

        String username = usernametxt.getText();
        String password = passwordtxt.getText();


        String sql = "select * from chef";
        rs = statement.executeQuery(sql);

        while (rs.next()) {
            if (!rs.getString("email").equals(username)) {
                error.setText("user n'existe pas");
            } else {
                sql = "select * from chef where email='"+username+"'";
                rs = statement.executeQuery(sql);
                while (rs.next()) {
                    if (!rs.getString("password").equals(password)) {
                        error.setText("password incorret");
                        passwordtxt.setText("");
                    } else {

                        outils.loadp(actionEvent, "Accueil", "/sample/menu1.fxml");


                    }
                }
            }
        }
    }
}
