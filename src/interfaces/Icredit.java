/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;


import classes.credit;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public interface Icredit {
    public boolean ajoutercredit();
    public ObservableList<credit> listecredit()throws SQLException;
    public boolean modifiecredit();
    public boolean suppcredit();
    
}
