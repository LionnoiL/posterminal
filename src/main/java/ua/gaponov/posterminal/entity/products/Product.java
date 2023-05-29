package ua.gaponov.posterminal.entity.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.organization.Organization;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class Product implements Serializable {

    private String guid;
    private String name;
    private String code;
    @JsonIgnore
    private double price;
    @JsonIgnore
    private String sku;
    @JsonIgnore
    private boolean banDisckount;
    @JsonIgnore
    private String taxCode;
    @JsonIgnore
    private int taxGroup;
    @JsonIgnore
    private boolean weight;
    @JsonIgnore
    private Organization organization;
    @JsonIgnore
    private double qty;
    @JsonIgnore
    private boolean needExcise;
    @JsonIgnore
    private String unitName;
    @JsonIgnore
    private boolean prostoPayProduct;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.guid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.guid, other.guid);
    }

    @Override
    public String toString() {
        return "Product{" + "guid=" + guid + ", name=" + name + ", code="
                + code + ", price=" + price + ", sku=" + sku + ", banDisckount="
                + banDisckount + ", taxCode=" + taxCode + ", taxGroup="
                + taxGroup + ", weight=" + weight + '}';
    }
}
