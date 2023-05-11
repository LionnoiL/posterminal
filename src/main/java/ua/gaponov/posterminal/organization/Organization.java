package ua.gaponov.posterminal.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@JacksonXmlRootElement(localName = "organizations_detail")
public class Organization implements Serializable {

    private String guid;
    private String name;
    private String code;
    private String rroName;
    private String rroToken;
    private boolean rroActive;

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
