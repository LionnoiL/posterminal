/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ua.gaponov.posterminal.forms.fiscal;

import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.vchasno.VchasnoFiscal;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.organization.OrganizationService;
import ua.gaponov.posterminal.utils.DialogUtils;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.table.DefaultTableModel;
import java.util.List;

import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;

/**
 *
 * @author wmcon
 */
public class FiscalForm extends javax.swing.JDialog {

    private DeviceFiscalPrinter fiscal;
    private DefaultTableModel model;
    private int selectedRow;
    private List<Organization> organizations;

    /**
     * Creates new form FiscalForm
     */
    public FiscalForm() {
        setModal(true);
        initComponents();
        setImages();
        updateTable();
        tblFiscals.removeColumn(tblFiscals.getColumnModel().getColumn(3));
        loadColumnsWidth();
    }

    private void setCurrentFiscalDevice(){
        tblFiscals.changeSelection(selectedRow, 0, false, false);
        String orgGuid = (String) model.getValueAt(selectedRow, 3);
        Organization byGuid = OrganizationService.getByGuid(orgGuid);
        fiscal = new VchasnoFiscal(byGuid.getRroName(), byGuid.getRroToken());
    }

    private void setImages() {
        this.setIconImage(getImage("barcode.png"));
    }

    private void updateTable() {
        model = (DefaultTableModel) tblFiscals.getModel();
        model.setRowCount(0);
        organizations = OrganizationService.getAll();
        for (Organization organization : organizations) {
            model.addRow(createRowTable(organization));
        }
    }

    private Object[] createRowTable(Organization organization) {
        fiscal = new VchasnoFiscal(organization.getRroName(), organization.getRroToken());
        String shiftStatus = "Закрита";
        String inSafe = "";
        if (fiscal.shiftIsOpen()){
            shiftStatus = "Відкрита";
            double safeMoney = fiscal.getSafeMoney();
            inSafe = "" + safeMoney + " " + AppProperties.getCurrency();
        }
        Object[] rowData = new Object[4];
        rowData[0] = organization.getName();
        rowData[1] = shiftStatus; //status
        rowData[2] = inSafe; //safe
        rowData[3] = organization.getGuid(); //guid
        return rowData;
    }

    private void saveColumnsWidth() {
        PropertiesUtils.saveApplicationTempValue("fiscal_table_column_org_name",
                String.valueOf(tblFiscals.getColumn("Організація").getWidth()));
        PropertiesUtils.saveApplicationTempValue("fiscal_table_column_status",
                String.valueOf(tblFiscals.getColumn("Статус зміни").getWidth()));
        PropertiesUtils.saveApplicationTempValue("fiscal_table_column_safe",
                String.valueOf(tblFiscals.getColumn("В сейфі").getWidth()));
    }

