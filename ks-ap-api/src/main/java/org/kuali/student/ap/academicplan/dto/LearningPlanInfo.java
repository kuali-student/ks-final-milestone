package org.kuali.student.ap.academicplan.dto;

import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * LearningPlan message structure
 *
 * @Author kmuthu
 * Date: 1/5/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningPlanInfo", propOrder = {"studentId", "id", "typeKey", "stateKey", "shared", "name", "descr", "meta", "attributes", "_futureElements"})
public class LearningPlanInfo extends IdEntityInfo implements LearningPlan {

    @XmlElement
    private String studentId;

    //TODO: KSAP-1014 - Add 'name' attribute to LearningPlan and PlanItem

    @XmlElement
    private Boolean shared;

    @XmlAnyElement
    private List<Element> _futureElements;


    public LearningPlanInfo() {
        this.setId(null);
        this.setDescr(null);
        this.studentId = null;
        this._futureElements = null;
    }

    public LearningPlanInfo(LearningPlan plan) {
        super(plan);

        if(null != plan) {
            this.setId(plan.getId());
            this.studentId = plan.getStudentId();
            this.setDescr((null != plan.getDescr()) ? new RichTextInfo(plan.getDescr()) : null);
            this.setShared(plan.isShared());
        }
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public Boolean isShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
