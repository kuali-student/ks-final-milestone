
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for widget.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="widget">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUGGEST_BOX"/>
 *     &lt;enumeration value="DROPDOWN_LIST"/>
 *     &lt;enumeration value="RADIO_BUTTONS"/>
 *     &lt;enumeration value="CHECK_BOXES"/>
 *     &lt;enumeration value="TEXT_BOX"/>
 *     &lt;enumeration value="CALENDAR"/>
 *     &lt;enumeration value="PICKER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "widget")
@XmlEnum
public enum Widget {

    SUGGEST_BOX,
    DROPDOWN_LIST,
    RADIO_BUTTONS,
    CHECK_BOXES,
    TEXT_BOX,
    CALENDAR,
    PICKER;

    public String value() {
        return name();
    }

    public static Widget fromValue(String v) {
        return valueOf(v);
    }

}
