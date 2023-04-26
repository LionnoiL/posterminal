package ua.gaponov.posterminal.products;

import java.util.Objects;
import ua.gaponov.posterminal.organization.Organization;

/**
 *
 * @author wmcon
 */
public class Product {
   
    private String guid;
    private String name;
    private String code;
    private double price;
    private String sku;
    private boolean banDisckount;
    private String taxCode;
    private int taxGroup;
    private boolean weight;
    private Organization organization;
    private double qty;
    private boolean needExcise;
    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public boolean isNeedExcise() {
        return needExcise;
    }

    public void setNeedExcise(boolean needExcise) {
        this.needExcise = needExcise;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isBanDisckount() {
        return banDisckount;
    }

    public void setBanDisckount(boolean banDisckount) {
        this.banDisckount = banDisckount;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public int getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(int taxGroup) {
        this.taxGroup = taxGroup;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

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
        return "Product{" + "guid=" + guid + ", name=" + name + ", code=" + 
                code + ", price=" + price + ", sku=" + sku + ", banDisckount=" + 
                banDisckount + ", taxCode=" + taxCode + ", taxGroup=" + 
                taxGroup + ", weight=" + weight + '}';
    }
}
