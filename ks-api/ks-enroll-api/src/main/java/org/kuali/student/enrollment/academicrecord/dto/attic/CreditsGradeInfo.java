package org.kuali.student.enrollment.academicrecord.dto.attic;

import org.kuali.student.enrollment.academicrecord.infc.attic.CreditsGrade;
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
@XmlType(name = "CreditsGradeInfo", propOrder = {
        "id", "meta", "attributes",
        "credits", "gradeKey",
        "typeKey", "stateKey", "_futureElements"})
public class CreditsGradeInfo extends IdNamelessEntityInfo
        implements CreditsGrade, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String credits;
    @XmlElement
    private String gradeKey;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CreditsGradeInfo() {

    }

    public CreditsGradeInfo(CreditsGrade creditsGrade) {
        super(creditsGrade);
        if (null != creditsGrade) {
            this.credits = creditsGrade.getCredits();
            this.gradeKey = creditsGrade.getGradeKey();
        }
    }

    @Override
    public String getCredits() {
        return this.credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @Override
    public String getGradeKey() {
        return this.gradeKey;
    }

    public void setGradeKey(String gradeKey) {
        this.gradeKey = gradeKey;
    }
}
