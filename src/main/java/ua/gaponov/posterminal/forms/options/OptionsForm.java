/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ua.gaponov.posterminal.forms.options;

import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.utils.DialogUtils;

import javax.swing.*;

/**
 * @author wmcon
 */
public class OptionsForm extends javax.swing.JFrame {


    /**
     * Creates new form OptionsForm
     */
    public OptionsForm() {
        initComponents();
        setImages();
        fillOptions();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OptionsForm frame = new OptionsForm();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    private void setImages() {
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/barcode.png")).getImage());
        cancelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/button_cancel.png")));
        okButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/button_ok.png")));
    }

    private void fillOptions() {
        fieldCurrency.setText(AppProperties.currency);
        fieldPrifixWegthBarcode.setText(AppProperties.weightItemPrefix);
        fieldIntervalExchange.setText(String.valueOf(AppProperties.exchangeInterval / 60000));
        chkExchangeEnable.setSelected(AppProperties.exchangeEnable);
        fieldShopName.setText(AppProperties.shopName);
        fieldShopAddress.setText(AppProperties.shopAddress);
        fieldShopGuid.setText(AppProperties.shopGuid);
        fieldShopId.setText(String.valueOf(AppProperties.shopId));
        fieldTerminalPort.setText(AppProperties.terminalPort);
        fieldCashRegister.setText(AppProperties.cashRegisterName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fieldCurrency = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fieldPrifixWegthBarcode = new javax.swing.JTextField();
        chkExchangeEnable = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        fieldIntervalExchange = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        fieldShopName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fieldShopAddress = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fieldShopId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        fieldShopGuid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        fieldTerminalPort = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fieldCashRegister = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Налаштування");
        setResizable(false);

        jLabel1.setText("Валюта");

        jLabel2.setText("Префікс вагового штрихкоду");

        chkExchangeEnable.setText("Обмін включений");

        jLabel3.setText("Інтервал обміну, хв");

        jLabel4.setText("Каталог обміну");

        jTextField1.setToolTipText("");

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Назва магазину");

        fieldShopName.setToolTipText("");

        jLabel6.setText("Адреса магазину");

        jLabel7.setText("ID магазину");

        fieldShopId.setToolTipText("");

        jLabel8.setText("GUID магазину");

        fieldShopGuid.setToolTipText("");

        jLabel9.setText("Порт терміналу");

        fieldTerminalPort.setText("COM1");
        fieldTerminalPort.setToolTipText("");

        jLabel10.setText("Назва каси");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkExchangeEnable)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldIntervalExchange, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(fieldPrifixWegthBarcode)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldShopAddress)
                            .addComponent(fieldShopName)
                            .addComponent(jTextField1)
                            .addComponent(fieldShopGuid)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fieldShopId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldCashRegister))
                            .addComponent(fieldTerminalPort))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(fieldPrifixWegthBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkExchangeEnable)
                    .addComponent(jLabel3)
                    .addComponent(fieldIntervalExchange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(fieldShopName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(fieldShopAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(fieldShopId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(fieldCashRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fieldShopGuid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTerminalPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            AppProperties.weightItemPrefix = fieldPrifixWegthBarcode.getText();
            AppProperties.currency = fieldCurrency.getText();
            AppProperties.exchangeInterval = Integer.parseInt(fieldIntervalExchange.getText()) * 60000;
            AppProperties.exchangeEnable = chkExchangeEnable.isSelected();
            AppProperties.shopName = fieldShopName.getText();
            AppProperties.shopAddress = fieldShopAddress.getText();
            AppProperties.shopGuid = fieldShopGuid.getText();
            AppProperties.shopId = Integer.parseInt(fieldShopId.getText());
            AppProperties.terminalPort = fieldTerminalPort.getText();
            AppProperties.cashRegisterName = fieldCashRegister.getText();
            dispose();
        } catch (Exception e){
            DialogUtils.error(this, "Помилка збереження налаштувань!");
        }

    }//GEN-LAST:event_okButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox chkExchangeEnable;
    private javax.swing.JTextField fieldCashRegister;
    private javax.swing.JTextField fieldCurrency;
    private javax.swing.JTextField fieldIntervalExchange;
    private javax.swing.JTextField fieldPrifixWegthBarcode;
    private javax.swing.JTextField fieldShopAddress;
    private javax.swing.JTextField fieldShopGuid;
    private javax.swing.JTextField fieldShopId;
    private javax.swing.JTextField fieldShopName;
    private javax.swing.JTextField fieldTerminalPort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
