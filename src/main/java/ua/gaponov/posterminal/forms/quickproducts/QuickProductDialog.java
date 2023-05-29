package ua.gaponov.posterminal.forms.quickproducts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.quickproduct.QuickProduct;
import ua.gaponov.posterminal.entity.quickproduct.QuickProductService;
import ua.gaponov.posterminal.utils.StringUtils;

import static ua.gaponov.posterminal.utils.ImagesUtils.getIcon;
import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;

/**
* @author Andriy Gaponov
*/
public class QuickProductDialog extends javax.swing.JDialog {

    private Product product;
    private boolean ok;
    private int pageIndex = 0;
    private List<QuickButton> buttons;

    public QuickProductDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    private QuickProductDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }

    public static QuickProductDialog getQuickProduct(Component parent) {
        Window window = SwingUtilities.windowForComponent(parent);

        QuickProductDialog dialog;
        if (window instanceof Frame) {
            dialog = new QuickProductDialog((Frame) window, true);
        } else {
            dialog = new QuickProductDialog((Dialog) window, true);
        }
        dialog.init();
        dialog.setLocationRelativeTo(null);
        dialog.applyComponentOrientation(parent.getComponentOrientation());
        return dialog;
    }

    private void setImages(){
        this.setIconImage(getImage("barcode.png"));
        btnPrev.setIcon(getIcon("1leftarrow.png"));
        btnNext.setIcon(getIcon("1rightarrow.png"));
        btnStart.setIcon(getIcon("gohome.png"));
        btnClose.setIcon(getIcon("button_cancel.png"));
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStart = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnProduct2 = new javax.swing.JButton();
        btnProduct3 = new javax.swing.JButton();
        btnProduct1 = new javax.swing.JButton();
        btnProduct4 = new javax.swing.JButton();
        btnProduct5 = new javax.swing.JButton();
        btnProduct6 = new javax.swing.JButton();
        btnProduct7 = new javax.swing.JButton();
        btnProduct8 = new javax.swing.JButton();
        btnProduct9 = new javax.swing.JButton();
        btnProduct10 = new javax.swing.JButton();
        btnProduct11 = new javax.swing.JButton();
        btnProduct12 = new javax.swing.JButton();
        btnProduct13 = new javax.swing.JButton();
        btnProduct14 = new javax.swing.JButton();
        btnProduct15 = new javax.swing.JButton();
        btnProduct16 = new javax.swing.JButton();
        btnProduct17 = new javax.swing.JButton();
        btnProduct18 = new javax.swing.JButton();
        btnProduct19 = new javax.swing.JButton();
        btnProduct20 = new javax.swing.JButton();
        btnProduct21 = new javax.swing.JButton();
        btnProduct22 = new javax.swing.JButton();
        btnProduct23 = new javax.swing.JButton();
        btnProduct24 = new javax.swing.JButton();
        btnProduct25 = new javax.swing.JButton();
        btnProduct26 = new javax.swing.JButton();
        btnProduct27 = new javax.swing.JButton();
        btnProduct28 = new javax.swing.JButton();
        btnProduct29 = new javax.swing.JButton();
        btnProduct30 = new javax.swing.JButton();
        btnProduct31 = new javax.swing.JButton();
        btnProduct32 = new javax.swing.JButton();
        btnProduct33 = new javax.swing.JButton();
        btnProduct34 = new javax.swing.JButton();
        btnProduct35 = new javax.swing.JButton();
        btnProduct36 = new javax.swing.JButton();
        btnProduct37 = new javax.swing.JButton();
        btnProduct38 = new javax.swing.JButton();
        btnProduct39 = new javax.swing.JButton();
        btnProduct40 = new javax.swing.JButton();
        btnProduct41 = new javax.swing.JButton();
        btnProduct42 = new javax.swing.JButton();

        setResizable(false);

        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnProduct2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct2.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct3.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct1.setToolTipText("");
        btnProduct1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct1.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct4.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct5.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct6.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct7.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct8.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct9.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct10.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct11.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct12.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct13.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct14.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct15.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct16.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct17.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct18.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct19.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct20.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct21.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct22.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct23.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct24.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct25.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct26.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct27.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct28.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct29.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct30.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct31.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct32.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct33.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct34.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct35.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct36.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct37.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct38.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct39.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct39.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct40.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct41.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct41.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnProduct42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct42.setMargin(new java.awt.Insets(2, 2, 2, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProduct15, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct18, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct20, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct21, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProduct8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProduct22, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct23, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct24, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct25, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct26, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct27, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct28, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProduct29, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct30, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct31, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct32, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct33, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct34, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct35, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnProduct36, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnProduct37, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnProduct38, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct39, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct40, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct41, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnProduct42, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct25, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct26, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct27, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct28, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct29, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct31, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct34, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct35, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProduct36, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct37, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct38, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct40, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct41, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProduct42, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fillButtons(){
        clearButtons();
        List<QuickProduct> productsByPage = QuickProductService.getByPage(pageIndex);
        int index = 0;
        for (QuickProduct quickProduct : productsByPage) {
            Product prod = quickProduct.getProduct();
            prod.setQty(1);
            buttons.get(index).setProduct(prod);
            JButton jButton = buttons.get(index).getButton();
            jButton.setBackground(Color.decode("#"+quickProduct.getColor()));
            jButton.setText(getButtonStringWithWrap(quickProduct.getProduct().getName()));
            jButton.setActionCommand(String.valueOf(index));
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    product = buttons.get(Integer.parseInt(e.getActionCommand())).getProduct();
                    ok = true;
                    dispose();
                }
            } );
            index ++;
        }
    }

    private void clearButtons(){
        for (int i = 0; i < 42; i++) {
            buttons.get(i).setProduct(null);
            JButton jButton = buttons.get(i).getButton();
            jButton.setBackground(null);
            jButton.setText(null);
        }
    }

    private String getButtonStringWithWrap(String textButton){
        StringBuilder result = new StringBuilder("<html>");
        if (textButton.length()<=18){
            result.append(textButton);
            result.append("</html>");
            return textButton;
        }
        String[] newStrings = StringUtils.splitStringByCharCount(textButton, 18);
        for (String newString : newStrings) {
           result.append(newString);
           result.append("<p>");
        }
        result.append("</html>");
        return result.toString();
    }
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        pageIndex = 0;
        fillButtons();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (pageIndex>=1) {
            pageIndex = pageIndex - 1;
            fillButtons();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        pageIndex = pageIndex + 1;
        fillButtons();
    }//GEN-LAST:event_btnNextActionPerformed


    private void init() {
        initComponents();
        setImages();
        fillButtonsList();
        getRootPane().setDefaultButton(btnClose);
        ok = false;
        product = null;
        fillButtons();
    }

    private void fillButtonsList(){
        buttons = List.of(btnProduct1, btnProduct2, btnProduct3, btnProduct4, btnProduct5,
            btnProduct6, btnProduct7, btnProduct8, btnProduct9, btnProduct10,
            btnProduct11, btnProduct12, btnProduct13, btnProduct14, btnProduct15,
            btnProduct16, btnProduct17, btnProduct18, btnProduct19, btnProduct20,
            btnProduct21, btnProduct22, btnProduct23, btnProduct24, btnProduct25,
            btnProduct26, btnProduct27, btnProduct28, btnProduct29, btnProduct30,
            btnProduct31, btnProduct32, btnProduct33, btnProduct34, btnProduct35,
            btnProduct36, btnProduct37, btnProduct38, btnProduct39, btnProduct40,
            btnProduct41, btnProduct42).stream()
            .map(el -> new QuickButton(el))
            .toList();
    }

    public boolean isOK() {
        return ok;
    }

    public Product getProduct() {
        return product;
    }

    public class QuickButton{
        private Product product;
        private JButton button;

        public QuickButton(JButton button) {
            this.button = button;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public JButton getButton() {
            return button;
        }

        public void setButton(JButton button) {
            this.button = button;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnProduct1;
    private javax.swing.JButton btnProduct10;
    private javax.swing.JButton btnProduct11;
    private javax.swing.JButton btnProduct12;
    private javax.swing.JButton btnProduct13;
    private javax.swing.JButton btnProduct14;
    private javax.swing.JButton btnProduct15;
    private javax.swing.JButton btnProduct16;
    private javax.swing.JButton btnProduct17;
    private javax.swing.JButton btnProduct18;
    private javax.swing.JButton btnProduct19;
    private javax.swing.JButton btnProduct2;
    private javax.swing.JButton btnProduct20;
    private javax.swing.JButton btnProduct21;
    private javax.swing.JButton btnProduct22;
    private javax.swing.JButton btnProduct23;
    private javax.swing.JButton btnProduct24;
    private javax.swing.JButton btnProduct25;
    private javax.swing.JButton btnProduct26;
    private javax.swing.JButton btnProduct27;
    private javax.swing.JButton btnProduct28;
    private javax.swing.JButton btnProduct29;
    private javax.swing.JButton btnProduct3;
    private javax.swing.JButton btnProduct30;
    private javax.swing.JButton btnProduct31;
    private javax.swing.JButton btnProduct32;
    private javax.swing.JButton btnProduct33;
    private javax.swing.JButton btnProduct34;
    private javax.swing.JButton btnProduct35;
    private javax.swing.JButton btnProduct36;
    private javax.swing.JButton btnProduct37;
    private javax.swing.JButton btnProduct38;
    private javax.swing.JButton btnProduct39;
    private javax.swing.JButton btnProduct4;
    private javax.swing.JButton btnProduct40;
    private javax.swing.JButton btnProduct41;
    private javax.swing.JButton btnProduct42;
    private javax.swing.JButton btnProduct5;
    private javax.swing.JButton btnProduct6;
    private javax.swing.JButton btnProduct7;
    private javax.swing.JButton btnProduct8;
    private javax.swing.JButton btnProduct9;
    private javax.swing.JButton btnStart;
    // End of variables declaration//GEN-END:variables
}
