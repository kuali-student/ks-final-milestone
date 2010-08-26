
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usage.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="usage">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEFAULT"/>
 *     &lt;enumeration value="ADVANCED"/>
 *     &lt;enumeration value="CUSTOM"/>
 *     &lt;enumeration value="ADVANCED_CUSTOM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "usage")
@XmlEnum
public enum Usage {

    DEFAULT,
    ADVANCED,
    CUSTOM,
    ADVANCED_CUSTOM;

    public String value() {
        return name();
    }

    public static Usage fromValue(String v) {
        return valueOf(v);
    }

}
