/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bicikliprojekt.main;
import com.mycompany.bicikliprojekt.SwingUI.MainWindow;
import javax.swing.SwingUtilities;

/**
 *
 * @author Denes
 */
public class Bicikliprojekt {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
