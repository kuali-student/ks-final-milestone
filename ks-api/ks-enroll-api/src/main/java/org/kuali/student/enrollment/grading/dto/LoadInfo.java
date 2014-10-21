package org.kuali.student.enrollment.grading.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.grading.infc.Load;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadInfo", propOrder = {"typeKey", "stateKey", "totalCredits", "totalLevelCode", "meta", "attributes",
        "_futureElements"})
public class LoadInfo extends TypeStateEntityInfo implements Load {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private Float totalCredits;
    @XmlElement
    private String totalLevelCode;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LoadInfo() {
        super();
        this.totalCredits = null;
        this.totalLevelCode = null;
        this._futureElements = null;
    }

    @Override
    public Float getTotalCredits() {
        return totalCredits;
    }

    @Override
    public String getTotalLevelCode() {
        return totalLevelCode;
    }
}
