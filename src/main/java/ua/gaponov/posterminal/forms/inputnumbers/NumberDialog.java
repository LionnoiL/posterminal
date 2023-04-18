package ua.gaponov.posterminal.forms.inputnumbers;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.*;

/**
 *
 * @author wmcon
 */
public class NumberDialog extends javax.swing.JDialog {

    private String number;
    private boolean ok;
    /**
     * Creates new form NumberDialog
     */
    public NumberDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    private NumberDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }

    private void setImages(){
        btnNumpadC.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btnce.png")));
        btnNumpadCancel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btnback.png")));
        btnNumpad0.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn0.png")));
        btnNumpad1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn1.png")));
        btnNumpad2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn2.png")));
        btnNumpad3.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn3.png")));
        btnNumpad4.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn4.png")));
        btnNumpad5.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn5.png")));
        btnNumpad6.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn6.png")));
        btnNumpad7.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn7.png")));
        btnNumpad9.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn9.png")));
        btnNumpad8.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/btn8.png")));
        okButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/button_ok.png")));
        cnclButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/button_cancel.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cnclButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        btnNumpad7 = new javax.swing.JButton();
        btnNumpad8 = new javax.swing.JButton();
        btnNumpad9 = new javax.swing.JButton();
        btnNumpad4 = new javax.swing.JButton();
        btnNumpad5 = new javax.swing.JButton();
        btnNumpad6 = new javax.swing.JButton();
        btnNumpad1 = new javax.swing.JButton();
        btnNumpad2 = new javax.swing.JButton();
        btnNumpad3 = new javax.swing.JButton();
        btnNumpad0 = new javax.swing.JButton();
        btnNumpadCancel = new javax.swing.JButton();
        btnNumpadC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setToolTipText("");

        btnNumpad7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad7ActionPerformed(evt);
            }
        });

        btnNumpad8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad8ActionPerformed(evt);
            }
        });

        btnNumpad9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad9ActionPerformed(evt);
            }
        });

        btnNumpad4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad4ActionPerformed(evt);
            }
        });

        btnNumpad5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad5ActionPerformed(evt);
            }
        });

        btnNumpad6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad6ActionPerformed(evt);
            }
        });

        btnNumpad1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad1ActionPerformed(evt);
            }
        });

        btnNumpad2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad2ActionPerformed(evt);
            }
        });

        btnNumpad3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad3ActionPerformed(evt);
            }
        });

        btnNumpad0.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad0ActionPerformed(evt);
            }
        });

        btnNumpadCancel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpadCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpadCancelActionPerformed(evt);
            }
        });

        btnNumpadC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpadCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNumpad1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNumpad4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNumpad7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNumpad0, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpadC, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpadCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNumpad0, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadC, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cnclButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnclButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cnclButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        ok = true;
        number = jTextField1.getText();
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void btnNumpad7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad7ActionPerformed
        addDigitToQtyField("7");
    }//GEN-LAST:event_btnNumpad7ActionPerformed

    private void btnNumpad8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad8ActionPerformed
        addDigitToQtyField("8");
    }//GEN-LAST:event_btnNumpad8ActionPerformed

    private void btnNumpad9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad9ActionPerformed
        addDigitToQtyField("9");
    }//GEN-LAST:event_btnNumpad9ActionPerformed

    private void btnNumpad4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad4ActionPerformed
        addDigitToQtyField("4");
    }//GEN-LAST:event_btnNumpad4ActionPerformed

    private void btnNumpad5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad5ActionPerformed
        addDigitToQtyField("5");
    }//GEN-LAST:event_btnNumpad5ActionPerformed

    private void btnNumpad6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad6ActionPerformed
        addDigitToQtyField("6");
    }//GEN-LAST:event_btnNumpad6ActionPerformed

    private void btnNumpad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad1ActionPerformed
        addDigitToQtyField("1");
    }//GEN-LAST:event_btnNumpad1ActionPerformed

    private void btnNumpad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad2ActionPerformed
        addDigitToQtyField("2");
    }//GEN-LAST:event_btnNumpad2ActionPerformed

    private void btnNumpad3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad3ActionPerformed
        addDigitToQtyField("3");
    }//GEN-LAST:event_btnNumpad3ActionPerformed

    private void btnNumpad0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad0ActionPerformed
        addDigitToQtyField("0");
    }//GEN-LAST:event_btnNumpad0ActionPerformed

    private void btnNumpadCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCancelActionPerformed
        deleteLastDigitInQtyField();
    }//GEN-LAST:event_btnNumpadCancelActionPerformed

    private void btnNumpadCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCActionPerformed
        jTextField1.setText("");
    }//GEN-LAST:event_btnNumpadCActionPerformed

    private void addDigitToQtyField(String digit){
        jTextField1.setText(jTextField1.getText()+digit);
    }

    private void deleteLastDigitInQtyField(){
        if (jTextField1.getText().length()>0){
            jTextField1.setText(jTextField1.getText().substring(0, jTextField1.getText().length() - 1));
        }
    }

    public static NumberDialog getNumber(Component parent) {
        Window window = SwingUtilities.windowForComponent(parent);

        NumberDialog dialog;
        if (window instanceof Frame) {
            dialog = new NumberDialog((Frame) window, true);
        } else {
            dialog = new NumberDialog((Dialog) window, true);
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
        number = null;
    }

    public boolean isOK() {
        return ok;
    }

    public String getNumber() {
        return number;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNumpad0;
    private javax.swing.JButton btnNumpad1;
    private javax.swing.JButton btnNumpad2;
    private javax.swing.JButton btnNumpad3;
    private javax.swing.JButton btnNumpad4;
    private javax.swing.JButton btnNumpad5;
    private javax.swing.JButton btnNumpad6;
    private javax.swing.JButton btnNumpad7;
    private javax.swing.JButton btnNumpad8;
    private javax.swing.JButton btnNumpad9;
    private javax.swing.JButton btnNumpadC;
    private javax.swing.JButton btnNumpadCancel;
    private javax.swing.JButton cnclButton;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
