
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for writeAccess.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="writeAccess">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ON_CREATE"/>
 *     &lt;enumeration value="ALWAYS"/>
 *     &lt;enumeration value="NEVER"/>
 *     &lt;enumeration value="WHEN_NULL"/>
 *     &lt;enumeration value="REQUIRED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "writeAccess")
@XmlEnum
public enum WriteAccess {

    ON_CREATE,
    ALWAYS,
    NEVER,
    WHEN_NULL,
    REQUIRED;

    public String value() {
        return name();
    }

    public static WriteAccess fromValue(String v) {
        return valueOf(v);
    }

}
