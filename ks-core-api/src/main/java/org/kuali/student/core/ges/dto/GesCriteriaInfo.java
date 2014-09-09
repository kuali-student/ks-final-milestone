package org.kuali.student.core.ges.dto;

import org.kuali.student.core.ges.infc.GesCriteria;
import org.kuali.student.r2.common.dto.HasAttributesInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterInfo", propOrder = {"personId", "courseId",
        "atpId", "attributes","_futureElements" })
public class GesCriteriaInfo extends HasAttributesInfo implements GesCriteria {

    ///////////////////////////
    // DATA VARIABLES
    ///////////////////////////

    @XmlElement
    private String atpId;

    @XmlElement
    private String personId;

    @XmlElement
    private String courseId;

    @XmlAnyElement
    private List<Object> _futureElements;

    ///////////////////////////
    // GETTERS & SETTERS
    ///////////////////////////

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    ///////////////////////////
    // FUNCTIONALS
    ///////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GesCriteriaInfo)) return false;

        GesCriteriaInfo that = (GesCriteriaInfo) o;

        if (atpId != null ? !atpId.equals(that.atpId) : that.atpId != null) return false;
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (atpId != null ? atpId.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }


}
