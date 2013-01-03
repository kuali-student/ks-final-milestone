package org.kuali.student.enrollment.academicrecord.dto.attic;

import org.kuali.student.enrollment.academicrecord.infc.attic.Honors;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HonorsInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "dateAwarded", "termName", "termKey", "termStartDate", "termEndDate",
        "meta", "attributes", "_futureElements"})
public class HonorsInfo extends IdEntityInfo implements Honors, Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private Date dateAwarded;

    @XmlElement
    private String termName;

    @XmlElement
    private String termKey;

    @XmlElement
    private Date termStartDate;
    @XmlElement
    private Date termEndDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    @Override
    public Date getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(Date dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    @Override
    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    @Override
    public String getTermKey() {
        return termKey;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    @Override
    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    @Override
    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }
}
