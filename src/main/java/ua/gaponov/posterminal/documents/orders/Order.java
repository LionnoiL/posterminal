package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.documents.PayTypes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ua.gaponov.posterminal.cards.Card;
import ua.gaponov.posterminal.products.Product;

/**
 *
 * @author wmcon
 */
public class Order {
    private String guid;
    private LocalDateTime date;
    private boolean upload;
    private double documentSum;
    private double paySum;
    private PayTypes payType; 
    private boolean fiscal;
    private boolean internet;
    private boolean fiscalPrint;
    private Card card;
    private List<OrderDetail> details = new ArrayList<>();

    public void addDetailRow(Product product, double qty){
        int findLine = findItemByProduct(product);
        OrderDetail orderDetail = new OrderDetail();
        if (findLine == -1){ 
            orderDetail.setQty(qty);
        } else {
            orderDetail = details.get(findLine);
            orderDetail.setQty(qty + orderDetail.getQty());
        }
        
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setSumma(orderDetail.getPrice() * orderDetail.getQty());
        
        if (findLine == -1){ 
            details.add(orderDetail);
//        } else {
//            orderDetail = details.get(findLine);
//            orderDetail.setQty(qty + orderDetail.getQty());
        }
        
        
    }
    
    private int findItemByProduct(Product product){
        int result = -1;
        for (int i = 0; i < details.size(); i++) {
           if (details.get(i).getProduct().equals(product)){
               result = i;
               break;
           } 
        }
        return result;
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
