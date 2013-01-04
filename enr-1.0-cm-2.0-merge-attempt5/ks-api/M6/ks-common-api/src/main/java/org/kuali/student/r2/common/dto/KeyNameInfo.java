package org.kuali.student.r2.common.dto;

import org.kuali.student.r2.common.infc.KeyName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Version 2.0
 * @Author Kuali Student
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyNameInfo", propOrder = {
        "key", "name"})
// "key", "value", "_futureElements" })
public class KeyNameInfo implements KeyName {

    @XmlElement
    private String key;
    @XmlElement
    private String name;
    // @XmlAnyElement		KSENROLL-2747
    // private List<Element> _futureElements;

    public KeyNameInfo() {
    }

    public KeyNameInfo(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public KeyNameInfo(KeyName keyName) {
        if(null != keyName) {
            this.key = keyName.getKey();
            this.name = keyName.getName();
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
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
