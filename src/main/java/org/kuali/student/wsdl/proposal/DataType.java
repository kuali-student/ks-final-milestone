
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dataType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="TRUNCATED_DATE"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="INTEGER"/>
 *     &lt;enumeration value="FLOAT"/>
 *     &lt;enumeration value="DOUBLE"/>
 *     &lt;enumeration value="LONG"/>
 *     &lt;enumeration value="COMPLEX"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dataType")
@XmlEnum
public enum DataType {

    STRING,
    DATE,
    TRUNCATED_DATE,
    BOOLEAN,
    INTEGER,
    FLOAT,
    DOUBLE,
    LONG,
    COMPLEX;

    public String value() {
        return name();
    }

    public static DataType fromValue(String v) {
        return valueOf(v);
    }

}
