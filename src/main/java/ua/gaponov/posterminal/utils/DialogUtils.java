/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.gaponov.posterminal.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author wmcon
 */
public class DialogUtils {
    
    public static int okcancel(JFrame parent, String title, String theMessage) {
        int result = JOptionPane.showConfirmDialog(parent, theMessage,
                title, JOptionPane.OK_CANCEL_OPTION);
        return result;
    }
    
    public static void ok(JFrame parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }
}
