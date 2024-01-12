package ua.gaponov.posterminal.forms.pay;

import ua.gaponov.posterminal.devices.terminal.Terminal;
import ua.gaponov.posterminal.devices.terminal.ingenico.IngenicoTerminal;
import ua.gaponov.posterminal.entity.orders.Order;

import javax.swing.*;
import java.awt.*;

import static ua.gaponov.posterminal.utils.ImagesUtils.getIcon;
import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;


/**
 * @author Andriy Gaponov
 */
public class ChoiseCardMerchForm extends javax.swing.JDialog {

    private int merchId;
    private boolean ok;
    private double summa;
    private Order order;

    /**
     * Creates new form ChoiseCardMerchForm
     */
    public ChoiseCardMerchForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    public ChoiseCardMerchForm(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private void setImages(){
        this.setIconImage(getImage("barcode.png"));
        cnclButton.setIcon(getIcon("button_cancel.png"));
        firstMerch.setIcon(getIcon("btn1.png"));
        secondMerch.setIcon(getIcon("btn2.png"));
        thirdMerch.setIcon(getIcon("btn3.png"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cnclButton = new javax.swing.JButton();
        firstMerch = new javax.swing.JButton();
        secondMerch = new javax.swing.JButton();
        thirdMerch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        cnclButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cnclButtonActionPerformed(evt);
            }
        });

        firstMerch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstMerchActionPerformed(evt);
            }
        });

        secondMerch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondMerchActionPerformed(evt);
            }
        });

        thirdMerch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirdMerchActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Оберіть мерчанта");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(firstMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secondMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(thirdMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(thirdMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstMerch, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cnclButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnclButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cnclButtonActionPerformed

    private void firstMerchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstMerchActionPerformed
        merchId = 1;
        order.setMerchId(merchId);
        payOnTerminal();
    }//GEN-LAST:event_firstMerchActionPerformed

    private void secondMerchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondMerchActionPerformed
        merchId = 2;
        order.setMerchId(merchId);
        payOnTerminal();
    }//GEN-LAST:event_secondMerchActionPerformed

    private void thirdMerchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirdMerchActionPerformed
        merchId = 3;
        order.setMerchId(merchId);
        payOnTerminal();
    }//GEN-LAST:event_thirdMerchActionPerformed

    public static ChoiseCardMerchForm getPay(Component parent, double summa, Order order) {
        Window window = SwingUtilities.windowForComponent(parent);

        ChoiseCardMerchForm dialog;

        if (window instanceof Frame) {
            dialog = new ChoiseCardMerchForm((Frame) window, true);
        } else {
            dialog = new ChoiseCardMerchForm((Dialog) window, true);
        }
        dialog.summa = summa;
        dialog.order = order;
        dialog.init();
        dialog.setLocationRelativeTo(null);
        dialog.applyComponentOrientation(parent.getComponentOrientation());
        return dialog;
    }

    private void init() {
        initComponents();
        setImages();
        getRootPane().setDefaultButton(cnclButton);
        ok = false;
    }

    public boolean isOK() {
        return ok;
    }


    private void payOnTerminal(){
        Terminal terminal = new IngenicoTerminal();
        ok = terminal.pay(merchId, summa, order);
        if (!ok){
            order.setMerchId(0);
        }
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cnclButton;
    private javax.swing.JButton firstMerch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton secondMerch;
    private javax.swing.JButton thirdMerch;
    // End of variables declaration//GEN-END:variables
}