    private void loadColumnsWidth() {
        try {
            tblFiscals.getColumn("Організація")
                    .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("fiscal_table_column_org_name")));
            tblFiscals.getColumn("Статус зміни")
                    .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("fiscal_table_column_status")));
            tblFiscals.getColumn("В сейфі")
                    .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("fiscal_table_column_safe")));
        } catch (Exception e){
            //NOP
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        btnOpenShift = new javax.swing.JButton();
        btnCloseShift = new javax.swing.JButton();
        btnMoneyMinus = new javax.swing.JButton();
        fldSum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnMoneyPlus = new javax.swing.JButton();
        btnXReport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFiscals = new javax.swing.JTable();
        btnCloseAllShift = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Управління фіскальним реєстратором");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btnOpenShift.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnOpenShift.setText("Відкрити зміну");
        btnOpenShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenShiftActionPerformed(evt);
            }
        });

        btnCloseShift.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCloseShift.setText("Закрити зміну");
        btnCloseShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseShiftActionPerformed(evt);
            }
        });

        btnMoneyMinus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoneyMinus.setText("Вийняти");
        btnMoneyMinus.setToolTipText("");
        btnMoneyMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoneyMinusActionPerformed(evt);
            }
        });

        jLabel1.setText("Сума");

        btnMoneyPlus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoneyPlus.setText("Внести");
        btnMoneyPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoneyPlusActionPerformed(evt);
            }
        });

        btnXReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXReport.setText("X-звіт");
        btnXReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXReportActionPerformed(evt);
            }
        });

        tblFiscals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Організація", "Статус зміни", "В сейфі", "guid"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFiscals.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblFiscals.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblFiscals.getTableHeader().setReorderingAllowed(false);
        tblFiscals.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFiscalsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFiscals);
        if (tblFiscals.getColumnModel().getColumnCount() > 0) {
            tblFiscals.getColumnModel().getColumn(1).setPreferredWidth(2);
            tblFiscals.getColumnModel().getColumn(2).setPreferredWidth(2);
            tblFiscals.getColumnModel().getColumn(3).setPreferredWidth(1);
        }

        btnCloseAllShift.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCloseAllShift.setText("Закрити всі зміни");
        btnCloseAllShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAllShiftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnOpenShift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCloseShift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMoneyMinus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(fldSum))
                                .addComponent(btnMoneyPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnXReport, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnCloseAllShift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOpenShift, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXReport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCloseShift, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fldSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoneyMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoneyPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCloseAllShift, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenShiftActionPerformed
        fiscal.openShift();
        updateTable();
        //fiscal.printOrder(OrderService.getByGuid("ec2b65a8-b2b8-4100-87c3-dcdb66bc4b3d"));
    }//GEN-LAST:event_btnOpenShiftActionPerformed

    private void btnCloseShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseShiftActionPerformed
        fiscal.printZReport();
        updateTable();
    }//GEN-LAST:event_btnCloseShiftActionPerformed

    private void btnMoneyMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoneyMinusActionPerformed
        try {
            double money = Double.parseDouble(fldSum.getText());
            if (fiscal.moneyMinus(money)) {
                DialogUtils.ok(null, "Службова видача виконана");
                updateTable();
            } else {
                DialogUtils.error(null, "Помилка видачі");
            }
        } catch (Exception e){
            //NOP
        }
    }//GEN-LAST:event_btnMoneyMinusActionPerformed

    private void btnMoneyPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoneyPlusActionPerformed
        try {
            double money = Double.parseDouble(fldSum.getText());
            if (fiscal.moneyPlus(money)) {
                DialogUtils.ok(null, "Службове внесення виконано");
                updateTable();
            } else {
                DialogUtils.error(null, "Помилка внесення");
            }
        } catch (Exception e){
            //NOP
        }
    }//GEN-LAST:event_btnMoneyPlusActionPerformed

    private void btnXReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXReportActionPerformed
        fiscal.printXReport();
    }//GEN-LAST:event_btnXReportActionPerformed

    private void tblFiscalsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFiscalsMouseClicked
        selectedRow = tblFiscals.getSelectedRow();
        setCurrentFiscalDevice();
    }//GEN-LAST:event_tblFiscalsMouseClicked

    private void btnCloseAllShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAllShiftActionPerformed
        for (Organization organization : organizations) {
            DeviceFiscalPrinter tempFiscal = new VchasnoFiscal(organization.getRroName(), organization.getRroToken());
            if (tempFiscal.shiftIsOpen()){
                double safeMoney = tempFiscal.getSafeMoney();
                tempFiscal.moneyMinus(safeMoney);
                tempFiscal.printZReport();
            }
        }

        DialogUtils.ok(null, "Всі зміни закрито");
        updateTable();
    }//GEN-LAST:event_btnCloseAllShiftActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        saveColumnsWidth();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FiscalForm frame = new FiscalForm();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloseAllShift;
    private javax.swing.JButton btnCloseShift;
    private javax.swing.JButton btnMoneyMinus;
    private javax.swing.JButton btnMoneyPlus;
    private javax.swing.JButton btnOpenShift;
    private javax.swing.JButton btnXReport;
    private javax.swing.JTextField fldSum;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFiscals;
    // End of variables declaration//GEN-END:variables
}
