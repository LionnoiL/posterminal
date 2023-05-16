package ua.gaponov.posterminal.entity.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@JacksonXmlRootElement(localName = "organizations_detail")
public class Organization implements Serializable {

    private String guid;
    private String name;
    private String code;
    private String rroName;
    private String rroToken;
    private boolean rroActive;

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

    @Transient
    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    @Transient
    public String getRroToken() {
        return rroToken;
    }

    public void setRroToken(String rroToken) {
        this.rroToken = rroToken;
    }

    @Transient
    public boolean isRroActive() {
        return rroActive;
    }

    public void setRroActive(boolean rroActive) {
        this.rroActive = rroActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        return guid.equals(that.guid);
    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }
}
