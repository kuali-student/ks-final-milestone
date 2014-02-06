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
import org.kuali.rice.core.api.util.type.KualiDecimal;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadInfo", propOrder = {
        "id", "meta", "attributes",
        "totalCredits", "loadLevelTypeKey",
        "personId", "atpId",
        "typeKey", "stateKey", "_futureElements"})
public class LoadInfo extends IdNamelessEntityInfo implements Load, Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private KualiDecimal totalCredits;
    @XmlElement
    private String loadLevelTypeKey;
    @XmlElement
    private String personId;
    @XmlElement
    private String atpId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LoadInfo() {

    }

    public LoadInfo(Load load) {
        super(load);
        if (null != load) {
            this.totalCredits = load.getTotalCredits();
            this.personId = load.getPersonId();
            this.atpId = load.getAtpId();
            this.loadLevelTypeKey = load.getLoadLevelTypeKey();
        }
    }

    @Override
    public KualiDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(KualiDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String getLoadLevelTypeKey() {
        return loadLevelTypeKey;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public void setLoadLevelTypeKey(String loadLevelTypeKey) {
        this.loadLevelTypeKey = loadLevelTypeKey;
    }
}
