package ua.gaponov.posterminal.entity.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.cards.Card;
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
@Getter
@Setter
public class Order implements Serializable, Cloneable {
    private String guid = UUID.randomUUID().toString();
    private long orderNumber;
    private LocalDateTime date;
    @JsonIgnore
    private boolean upload;
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
        documentSum = RoundUtils.round(sum);
        discountSum = RoundUtils.round(discount);
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
