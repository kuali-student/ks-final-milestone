package org.kuali.student.enrollment.academicrecord.dto;

import org.kuali.student.enrollment.academicrecord.infc.Load;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadInfo", propOrder = {
        "id", "meta", "attributes",
        "totalCredits", "loadLevelTypeKey",
        "typeKey", "stateKey", "_futureElements"})
public class LoadInfo extends IdNamelessEntityInfo implements Load, Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private String totalCredits;
    @XmlElement
    private String loadLevelTypeKey;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LoadInfo() {

    }

    public LoadInfo(Load load) {
        super(load);
        if (null != load) {
            this.totalCredits = load.getTotalCredits();
            this.loadLevelTypeKey = load.getLoadLevelTypeKey();
        }
    }

    @Override
    public String getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(String totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String getLoadLevelTypeKey() {
        return loadLevelTypeKey;
    }

    public void setLoadLevelTypeKey(String loadLevelTypeKey) {
        this.loadLevelTypeKey = loadLevelTypeKey;
    }
}
