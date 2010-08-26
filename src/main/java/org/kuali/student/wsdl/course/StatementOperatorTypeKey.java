
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statementOperatorTypeKey.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statementOperatorTypeKey">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AND"/>
 *     &lt;enumeration value="OR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statementOperatorTypeKey")
@XmlEnum
public enum StatementOperatorTypeKey {

    AND,
    OR;

    public String value() {
        return name();
    }

    public static StatementOperatorTypeKey fromValue(String v) {
        return valueOf(v);
    }

}
