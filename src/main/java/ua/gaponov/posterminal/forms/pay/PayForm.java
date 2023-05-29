package ua.gaponov.posterminal.forms.pay;

import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.utils.RoundUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static ua.gaponov.posterminal.utils.ImagesUtils.getIcon;
import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;

/**
 * @author Andriy Gaponov
 */
public class PayForm extends javax.swing.JDialog {

    private double summaPay;
    private PayTypes payType = PayTypes.CASH;

    private boolean ok;
    private boolean printFiscal;

    private Order order;
    private boolean edit = false;

    public PayForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    private PayForm(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PayTypes getPayType() {
        return payType;
    }

    public void setPayType(PayTypes payType) {
        this.payType = payType;
    }

    private void setImages(){
        this.setIconImage(getImage("barcode.png"));
        btnNumpadC.setIcon(getIcon("btndot.png"));
        btnNumpadCancel.setIcon(getIcon("btnback.png"));
        btnNumpad0.setIcon(getIcon("btn0.png"));
        btnNumpad1.setIcon(getIcon("btn1.png"));
        btnNumpad2.setIcon(getIcon("btn2.png"));
        btnNumpad3.setIcon(getIcon("btn3.png"));
        btnNumpad4.setIcon(getIcon("btn4.png"));
        btnNumpad5.setIcon(getIcon("btn5.png"));
        btnNumpad6.setIcon(getIcon("btn6.png"));
        btnNumpad7.setIcon(getIcon("btn7.png"));
        btnNumpad9.setIcon(getIcon("btn9.png"));
        btnNumpad8.setIcon(getIcon("btn8.png"));
        okButton.setIcon(getIcon("button_ok.png"));
        cnclButton.setIcon(getIcon("button_cancel.png"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cashButton = new javax.swing.JButton();
        cnclButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        cardButton = new javax.swing.JButton();
        onlineButton = new javax.swing.JButton();
        chkFiscalPrint = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnNumpad7 = new javax.swing.JButton();
        btnNumpad4 = new javax.swing.JButton();
        btnNumpad1 = new javax.swing.JButton();
        btnNumpad0 = new javax.swing.JButton();
        btnNumpad2 = new javax.swing.JButton();
        btnNumpad5 = new javax.swing.JButton();
        btnNumpad8 = new javax.swing.JButton();
        btnNumpad9 = new javax.swing.JButton();
        btnNumpad6 = new javax.swing.JButton();
        btnNumpad3 = new javax.swing.JButton();
        btnNumpadCancel = new javax.swing.JButton();
        btnNumpadC = new javax.swing.JButton();
        btnNumpad10 = new javax.swing.JButton();
        btnNumpad11 = new javax.swing.JButton();
        btnNumpad12 = new javax.swing.JButton();
        btnNumpad13 = new javax.swing.JButton();
        btnNumpad14 = new javax.swing.JButton();
        btnNumpad15 = new javax.swing.JButton();
        btnNumpad16 = new javax.swing.JButton();
        btnNumpad17 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblPay = new javax.swing.JLabel();
        lblRemainder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        cashButton.setBackground(new java.awt.Color(255, 204, 204));
        cashButton.setText("ГОТІВКА");
        cashButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashButtonActionPerformed(evt);
            }
        });

        cnclButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cnclButtonActionPerformed(evt);
            }
        });

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cardButton.setText("КАРТКА");
        cardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonActionPerformed(evt);
            }
        });

        onlineButton.setText("ОНЛАЙН");
        onlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineButtonActionPerformed(evt);
            }
        });

        chkFiscalPrint.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chkFiscalPrint.setText("Друкувати фіскальний чек");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cashButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(onlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkFiscalPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cnclButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cashButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFiscalPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnNumpad7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad7ActionPerformed(evt);
            }
        });

        btnNumpad4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad4ActionPerformed(evt);
            }
        });

        btnNumpad1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad1ActionPerformed(evt);
            }
        });

        btnNumpad0.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad0ActionPerformed(evt);
            }
        });

        btnNumpad2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad2ActionPerformed(evt);
            }
        });

        btnNumpad5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad5ActionPerformed(evt);
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

        btnNumpad6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad6ActionPerformed(evt);
            }
        });

        btnNumpad3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad3ActionPerformed(evt);
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

        btnNumpad10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad10.setText("5");
        btnNumpad10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad10ActionPerformed(evt);
            }
        });

        btnNumpad11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad11.setText("50");
        btnNumpad11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad11ActionPerformed(evt);
            }
        });

        btnNumpad12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad12.setText("500");
        btnNumpad12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad12ActionPerformed(evt);
            }
        });

        btnNumpad13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad13.setText("10");
        btnNumpad13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad13ActionPerformed(evt);
            }
        });

        btnNumpad14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad14.setText("100");
        btnNumpad14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad14ActionPerformed(evt);
            }
        });

        btnNumpad15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad15.setText("1000");
        btnNumpad15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad15ActionPerformed(evt);
            }
        });

        btnNumpad16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad16.setText("20");
        btnNumpad16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad16ActionPerformed(evt);
            }
        });

        btnNumpad17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNumpad17.setText("200");
        btnNumpad17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadC, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnNumpad6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpad12, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNumpad14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNumpad13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNumpad17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNumpad16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnNumpad15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNumpad10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNumpad13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnNumpad16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNumpad11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnNumpad17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNumpad12, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNumpad0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNumpadC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("РАЗОМ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setText("ОПЛАЧЕНО");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel3.setText("РЕШТА");
        jLabel3.setToolTipText("");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        lblPay.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        lblRemainder.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPay, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblRemainder, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblPay, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblRemainder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cashButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashButtonActionPerformed
        setPayType(PayTypes.CASH);
        clearPayTypesButtonsColor();
        cashButton.setBackground(new java.awt.Color(255, 204, 204));
    }//GEN-LAST:event_cashButtonActionPerformed

    private void cnclButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnclButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cnclButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        ok = true;
        summaPay = Double.parseDouble(lblPay.getText());
        printFiscal = chkFiscalPrint.isSelected();
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void clearPayTypesButtonsColor(){
        cashButton.setBackground(null);
        cardButton.setBackground(null);
        onlineButton.setBackground(null);
    }

    private void cardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardButtonActionPerformed
        ChoiseCardMerchForm payCard = ChoiseCardMerchForm.getPay(this, RoundUtils.roundHalfUp(order.getDocumentSum()), order);
        payCard.setVisible(true);
        if (payCard.isOK()) {
            ok = true;
            summaPay = Double.parseDouble(lblTotal.getText());
            printFiscal = chkFiscalPrint.isSelected();
            setPayType(PayTypes.CARD);
            dispose();
        }
    }//GEN-LAST:event_cardButtonActionPerformed

    private void onlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineButtonActionPerformed
        setPayType(PayTypes.ONLINE);
        clearPayTypesButtonsColor();
        onlineButton.setBackground(new java.awt.Color(255, 204, 204));
    }//GEN-LAST:event_onlineButtonActionPerformed

    private void btnNumpad7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad7ActionPerformed
        addDigitToQtyField("7");
    }//GEN-LAST:event_btnNumpad7ActionPerformed

    private void btnNumpad4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad4ActionPerformed
        addDigitToQtyField("4");
    }//GEN-LAST:event_btnNumpad4ActionPerformed

    private void btnNumpad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad1ActionPerformed
        addDigitToQtyField("1");
    }//GEN-LAST:event_btnNumpad1ActionPerformed

    private void btnNumpad0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad0ActionPerformed
        addDigitToQtyField("0");
    }//GEN-LAST:event_btnNumpad0ActionPerformed

    private void btnNumpad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad2ActionPerformed
        addDigitToQtyField("2");
    }//GEN-LAST:event_btnNumpad2ActionPerformed

    private void btnNumpad5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad5ActionPerformed
        addDigitToQtyField("5");
    }//GEN-LAST:event_btnNumpad5ActionPerformed

    private void btnNumpad8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad8ActionPerformed
        addDigitToQtyField("8");
    }//GEN-LAST:event_btnNumpad8ActionPerformed

    private void btnNumpad9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad9ActionPerformed
        addDigitToQtyField("9");
    }//GEN-LAST:event_btnNumpad9ActionPerformed

    private void btnNumpad6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad6ActionPerformed
        addDigitToQtyField("6");
    }//GEN-LAST:event_btnNumpad6ActionPerformed

    private void btnNumpad3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad3ActionPerformed
        addDigitToQtyField("3");
    }//GEN-LAST:event_btnNumpad3ActionPerformed

    private void btnNumpadCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCancelActionPerformed
        deleteLastDigitInQtyField();
    }//GEN-LAST:event_btnNumpadCancelActionPerformed

    private void btnNumpadCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCActionPerformed
        addComaToQtyField();
    }//GEN-LAST:event_btnNumpadCActionPerformed

    private void btnNumpad10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad10ActionPerformed
        lblPay.setText("5");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad10ActionPerformed

    private void btnNumpad11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad11ActionPerformed
        lblPay.setText("50");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad11ActionPerformed

    private void btnNumpad12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad12ActionPerformed
        lblPay.setText("500");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad12ActionPerformed

    private void btnNumpad13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad13ActionPerformed
        lblPay.setText("10");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad13ActionPerformed

    private void btnNumpad14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad14ActionPerformed
        lblPay.setText("100");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad14ActionPerformed

    private void btnNumpad15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad15ActionPerformed
        lblPay.setText("1000");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad15ActionPerformed

    private void btnNumpad16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad16ActionPerformed
        lblPay.setText("20");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad16ActionPerformed

    private void btnNumpad17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad17ActionPerformed
        lblPay.setText("200");
        handleRemainder();
        edit = true;
    }//GEN-LAST:event_btnNumpad17ActionPerformed

    private void addDigitToQtyField(String digit){
        if (!edit || Objects.equals(lblPay.getText(), "0")){
            lblPay.setText(digit);
        } else {
            lblPay.setText(lblPay.getText()+digit);
        }
        handleRemainder();
        edit = true;
    }

    private void deleteLastDigitInQtyField(){
        if (lblPay.getText().length()>0){
            lblPay.setText(lblPay.getText().substring(0, lblPay.getText().length() - 1));
        }

        if (lblPay.getText().isEmpty()){
            lblPay.setText("0");
        }
        handleRemainder();
    }

    private void addComaToQtyField() {
        if (lblPay.getText().length() == 0 || !edit) {
            lblPay.setText("0.");
        } else {
            if (!lblPay.getText().contains(".")) {
                lblPay.setText(lblPay.getText() + ".");
            }
        }
        handleRemainder();
        edit = true;
    }

    public static PayForm getPay(Component parent, Order order) {
        Window window = SwingUtilities.windowForComponent(parent);

        PayForm dialog;

        if (window instanceof Frame) {
            dialog = new PayForm((Frame) window, true);
        } else {
            dialog = new PayForm((Dialog) window, true);
        }
        dialog.setOrder(order);
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
        lblTotal.setText(String.valueOf(RoundUtils.roundHalfUp(order.getDocumentSum())));
        lblPay.setText(lblTotal.getText());
        summaPay = RoundUtils.roundHalfUp(order.getDocumentSum());
        handleRemainder();
    }

    private void handleRemainder(){
        double summaPay = Double.parseDouble(lblPay.getText());
        double remainder = RoundUtils.roundHalfUp(summaPay - order.getDocumentSum());
        if (remainder<0){
            remainder = 0;
        }
        lblRemainder.setText(String.valueOf(remainder));
    }

    public boolean isOK() {
        return ok;
    }

    public boolean isPrintFiscal() {
        return printFiscal;
    }

    public double getPay() {
        return summaPay;
    }

    public String getRemainder() {
        return lblRemainder.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNumpad0;
    private javax.swing.JButton btnNumpad1;
    private javax.swing.JButton btnNumpad10;
    private javax.swing.JButton btnNumpad11;
    private javax.swing.JButton btnNumpad12;
    private javax.swing.JButton btnNumpad13;
    private javax.swing.JButton btnNumpad14;
    private javax.swing.JButton btnNumpad15;
    private javax.swing.JButton btnNumpad16;
    private javax.swing.JButton btnNumpad17;
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
    private javax.swing.JButton cardButton;
    private javax.swing.JButton cashButton;
    private javax.swing.JCheckBox chkFiscalPrint;
    private javax.swing.JButton cnclButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblPay;
    private javax.swing.JLabel lblRemainder;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton okButton;
    private javax.swing.JButton onlineButton;
    // End of variables declaration//GEN-END:variables
}
