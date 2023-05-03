package ua.gaponov.posterminal.documents.orders;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import ua.gaponov.posterminal.documents.PayTypes;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import ua.gaponov.posterminal.cards.Card;
import ua.gaponov.posterminal.organization.Organization;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.utils.RoundUtils;

/**
 *
 * @author wmcon
 */
public class Order implements Serializable {
    private String guid = UUID.randomUUID().toString();
    private long orderNumber;
    private LocalDateTime date;
    private transient boolean upload;
    private double documentSum;
    private double paySum;
    private double discountSum;
    private PayTypes payType = PayTypes.CASH;
    private boolean fiscal;
    private boolean internet;
    private boolean fiscalPrint;
    private Card card;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "detail")
    private List<OrderDetail> details = new ArrayList<>();

    public boolean canBePrinted(){
        double maxDebt = 0;
        if (Objects.nonNull(getCard())){
            if (getCard().isDebtAllowed()){
                maxDebt = getCard().getMaxDebt();
            }
        }

        if ((getDocumentSum() - getPaySum()) > maxDebt){
            return false;
        }

        return true;
    }

    public int addDetailRow(Product product, double qty){
        int findLine = -1;
        if (!product.isNeedExcise()){
            findLine = findRowByProduct(product);
        }
        OrderDetail orderDetail = new OrderDetail();

        if (findLine == -1){
            details.add(orderDetail);
            orderDetail.setQty(qty);
            findLine = details.size() - 1;
        } else {
            orderDetail = details.get(findLine);
            orderDetail.setQty(qty + orderDetail.getQty());
        }
        
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.recalculateDiscountsInRow(getCard());
        recalculateDocumentSum();

        return findLine;
    }
    
    public int findRowByProduct(Product product){
        int result = -1;
        for (int i = 0; i < details.size(); i++) {
           if (details.get(i).getProduct().equals(product)){
               result = i;
               break;
           } 
        }
        return result;
    }

    public void changeQtyInRow(int row, double newQty){
        OrderDetail orderDetail = details.get(row);
        orderDetail.setQty(newQty);
        orderDetail.recalculateDiscountsInRow(getCard());
        recalculateDocumentSum();
    }

    public void deleteRow(int row){
        if (row<0 || row>details.size()){
            return;
        }
        details.remove(row);
    }

    public void recalculateDocumentSum(){
        double sum = 0;
        double discount = 0;
        for (OrderDetail detail : details) {
            sum = sum + detail.getSumma();
            discount = discount + detail.getSummaDiscount();
        }
        documentSum = sum;
        discountSum = discount;
    }

    public void recalculateAllRowsDiscounts(){
        getDetails().forEach(detail -> detail.recalculateDiscountsInRow(getCard()));
        recalculateDocumentSum();
    }

    @Transient
    public Map<Organization, Double> getTotalsByOrganizations(){
        Map<Organization, Double> result = new HashMap<>();
        List<OrderDetail> orderDetails = getDetails();
        for (OrderDetail orderDetail : orderDetails) {
            Organization organization = orderDetail.getProduct().getOrganization();
            if (result.containsKey(organization)){
                result.put(organization, result.get(organization) + orderDetail.getSumma());
            } else {
                result.put(organization, orderDetail.getSumma());
            }
        }
        return result;
    }

    public double getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public double getDocumentSum() {
        return documentSum;
    }

    public void setDocumentSum(double documentSum) {
        this.documentSum = documentSum;
    }

    public double getPaySum() {
        return paySum;
    }

    public void setPaySum(double paySum) {
        this.paySum = paySum;
    }

    public PayTypes getPayType() {
        return payType;
    }

    public void setPayType(PayTypes payType) {
        this.payType = payType;
    }

    public boolean isFiscal() {
        return fiscal;
    }

    public void setFiscal(boolean fiscal) {
        this.fiscal = fiscal;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isFiscalPrint() {
        return fiscalPrint;
    }

    public void setFiscalPrint(boolean fiscalPrint) {
        this.fiscalPrint = fiscalPrint;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}
