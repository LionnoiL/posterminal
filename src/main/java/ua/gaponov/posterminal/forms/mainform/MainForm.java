package ua.gaponov.posterminal.forms.mainform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.PosTerminal;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.dataexchange.upload.ExchangeUpload;
import ua.gaponov.posterminal.dataexchange.upload.HttpDocumentUploadService;
import ua.gaponov.posterminal.devices.customerdisplay.CustomerDisplay;
import ua.gaponov.posterminal.devices.customerdisplay.lpos.LposDisplay;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.vchasno.VchasnoFiscal;
import ua.gaponov.posterminal.devices.printer.Printer;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.moneymove.MoneyMove;
import ua.gaponov.posterminal.entity.moneymove.MoneyMoveService;
import ua.gaponov.posterminal.entity.moneymove.PrintMoneyMove;
import ua.gaponov.posterminal.entity.orders.*;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductService;
import ua.gaponov.posterminal.forms.additionally.AdditionallyForm;
import ua.gaponov.posterminal.forms.additionally.ApplicationInfoForm;
import ua.gaponov.posterminal.forms.additionally.BarCodeNotFoundInfoForm;
import ua.gaponov.posterminal.forms.excise.ExciseScanForm;
import ua.gaponov.posterminal.forms.fiscal.FiscalForm;
import ua.gaponov.posterminal.forms.inputnumbers.NumberDialog;
import ua.gaponov.posterminal.forms.moneymove.MoneyMoveForm;
import ua.gaponov.posterminal.forms.options.OptionsForm;
import ua.gaponov.posterminal.forms.pay.PayForm;
import ua.gaponov.posterminal.forms.productinfo.ProductInfoForm;
import ua.gaponov.posterminal.forms.quickproducts.QuickProductDialog;
import ua.gaponov.posterminal.forms.returnproduct.ReturnForm;
import ua.gaponov.posterminal.forms.shiftresult.ShiftResultForm;
import ua.gaponov.posterminal.prostopay.ProstoPayProductNotFoundException;
import ua.gaponov.posterminal.prostopay.ProstoPayService;
import ua.gaponov.posterminal.utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ua.gaponov.posterminal.utils.Constants.ORDER_NUMBER_NAME;
import static ua.gaponov.posterminal.utils.ImagesUtils.getIcon;
import static ua.gaponov.posterminal.utils.ImagesUtils.getImage;
import static ua.gaponov.posterminal.utils.PropertiesUtils.saveAllApplicationProperties;

