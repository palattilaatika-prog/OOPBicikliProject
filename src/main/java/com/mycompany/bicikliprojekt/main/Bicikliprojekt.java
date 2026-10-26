/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bicikliprojekt.main;
import com.mycompany.bicikliprojekt.DataBase.DataBase;
import com.mycompany.bicikliprojekt.DataBase.DataBaseManager;
import com.mycompany.bicikliprojekt.SwingUI.LoginDialog;

import com.mycompany.bicikliprojekt.SwingUI.MainWindow;
import javax.swing.SwingUtilities;

/**
 *
 * @author Denes
 */
public class Bicikliprojekt {

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
        DataBase.createTables();
        DataBaseManager manager = new DataBaseManager();
        manager.insertTestBikes();
        manager.correctTestBikeImageExtensions();

        LoginDialog loginDialog = new LoginDialog(null);

        if (loginDialog.showDialog()) {
            new MainWindow().setVisible(true);
        } else {
            System.exit(0);
        }
        });
    }
}
