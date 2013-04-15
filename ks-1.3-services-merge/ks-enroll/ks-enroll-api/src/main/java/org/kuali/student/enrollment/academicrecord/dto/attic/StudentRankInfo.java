package org.kuali.student.enrollment.academicrecord.dto.attic;

import org.kuali.student.enrollment.academicrecord.infc.attic.StudentRank;
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
@XmlType(name = "StudentRankInfo", propOrder = {
        "id", "meta", "attributes",
        "rank", "populationSize", "populationKey",
        "typeKey", "stateKey", "_futureElements"})
public class StudentRankInfo extends IdNamelessEntityInfo implements StudentRank, Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement
    public String rank;

    @XmlElement
    public Integer populationSize;

    @XmlElement
    public String populationKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    public StudentRankInfo() {

    }

    public StudentRankInfo(StudentRank studentRank) {
        super(studentRank);
        if (null != studentRank) {
            this.rank = studentRank.getRank();
            this.populationSize = studentRank.getPopulationSize();
            this.populationKey = studentRank.getPopulationKey();
        }
    }

    @Override
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public Integer getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(Integer populationSize) {
        this.populationSize = populationSize;
    }

    @Override
    public String getPopulationKey() {
        return populationKey;
    }

    public void setPopulationKey(String populationKey) {
        this.populationKey = populationKey;
    }
}