/**
* @author Andriy Gaponov
*/
public class MainForm extends javax.swing.JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(MainForm.class);
    private static MainForm frame;
    private static StringBuilder barBufer = new StringBuilder();
    private static Order order = new Order();
    private String infoMessage = "";
    private static Map<Integer, Order> currentOrders = new HashMap<>();
    private static int currentOrderIndex = 1;

    public transient Timer infoTimer = new Timer();
    private transient CustomerDisplay display = new LposDisplay();

    private DeviceFiscalPrinter fiscal = new VchasnoFiscal();

    /**
     * Creates new form mainForm
     */
    public MainForm() {
        this.setUndecorated(true);
        initComponents();
        jLabelUserName.setText(AppProperties.getCurrentUser().getName());
        setImages();
        updateVisibleButtons();
        setTopButtons();

        currentOrders = OrderService.loadOrderFromBackupDir();
        AppProperties.getAutoSaveScheduler().setTimeReceived(currentOrders);
        setSumInTopButtons();
        updateTopButtons();

        setInfoTimer();
        setClockTimer();
    }

    private void setClockTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateClockLabel, 0, 100, TimeUnit.MILLISECONDS);
        AppProperties.setClockScheduler(scheduler);
    }

    public void updateClockLabel() {
        jLabelTime.setText(DateUtils.getDateTimeNow());
    }

    private void setTopButtons() {
        jButton1.setText("0.0");
        JLabel jLabelTopButton1 = new JLabel("1");
        jLabelTopButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
        jLabelTopButton1.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelTopButton1.setAlignmentX(2);
        jLabelTopButton1.setAlignmentY(45);
        jButton1.add(jLabelTopButton1);

        jButton2.setText("0.0");
        JLabel jLabelTopButton2 = new JLabel("2");
        jLabelTopButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
        jLabelTopButton2.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelTopButton2.setAlignmentX(2);
        jLabelTopButton2.setAlignmentY(45);
        jButton2.add(jLabelTopButton2);

        jButton3.setText("0.0");
        JLabel jLabelTopButton3 = new JLabel("3");
        jLabelTopButton3.setVerticalTextPosition(SwingConstants.BOTTOM);
        jLabelTopButton3.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelTopButton3.setAlignmentX(2);
        jLabelTopButton3.setAlignmentY(45);
        jButton3.add(jLabelTopButton3);

        jButton4.setText("0.0");
        JLabel jLabelTopButton4 = new JLabel("4");
        jLabelTopButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
        jLabelTopButton4.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelTopButton4.setAlignmentX(2);
        jLabelTopButton4.setAlignmentY(45);
        jButton4.add(jLabelTopButton4);

        jButton5.setText("0.0");
        JLabel jLabelTopButton5 = new JLabel("5");
        jLabelTopButton5.setVerticalTextPosition(SwingConstants.BOTTOM);
        jLabelTopButton5.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelTopButton5.setAlignmentX(2);
        jLabelTopButton5.setAlignmentY(45);
        jButton5.add(jLabelTopButton5);
    }
    
    private void clearBackgroundsTopButtons() {
        jButton1.setBackground(null);
        jButton2.setBackground(null);
        jButton3.setBackground(null);
        jButton4.setBackground(null);
        jButton5.setBackground(null);
    }

    private JButton getButtonByIndex(int index) {
        JButton currentButton;
        switch (index) {
            case 2:
                currentButton = jButton2;
                break;
            case 3:
                currentButton = jButton3;
                break;
            case 4:
                currentButton = jButton4;
                break;
            case 5:
                currentButton = jButton5;
                break;
            default:
                currentButton = jButton1;
        }
        return currentButton;
    }

    private void setBackgroundInCurrentTopButton() {
        JButton currentButton = getButtonByIndex(currentOrderIndex);
        currentButton.setBackground(new java.awt.Color(141, 208, 211));
    }

    private void setSumInTopButtons() {
        for (Map.Entry<Integer, Order> entry : currentOrders.entrySet()) {
            setSumToTopButton(entry.getKey(), entry.getValue());
        }
    }

    private void setSumToTopButton(int index, Order order) {
        JButton button = getButtonByIndex(index);
        if (order == null) {
            button.setText("0.0");
        } else {
            button.setText(String.valueOf(order.getDocumentSum()));
        }
    }

    private void updateTopButtons() {
        clearBackgroundsTopButtons();
        setBackgroundInCurrentTopButton();
        order = currentOrders.get(currentOrderIndex);
        if (order == null){
            createNewOrder();
        }
        loadOrder();

        jButtonClearCard.setVisible(order.getCard() != null);
    }

    private void loadOrder() {
        updateTable();
        updateLabelByOrderCard();
        updateSumLabel();
    }

    private static void createNewOrder() {
        order = null;
        order = new Order();
        currentOrders.put(currentOrderIndex, order);

        AppProperties.getAutoSaveScheduler().setOrders(currentOrders);
        OrderService.saveOrderToBackupDir(currentOrders);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(
                () -> {
                    frame = new MainForm();
                    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                    frame.refresh();
                    frame.loadColumnsWidth();
                    frame.updateSumLabel();
                }
        );
    }

    public void setInfoTimer() {
        Calendar calendar = Calendar.getInstance();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if (AppProperties.exchangeRunning){
                    lblInfo.setText("Триває оновлення довідників");
                } else {
                    lblInfo.setText(infoMessage);
                }
            }
        };

        infoTimer.schedule(
                timerTask,
                calendar.getTime(),
                2000
        );
    }

    private void updateVisibleButtons() {
        btnOptions.setVisible(AppProperties.getCurrentUser().isAdmin());
    }

    private void updateByCard(Card card){
        order.setCard(card);
        order.recalculateAllRowsDiscounts();
        updateTable();
        updateLabelByOrderCard();
        updateSumLabel();

        jButtonClearCard.setVisible(card != null);
    }

    private void updateLabelByOrderCard(){
        Card card = order.getCard();
        lblCardCode.setText(null);
        lblClientName.setText(null);
        lblDebt.setText(null);

        if (Objects.nonNull(card)){
            lblCardCode.setText(card.getCode());
            lblClientName.setText(card.getClientName());
            lblDebt.setText(String.valueOf(card.getDebt()));
        }
    }

    private void setImages() {
        this.setIconImage(getImage("barcode.png"));
        btnNumpadC.setIcon(getIcon("btnce.png"));
        btnNumpadCancel.setIcon(getIcon("btnback.png"));
        btnDeleteProductRow.setIcon(getIcon("btnminus.png"));
        btnNumpad0.setIcon(getIcon("btn0.png"));
        btnNumpadComa.setIcon(getIcon("btndot.png"));
        btnNumpad05.setIcon(getIcon("btndiv.png"));
        btnNumpad1.setIcon(getIcon("btn1.png"));
        btnNumpad2.setIcon(getIcon("btn2.png"));
        btnNumpad3.setIcon(getIcon("btn3.png"));
        btnNumpad4.setIcon(getIcon("btn4.png"));
        btnNumpad5.setIcon(getIcon("btn5.png"));
        btnNumpad6.setIcon(getIcon("btn6.png"));
        btnNumpad7.setIcon(getIcon("btn7.png"));
        btnNumpad9.setIcon(getIcon("btn9.png"));
        btnNumpad8.setIcon(getIcon("btn8.png"));
        btnEnterQty.setIcon(getIcon("apply.png"));
        eixitButton.setIcon(getIcon("exit.png"));
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
        quickProductsButton = new javax.swing.JButton();
        barcodeButoon = new javax.swing.JButton();
        skuButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnEnterQty = new javax.swing.JButton();
        btnNumpadC = new javax.swing.JButton();
        btnNumpadCancel = new javax.swing.JButton();
        btnDeleteProductRow = new javax.swing.JButton();
        btnNumpad0 = new javax.swing.JButton();
        btnNumpadComa = new javax.swing.JButton();
        btnNumpad05 = new javax.swing.JButton();
        btnNumpad1 = new javax.swing.JButton();
        btnNumpad2 = new javax.swing.JButton();
        btnNumpad3 = new javax.swing.JButton();
        btnNumpad4 = new javax.swing.JButton();
        btnNumpad5 = new javax.swing.JButton();
        btnNumpad6 = new javax.swing.JButton();
        btnNumpad7 = new javax.swing.JButton();
        btnNumpad8 = new javax.swing.JButton();
        btnNumpad9 = new javax.swing.JButton();
        inputQty = new javax.swing.JTextField();
        btnInfoProduct = new javax.swing.JButton();
        btnInfoShift = new javax.swing.JButton();
        btnMoney = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnAdditionally = new javax.swing.JButton();
        btnFiscal = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        eixitButton = new javax.swing.JButton();
        jTextFieldBarCodeInput = new javax.swing.JTextField();
        btnOptions = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();
        jButtonAppInfo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        sumLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCardCode = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblClientName = new javax.swing.JLabel();
        lblDebt = new javax.swing.JLabel();
        jButtonClearCard = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProducts = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabelTime = new javax.swing.JLabel();
        jLabelUserName = new javax.swing.JLabel();
        btnPay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("POS");
        setExtendedState(3);
        setPreferredSize(new java.awt.Dimension(1024, 768));

        quickProductsButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        quickProductsButton.setText("Швидкі товари");
        quickProductsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickProductsButtonActionPerformed(evt);
            }
        });

        barcodeButoon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        barcodeButoon.setText("Штрихкод");
        barcodeButoon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barcodeButoonActionPerformed(evt);
            }
        });

        skuButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        skuButton.setText("СКЮ");
        skuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skuButtonActionPerformed(evt);
            }
        });

        btnEnterQty.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEnterQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterQtyActionPerformed(evt);
            }
        });

        btnNumpadC.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpadC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpadCActionPerformed(evt);
            }
        });

        btnNumpadCancel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpadCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpadCancelActionPerformed(evt);
            }
        });

        btnDeleteProductRow.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDeleteProductRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductRowActionPerformed(evt);
            }
        });

        btnNumpad0.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad0ActionPerformed(evt);
            }
        });

        btnNumpadComa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpadComa.setToolTipText("");
        btnNumpadComa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpadComaActionPerformed(evt);
            }
        });

        btnNumpad05.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad05ActionPerformed(evt);
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
        btnNumpad3.setToolTipText("");
        btnNumpad3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumpad3ActionPerformed(evt);
            }
        });

        btnNumpad4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad4.setToolTipText("");
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

        btnNumpad7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNumpad7.setToolTipText("");
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

        inputQty.setEditable(false);
        inputQty.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        inputQty.setToolTipText("");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNumpad4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNumpad1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNumpad0, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpadComa, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad05, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNumpadC, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpadCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteProductRow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputQty)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNumpad7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumpad9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEnterQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteProductRow, btnNumpad0, btnNumpad05, btnNumpad1, btnNumpad2, btnNumpad3, btnNumpad4, btnNumpad5, btnNumpad6, btnNumpad7, btnNumpad8, btnNumpad9, btnNumpadC, btnNumpadCancel, btnNumpadComa});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(inputQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad7, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad8, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpad0, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpadComa, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpad05, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNumpadC, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnNumpadCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnDeleteProductRow, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnterQty, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteProductRow, btnNumpad0, btnNumpad05, btnNumpad1, btnNumpad2, btnNumpad3, btnNumpad4, btnNumpad5, btnNumpad6, btnNumpad7, btnNumpad8, btnNumpad9, btnNumpadC, btnNumpadCancel, btnNumpadComa});

        btnInfoProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnInfoProduct.setText("<html>Інформація <br>про продукт</html>");
        btnInfoProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoProductActionPerformed(evt);
            }
        });

        btnInfoShift.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnInfoShift.setText("<html>Інформація <br>про зміну</html>");
        btnInfoShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoShiftActionPerformed(evt);
            }
        });

        btnMoney.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoney.setText("Гроші");
        btnMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoneyActionPerformed(evt);
            }
        });

        btnAdditionally.setText("Додатково");
        btnAdditionally.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdditionallyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnAdditionally, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 39, Short.MAX_VALUE)
                .addComponent(btnAdditionally, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnFiscal.setText("РРО");
        btnFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiscalActionPerformed(evt);
            }
        });

        btnReturn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReturn.setText("Повернення");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(quickProductsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInfoProduct, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(skuButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barcodeButoon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInfoShift)
                    .addComponent(btnReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(quickProductsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(skuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barcodeButoon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInfoProduct)
                    .addComponent(btnInfoShift, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        eixitButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        eixitButton.setToolTipText("");
        eixitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eixitButtonActionPerformed(evt);
            }
        });

        jTextFieldBarCodeInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldBarCodeInputKeyPressed(evt);
            }
        });

        btnOptions.setText("Налаштування");
        btnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionsActionPerformed(evt);
            }
        });

        lblInfo.setToolTipText("");

        jButtonAppInfo.setText("?");
        jButtonAppInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAppInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eixitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBarCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addGap(43, 43, 43)
                .addComponent(btnOptions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAppInfo)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(eixitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldBarCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAppInfo)))
                .addContainerGap())
        );

        sumLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        sumLabel.setText("1254,00");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Картка");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Покупець:");

        lblCardCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCardCode.setText("1234567890123");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Борг");

        lblClientName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblClientName.setText("Гапонов Андрій");

        lblDebt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDebt.setText("0.0");
        lblDebt.setToolTipText("");

        jButtonClearCard.setText("Видалити картку");
        jButtonClearCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(lblCardCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDebt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonClearCard))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCardCode)
                    .addComponent(jButtonClearCard, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDebt))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTableProducts.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Товар", "Одиниці", "Кількість", "Ціна", "Сума"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProducts.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableProducts.setColumnSelectionAllowed(true);
        jTableProducts.setRowHeight(40);
        jTableProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableProducts.setUpdateSelectionOnSort(false);
        jTableProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableProducts);
        jTableProducts.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabelTime.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTime.setText("00:00:00");

        jLabelUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelUserName.setText("Користувач");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTime, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabelTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelUserName))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        btnPay.setBackground(new java.awt.Color(0, 204, 0));
        btnPay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPay.setText("Оплата");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eixitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eixitButtonActionPerformed
        if (DialogUtils.okcancel(frame, "Вихід з програми", "Вийти з програми?") == 0) {
            infoTimer.cancel();
            ExchangeUpload.upload();
            SqlHelper.backupDB();
            saveColumnsWidth();
            saveAllApplicationProperties();
            OrderService.saveOrderToBackupDir(currentOrders);
            Sound.end();
            dispose();
            PosTerminal.closeApp();
        }
    }//GEN-LAST:event_eixitButtonActionPerformed

    private void jTextFieldBarCodeInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBarCodeInputKeyPressed
        frame.jTextFieldBarCodeInput.setText(null);
        getBarcode(evt);
    }//GEN-LAST:event_jTextFieldBarCodeInputKeyPressed

    private void jTableProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductsMouseClicked
        refresh();
    }//GEN-LAST:event_jTableProductsMouseClicked

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        refresh();

        //Pay
        if (order.getDetails().size()>0){

            sendTotalSumToCustomerDisplay();

            PayForm payForm = PayForm.getPay(frame, order);
            payForm.setVisible(true);
            if (payForm.isOK()) {

                //перевірка достатності оплати
                order.setPaySum(payForm.getPay());
                order.setPayType(payForm.getPayType());
                if (!order.canBePrinted()){
                    DialogUtils.error(this, "Помилка оплати чеку");
                    return;
                }

                sendRemainderSumToCustomerDisplay(payForm.getRemainder());

                if (payForm.isPrintFiscal()){
                    if (!fiscal.printOrder(order)){
                        DialogUtils.error(this, "Fiscal print order failed");
                        LOG.error("Fiscal print order failed");
                        return;
                    } else {
                        order.setFiscalPrint(true);
                    }
                }

                try {
                    order.setUser(AppProperties.getCurrentUser());
                    ProstoPayService.printQrCodesByOrder(order);
                    OrderService.save(order);
                    HttpDocumentUploadService.sendOrder(order);
                    NumbersService.saveNumber(ORDER_NUMBER_NAME, order.getOrderNumber());
                } catch (ProstoPayProductNotFoundException ex) {
                    DialogUtils.error(this, "Продукт не знайдено в таблиці ProstoPay");
                    LOG.error("Error printing QR codes for order", ex);
                    return;
                }  catch (SQLException ex){
                    DialogUtils.error(this, "Помилка збереження чеку");
                    LOG.error("Error save order", ex);
                    return;
                }

                if (PayTypes.CASH.equals(order.getPayType())) {
                    Printer.openCashDrawer();
                }

                new PrintOrder(order);
                createNewOrder();

                jButtonClearCard.setVisible(false);

                loadOrder();
                sumLabel.setText(payForm.getRemainder() + " " + AppProperties.getCurrency());
            }
        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoneyActionPerformed
        refresh();
        MoneyMoveForm moneyMoveForm = MoneyMoveForm.getMoneyMove(frame);
        moneyMoveForm.setVisible(true);
        if (moneyMoveForm.isOk()) {
            MoneyMove moneyMoveDoc = new MoneyMove();
            moneyMoveDoc.setDocumentSum(moneyMoveForm.getSummaDoc());
            moneyMoveDoc.setMoveType(moneyMoveForm.getMoveType());
            moneyMoveDoc.setComment(moneyMoveForm.getComment());
            try {
                MoneyMoveService.save(moneyMoveDoc);
                HttpDocumentUploadService.sendMoneyMove(moneyMoveDoc);

                new PrintMoneyMove(moneyMoveDoc);
            } catch (SQLException ex) {
                DialogUtils.error(this, "Помилка збереження оплати");
                LOG.error("Error save money move", ex);
            }
        }
    }//GEN-LAST:event_btnMoneyActionPerformed

    private void btnInfoShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoShiftActionPerformed
        refresh();

        ShiftResultForm shiftResultForm = ShiftResultForm.showDialog(frame);
        shiftResultForm.setVisible(true);
    }//GEN-LAST:event_btnInfoShiftActionPerformed

    private void btnInfoProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoProductActionPerformed
        refresh();
        ProductInfoForm.main(null);
    }//GEN-LAST:event_btnInfoProductActionPerformed

    private void btnNumpad9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad9ActionPerformed
        refresh();
        addDigitToQtyField("9");
    }//GEN-LAST:event_btnNumpad9ActionPerformed

    private void btnNumpad8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad8ActionPerformed
        refresh();
        addDigitToQtyField("8");
    }//GEN-LAST:event_btnNumpad8ActionPerformed

    private void btnNumpad7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad7ActionPerformed
        refresh();
        addDigitToQtyField("7");
    }//GEN-LAST:event_btnNumpad7ActionPerformed

    private void btnNumpad6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad6ActionPerformed
        refresh();
        addDigitToQtyField("6");
    }//GEN-LAST:event_btnNumpad6ActionPerformed

    private void btnNumpad5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad5ActionPerformed
        refresh();
        addDigitToQtyField("5");
    }//GEN-LAST:event_btnNumpad5ActionPerformed

    private void btnNumpad4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad4ActionPerformed
        refresh();
        addDigitToQtyField("4");
    }//GEN-LAST:event_btnNumpad4ActionPerformed

    private void btnNumpad3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad3ActionPerformed
        refresh();
        addDigitToQtyField("3");
    }//GEN-LAST:event_btnNumpad3ActionPerformed

    private void btnNumpad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad2ActionPerformed
        refresh();
        addDigitToQtyField("2");
    }//GEN-LAST:event_btnNumpad2ActionPerformed

    private void btnNumpad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad1ActionPerformed
        refresh();
        addDigitToQtyField("1");
    }//GEN-LAST:event_btnNumpad1ActionPerformed

    private void btnNumpad05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad05ActionPerformed
        refresh();
        inputQty.setText("0.5");
    }//GEN-LAST:event_btnNumpad05ActionPerformed

    private void btnNumpadComaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadComaActionPerformed
        refresh();
        addComaToQtyField();
    }//GEN-LAST:event_btnNumpadComaActionPerformed

    private void btnNumpad0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpad0ActionPerformed
        refresh();
        addDigitToQtyField("0");
    }//GEN-LAST:event_btnNumpad0ActionPerformed

    private void btnDeleteProductRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductRowActionPerformed
        refresh();
        order.deleteRow(jTableProducts.getSelectedRow());
        updateSumLabel();
        updateTable();
        selectTableRow(order.getDetails().size() - 1);
        if (order.getDetails().size()>0){
            sendOrderDetailInCustomerDisplay(order.getDetails().get(order.getDetails().size() - 1));
        }
    }//GEN-LAST:event_btnDeleteProductRowActionPerformed

    private void btnNumpadCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCancelActionPerformed
        refresh();
        deleteLastDigitInQtyField();
    }//GEN-LAST:event_btnNumpadCancelActionPerformed

    private void btnNumpadCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumpadCActionPerformed
        refresh();
        inputQty.setText("");
    }//GEN-LAST:event_btnNumpadCActionPerformed

    private void btnEnterQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterQtyActionPerformed
        refresh();
        updateQtyRow();
    }//GEN-LAST:event_btnEnterQtyActionPerformed

    private void skuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skuButtonActionPerformed
        refresh();
        NumberDialog inputSku = NumberDialog.getNumber(frame);
        inputSku.setVisible(true);
        if (inputSku.isOK()) {
            skuHandle(inputSku.getNumber());
        }
    }//GEN-LAST:event_skuButtonActionPerformed

    private void barcodeButoonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barcodeButoonActionPerformed
        refresh();
        NumberDialog inputBarcode = NumberDialog.getNumber(frame);
        inputBarcode.setVisible(true);
        if (inputBarcode.isOK()) {
            barcodeHandle(inputBarcode.getNumber());
        }
    }//GEN-LAST:event_barcodeButoonActionPerformed

    private void quickProductsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickProductsButtonActionPerformed
        refresh();
        QuickProductDialog quickProduct = QuickProductDialog.getQuickProduct(frame);
        quickProduct.setVisible(true);
        if (quickProduct.isOK()) {
            Product product = quickProduct.getProduct();
            if (product != null) {
                int lineNumber = order.addDetailRow(product, product.getQty());
                sendOrderDetailInCustomerDisplay(order.getDetails().get(lineNumber));
                updateTable();
                selectTableRow(lineNumber);
                updateSumLabel();
            }
        }
    }//GEN-LAST:event_quickProductsButtonActionPerformed

    private void btnOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionsActionPerformed
        refresh();
        OptionsForm.main(null);
    }//GEN-LAST:event_btnOptionsActionPerformed

    private void btnFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiscalActionPerformed
        refresh();
        FiscalForm.main(null);
    }//GEN-LAST:event_btnFiscalActionPerformed

    private void btnAdditionallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdditionallyActionPerformed
        refresh();
        AdditionallyForm.main(null);
    }//GEN-LAST:event_btnAdditionallyActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        refresh();
        ReturnForm returnForm = ReturnForm.getReturn(frame);
        returnForm.setVisible(true);
        if (returnForm.isOk()) {
            sumLabel.setText("Повернути "+RoundUtils.roundHalfUp(returnForm.getOrder().getDocumentSum()) + " " + AppProperties.getCurrency());
        }
    }//GEN-LAST:event_btnReturnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        currentOrderIndex = 1;
        updateTopButtons();
        refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        currentOrderIndex = 2;
        updateTopButtons();
        refresh();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        currentOrderIndex = 3;
        updateTopButtons();
        refresh();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        currentOrderIndex = 4;
        updateTopButtons();
        refresh();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        currentOrderIndex = 5;
        updateTopButtons();
        refresh();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonClearCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearCardActionPerformed
        updateByCard(null);
    }//GEN-LAST:event_jButtonClearCardActionPerformed

    private void jButtonAppInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAppInfoActionPerformed
        refresh();
        ApplicationInfoForm infoForm = ApplicationInfoForm.showDialog(frame);
        infoForm.setVisible(true);
    }//GEN-LAST:event_jButtonAppInfoActionPerformed

    private void addDigitToQtyField(String digit) {
        inputQty.setText(inputQty.getText() + digit);
    }

    private void deleteLastDigitInQtyField() {
        if (inputQty.getText().length() > 0) {
            inputQty.setText(inputQty.getText().substring(0, inputQty.getText().length() - 1));
        }
    }

    private void addComaToQtyField() {
        if (inputQty.getText().length() == 0) {
            inputQty.setText("0.");
        } else {
            if (!inputQty.getText().contains(".")) {
                inputQty.setText(inputQty.getText() + ".");
            }
        }
    }

    private void updateQtyRow() {
        refresh();
        int selectedRow = jTableProducts.getSelectedRow();

        if (selectedRow == -1 || inputQty.getText().isEmpty()) {
            return;
        }

        try {
            double newQty = Double.valueOf(inputQty.getText());
            if (newQty == 0){
                btnDeleteProductRowActionPerformed(null);
                return;
            }
            setQtyInRow(selectedRow, newQty);
        } catch (NumberFormatException e) {
            //NOP
        }
    }

    private void setQtyInRow(int row, double qty) {
        order.changeQtyInRow(jTableProducts.getSelectedRow(), qty);
        inputQty.setText(null);
        updateSumLabel();
        updateTable();
        selectTableRow(row);

        sendOrderDetailInCustomerDisplay(order.getDetails().get(row));
    }

    private void sendOrderDetailInCustomerDisplay(OrderDetail orderDetail){
        if (Objects.isNull(orderDetail) || Objects.isNull(orderDetail.getProduct())){
            return;
        }
        display.writeDisplay(orderDetail.getProduct().getName(),
                orderDetail.getQty() + "x" + orderDetail.getPrice(),
                String.valueOf(RoundUtils.roundHalfUp(orderDetail.getSumma())));
    }

    private void sendTotalSumToCustomerDisplay(){
        display.writeDisplay("","Загалом", String.valueOf(RoundUtils.roundHalfUp(order.getDocumentSum())));
    }

    private void sendRemainderSumToCustomerDisplay(String summa){
        display.writeDisplay("Загалом", String.valueOf(RoundUtils.roundHalfUp(order.getDocumentSum())),
                            "Решта", summa);
    }

    private void refresh() {
        jTextFieldBarCodeInput.setText(null);
        java.awt.EventQueue.invokeLater(() -> jTextFieldBarCodeInput.requestFocus());
    }

    private void getBarcode(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            barcodeHandle(barBufer.toString());
            barBufer.setLength(0);
        } else {
            barBufer.append(evt.getKeyChar());
        }
        refresh();
    }

    private void barcodeHandle(String barcode) {
        barcode = barcode.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
        Product product = ProductService.getByBarcode(barcode);
        if (product != null) {
            int lineNumber = order.addDetailRow(product, product.getQty());
            sendOrderDetailInCustomerDisplay(order.getDetails().get(lineNumber));
            addExcise(product, lineNumber);
            updateTable();
            selectTableRow(lineNumber);
            updateSumLabel();
        } else {
            Card findCard = CardService.getByCode(barcode);
            if (findCard != null){
                updateByCard(findCard);
            } else {
                Sound.error();
                BarCodeNotFoundInfoForm barCodeNotFoundInfoForm = BarCodeNotFoundInfoForm.showDialog(frame);
                barCodeNotFoundInfoForm.setVisible(true);
            }
        }
    }

    private void addExcise(Product product, int line){
        if (product.isNeedExcise()){
            ExciseScanForm inputExcise = ExciseScanForm.getBarcode(frame);
            inputExcise.setVisible(true);
            if (inputExcise.isOK()) {
                List<OrderDetail> details = order.getDetails();
                details.get(line).setExcise(inputExcise.getExcise());
            }
        }
    }

    private void skuHandle(String sku) {
        sku = sku.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
        Product product = ProductService.getProductFromSku(sku);
        if (product != null) {
            int lineNumber = order.addDetailRow(product, product.getQty());
            sendOrderDetailInCustomerDisplay(order.getDetails().get(lineNumber));
            updateTable();
            selectTableRow(lineNumber);
            updateSumLabel();
        }
    }

    private void updateSumLabel() {
        order.recalculateDocumentSum();
        sumLabel.setText(String.valueOf(RoundUtils.roundHalfUp(order.getDocumentSum())) + " " + AppProperties.getCurrency());
    }

    private void selectTableRow(int lineNumber) {
        jTableProducts.changeSelection(lineNumber, 0, false, false);
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) jTableProducts.getModel();
        model.setRowCount(0);
        List<OrderDetail> details = order.getDetails();
        for (int i = 0; i < details.size(); i++) {
            model.addRow(createRowTable(details.get(i)));
        }
        setSumToTopButton(currentOrderIndex, order);
    }

    private Object[] createRowTable(OrderDetail orderDetail) {
        String excise = "";
        Object[] rowData = new Object[6];
        if (orderDetail.getExcise() != null){
            excise = " " + orderDetail.getExcise();
        }
        rowData[0] = orderDetail.getProduct().getName() + excise;
        rowData[1] = orderDetail.getProduct().getUnitName();
        rowData[2] = orderDetail.getQty();
        rowData[3] = orderDetail.getPrice();
        rowData[4] = RoundUtils.round(orderDetail.getSumma());
        return rowData;
    }

    private void saveColumnsWidth() {
        PropertiesUtils.saveApplicationTempValue("main_table_column_product",
                String.valueOf(jTableProducts.getColumn("Товар").getWidth()));
        PropertiesUtils.saveApplicationTempValue("main_table_column_unit",
                String.valueOf(jTableProducts.getColumn("Одиниці").getWidth()));
        PropertiesUtils.saveApplicationTempValue("main_table_column_qty",
                String.valueOf(jTableProducts.getColumn("Кількість").getWidth()));
        PropertiesUtils.saveApplicationTempValue("main_table_column_price",
                String.valueOf(jTableProducts.getColumn("Ціна").getWidth()));
        PropertiesUtils.saveApplicationTempValue("main_table_column_sum",
                String.valueOf(jTableProducts.getColumn("Сума").getWidth()));;
    }

    private void loadColumnsWidth() {
        try {
        jTableProducts.getColumn("Товар")
                .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("main_table_column_product")));
        jTableProducts.getColumn("Одиниці")
                .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("main_table_column_unit")));
        jTableProducts.getColumn("Кількість")
                .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("main_table_column_qty")));
        jTableProducts.getColumn("Ціна")
                .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("main_table_column_price")));
        jTableProducts.getColumn("Сума")
                .setPreferredWidth(Integer.parseInt(PropertiesUtils.getApplicationTempValue("main_table_column_sum")));
        } catch (Exception e){
            //NOP
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barcodeButoon;
    private javax.swing.JButton btnAdditionally;
    private javax.swing.JButton btnDeleteProductRow;
    private javax.swing.JButton btnEnterQty;
    private javax.swing.JButton btnFiscal;
    private javax.swing.JButton btnInfoProduct;
    private javax.swing.JButton btnInfoShift;
    private javax.swing.JButton btnMoney;
    private javax.swing.JButton btnNumpad0;
    private javax.swing.JButton btnNumpad05;
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
    private javax.swing.JButton btnNumpadComa;
    private javax.swing.JButton btnOptions;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton eixitButton;
    private javax.swing.JTextField inputQty;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAppInfo;
    private javax.swing.JButton jButtonClearCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelUserName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProducts;
    private javax.swing.JTextField jTextFieldBarCodeInput;
    private javax.swing.JLabel lblCardCode;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblDebt;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JButton quickProductsButton;
    private javax.swing.JButton skuButton;
    private javax.swing.JLabel sumLabel;
    // End of variables declaration//GEN-END:variables
}
