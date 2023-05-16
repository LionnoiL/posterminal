package ua.gaponov.posterminal.entity.orders;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.utils.RoundUtils;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Andriy Gaponov
 */
public class Order implements Serializable, Cloneable {
    private String guid = UUID.randomUUID().toString();
    private long orderNumber;
    private LocalDateTime date;
    private transient boolean upload;
    private double documentSum;
    private double documentSumWithoutDiscount;
    private double roundSum;
    private double paySum;
    private double toPaySum;
    private double discountSum;
    private PayTypes payType = PayTypes.CASH;
    private String prnCode;
    private String authCode;
    private boolean fiscal;
    private boolean internet;
    private boolean fiscalPrint;
    private Card card;
    private DocumentTypes documentType = DocumentTypes.ORDER;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "detail")
    private List<OrderDetail> details = new ArrayList<>();

    public boolean canBePrinted() {
        double maxDebt = 0;
        if (Objects.nonNull(getCard()) && getCard().isDebtAllowed()) {
            maxDebt = getCard().getMaxDebt();
        }
        return (RoundUtils.roundHalfUp(getDocumentSum()) - getPaySum()) <= maxDebt;
    }

    public int addDetailRow(Product product, double qty) {
        int findLine = -1;
        if (!product.isNeedExcise()) {
            findLine = findRowByProduct(product);
        }
        OrderDetail orderDetail = new OrderDetail();

        if (findLine == -1) {
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

    public int findRowByProduct(Product product) {
        int result = -1;
        for (int i = 0; i < details.size(); i++) {
            if (details.get(i).getProduct().equals(product)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public void changeQtyInRow(int row, double newQty) {
        OrderDetail orderDetail = details.get(row);
        orderDetail.setQty(newQty);
        orderDetail.recalculateDiscountsInRow(getCard());
        recalculateDocumentSum();
    }

    public void deleteRow(int row) {
        if (row < 0 || row > details.size()) {
            return;
        }
        details.remove(row);
    }

    public void recalculateDocumentSum() {
        double sum = 0;
        double sumWithoutDiscount = 0;
        double discount = 0;
        for (OrderDetail detail : details) {
            sum = sum + detail.getSumma();
            sumWithoutDiscount = sumWithoutDiscount + detail.getSummaWithoutDiscount();
            discount = discount + detail.getSummaDiscount();
        }
        documentSum = sum;
        discountSum = discount;
        documentSumWithoutDiscount = sumWithoutDiscount;

        roundSum = RoundUtils.round(documentSum - RoundUtils.roundHalfUp(documentSum));

        toPaySum = RoundUtils.roundHalfUp(documentSum - roundSum);
    }

    public void recalculateAllRowsDiscounts() {
        getDetails().forEach(detail -> detail.recalculateDiscountsInRow(getCard()));
        recalculateDocumentSum();
    }

    @Transient
    public Map<Organization, Double> getTotalsByOrganizations() {
        Map<Organization, Double> result = new HashMap<>();
        List<OrderDetail> orderDetails = getDetails();
        for (OrderDetail orderDetail : orderDetails) {
            Organization organization = orderDetail.getProduct().getOrganization();
            if (result.containsKey(organization)) {
                result.put(organization, result.get(organization) + orderDetail.getSumma());
            } else {
                result.put(organization, orderDetail.getSumma());
            }
        }
        return result;
    }

    public DocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    public double getDocumentSumWithoutDiscount() {
        return documentSumWithoutDiscount;
    }

    public void setDocumentSumWithoutDiscount(double documentSumWithoutDiscount) {
        this.documentSumWithoutDiscount = documentSumWithoutDiscount;
    }

    public String getPrnCode() {
        return prnCode;
    }

    public void setPrnCode(String prnCode) {
        this.prnCode = prnCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public double getToPaySum() {
        return toPaySum;
    }

    public void setToPaySum(double toPaySum) {
        this.toPaySum = toPaySum;
    }

    public double getRoundSum() {
        return roundSum;
    }

    public void setRoundSum(double roundSum) {
        this.roundSum = roundSum;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
