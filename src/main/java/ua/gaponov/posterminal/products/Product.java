package ua.gaponov.posterminal.products;

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
}
