
package edu.umd.ks.poc.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Mon Oct 06 10:48:49 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "searchForLuTypesByDescription", namespace = "http://edu.umd.ks/poc/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchForLuTypesByDescription", namespace = "http://edu.umd.ks/poc/lum/lu")

public class SearchForLuTypesByDescription {

    @XmlElement(name = "descriptionSearchString")
    private java.lang.String descriptionSearchString;

    public java.lang.String getDescriptionSearchString() {
        return this.descriptionSearchString;
    }

    public void setDescriptionSearchString(java.lang.String newDescriptionSearchString)  {
        this.descriptionSearchString = newDescriptionSearchString;
    }

}

