package ua.gaponov.posterminal.forms.moneymove;

import lombok.Getter;
import ua.gaponov.posterminal.entity.Language;
import ua.gaponov.posterminal.entity.MoveType;
import ua.gaponov.posterminal.forms.inputnumbers.InputDecimalDialog;
import ua.gaponov.posterminal.forms.inputstring.InputStringDialog;

import javax.swing.*;
import java.awt.*;

import static ua.gaponov.posterminal.utils.ImagesUtils.getIcon;
import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;

/**
 * @author Andriy Gaponov
 */
@Getter
public class MoneyMoveForm extends javax.swing.JDialog {


    private double summaDoc;
    private String comment;
    private boolean ok;
    private MoveType moveType;

    public MoneyMoveForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    private MoneyMoveForm(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }


    private void setImages(){
        this.setIconImage(getImage("barcode.png"));
        okButton.setIcon(getIcon("button_ok.png"));
        cnclButton.setIcon(getIcon("button_cancel.png"));
    }

    private void clearPayTypesButtonsColor(){
        btnMoveIn.setBackground(null);
        btnMoveOut.setBackground(null);
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
        fldSum = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fldComment = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        cnclButton = new javax.swing.JButton();
        btnCash = new javax.swing.JButton();
        btnTaxi = new javax.swing.JButton();
        btnCoffe = new javax.swing.JButton();
        btnNumberForm = new javax.swing.JButton();
        btnStringForm = new javax.swing.JButton();
        btnMoveOut = new javax.swing.JButton();
        btnMoveIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Рух коштів");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Сума");

        fldSum.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Коментар");

        fldComment.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cnclButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cnclButtonActionPerformed(evt);
            }
        });

        btnCash.setText("ГОТІВКА");
        btnCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCashActionPerformed(evt);
            }
        });

        btnTaxi.setText("ТАКСІ");
        btnTaxi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaxiActionPerformed(evt);
            }
        });

        btnCoffe.setText("КАВА");
        btnCoffe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoffeActionPerformed(evt);
            }
        });

        btnNumberForm.setText("...");
        btnNumberForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumberFormActionPerformed(evt);
            }
        });

        btnStringForm.setText("...");
        btnStringForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStringFormActionPerformed(evt);
            }
        });

        btnMoveOut.setBackground(new java.awt.Color(255, 204, 204));
        btnMoveOut.setText("ВИДАЧА");
        btnMoveOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveOutActionPerformed(evt);
            }
        });

        btnMoveIn.setText("ВНЕСЕННЯ");
        btnMoveIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fldComment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStringForm, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fldSum, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNumberForm, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnMoveOut, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMoveIn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCash, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCoffe, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(fldSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNumberForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoveIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoveOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStringForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(fldComment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCash, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCoffe, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            ok = true;
            summaDoc = Double.parseDouble(fldSum.getText());
            comment = fldComment.getText();
            dispose();
        } catch (Exception ex){
            //NOP
        }

    }//GEN-LAST:event_okButtonActionPerformed

    private void cnclButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnclButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cnclButtonActionPerformed

    private void btnCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCashActionPerformed
        fldComment.setText("Здача виручки");
    }//GEN-LAST:event_btnCashActionPerformed

    private void btnTaxiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaxiActionPerformed
        fldComment.setText("Оплата таксі");
    }//GEN-LAST:event_btnTaxiActionPerformed

    private void btnCoffeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoffeActionPerformed
        fldComment.setText("Кава");
    }//GEN-LAST:event_btnCoffeActionPerformed

    private void btnMoveOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveOutActionPerformed
        moveType = MoveType.MOVE_OUT;
        clearPayTypesButtonsColor();
        btnMoveOut.setBackground(new java.awt.Color(255, 204, 204));
    }//GEN-LAST:event_btnMoveOutActionPerformed

    private void btnMoveInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveInActionPerformed
        moveType = MoveType.MOVE_IN;
        clearPayTypesButtonsColor();
        btnMoveIn.setBackground(new java.awt.Color(255, 204, 204));
    }//GEN-LAST:event_btnMoveInActionPerformed

    private void btnNumberFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumberFormActionPerformed
        InputDecimalDialog dialog = InputDecimalDialog.getNumber(this);
        dialog.setVisible(true);
        if (dialog.isOK()) {
            fldSum.setText(dialog.getNumber());
        }
    }//GEN-LAST:event_btnNumberFormActionPerformed

    private void btnStringFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStringFormActionPerformed
        InputStringDialog dialog = InputStringDialog.getDialog(this, fldComment.getText(), Language.UA);
        dialog.setVisible(true);
        if (dialog.isOk()) {
            fldComment.setText(dialog.getInputString());
        }
    }//GEN-LAST:event_btnStringFormActionPerformed

    public static MoneyMoveForm getMoneyMove(Component parent) {
        Window window = SwingUtilities.windowForComponent(parent);

        MoneyMoveForm dialog;

        if (window instanceof Frame) {
            dialog = new MoneyMoveForm((Frame) window, true);
        } else {
            dialog = new MoneyMoveForm((Dialog) window, true);
        }
        dialog.init();
        dialog.setLocationRelativeTo(null);
        dialog.applyComponentOrientation(parent.getComponentOrientation());
        return dialog;
    }

    private void init() {
        initComponents();
        setImages();
        getRootPane().setDefaultButton(okButton);
        ok = false;
        moveType = MoveType.MOVE_OUT;
        comment = "";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCash;
    private javax.swing.JButton btnCoffe;
    private javax.swing.JButton btnMoveIn;
    private javax.swing.JButton btnMoveOut;
    private javax.swing.JButton btnNumberForm;
    private javax.swing.JButton btnStringForm;
    private javax.swing.JButton btnTaxi;
    private javax.swing.JButton cnclButton;
    private javax.swing.JTextField fldComment;
    private javax.swing.JTextField fldSum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
