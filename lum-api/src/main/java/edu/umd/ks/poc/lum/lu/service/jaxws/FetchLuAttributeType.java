
package edu.umd.ks.poc.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Tue Sep 30 15:22:40 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "fetchLuAttributeType", namespace = "http://edu.umd.ks/poc/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchLuAttributeType", namespace = "http://edu.umd.ks/poc/lum/lu")

public class FetchLuAttributeType {

    @XmlElement(name = "luAttributeTypeId")
    private java.lang.String luAttributeTypeId;

    public java.lang.String getLuAttributeTypeId() {
        return this.luAttributeTypeId;
    }

    public void setLuAttributeTypeId(java.lang.String newLuAttributeTypeId)  {
        this.luAttributeTypeId = newLuAttributeTypeId;
    }

}

