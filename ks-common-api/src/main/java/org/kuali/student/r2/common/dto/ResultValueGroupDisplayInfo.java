package org.kuali.student.r2.common.dto;

import org.kuali.student.r2.common.infc.ResultValueGroupDisplay;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Version 2.0
 * @Author Kuali Student
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultValueGroupDisplayInfo", propOrder = {
        "key", "value" })
// "key", "value", "_futureElements" })
public class ResultValueGroupDisplayInfo implements ResultValueGroupDisplay {

    @XmlElement
    private String key;
    @XmlElement
    private String value;
    // @XmlAnyElement		KSENROLL-2747
    // private List<Element> _futureElements;

    public ResultValueGroupDisplayInfo() {
    }

    public ResultValueGroupDisplayInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public ResultValueGroupDisplayInfo(ResultValueGroupDisplay resultValueGroupDisplay) {
        if(null != resultValueGroupDisplay) {
            this.key = resultValueGroupDisplay.getKey();
            this.value = resultValueGroupDisplay.getValue();
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
