
package edu.umd.ks.poc.lum.scat.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Mon Oct 13 10:42:36 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "removeScatCodesFromScatTableResponse", namespace = "http://edu.umd.ks/poc/lum/scat")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeScatCodesFromScatTableResponse", namespace = "http://edu.umd.ks/poc/lum/scat")

public class RemoveScatCodesFromScatTableResponse {

    @XmlElement(name = "return")
    private int _return;

    public int getReturn() {
        return this._return;
    }

    public void setReturn(int new_return)  {
        this._return = new_return;
    }

}

