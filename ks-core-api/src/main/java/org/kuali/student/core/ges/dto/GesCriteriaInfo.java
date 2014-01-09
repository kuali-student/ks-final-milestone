package org.kuali.student.core.ges.dto;

import org.kuali.student.core.ges.infc.GesCriteria;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterInfo", propOrder = {"personId",
        "atpTypeKey", "_futureElements" })
public class GesCriteriaInfo implements GesCriteria {

    @XmlElement
    private String personId;

    @XmlElement
    private String atpTypeKey;

    @XmlAnyElement
    private List<Object> _futureElements;



    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getAtpTypeKey() {
        return atpTypeKey;
    }

    public void setAtpTypeKey(String atpTypeKey) {
        this.atpTypeKey = atpTypeKey;
    }


}
