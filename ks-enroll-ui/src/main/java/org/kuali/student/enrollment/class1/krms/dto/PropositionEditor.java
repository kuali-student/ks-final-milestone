package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.PropositionParameterBo;
import org.kuali.rice.krms.impl.repository.TermBo;
import org.kuali.rice.krms.impl.repository.TermSpecificationBo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionEditor extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private PropositionBo proposition;
    private TermBo term;
    private String type;

    private String multipleCourseType;
    private String gradeScale;

    private String newTermDescription = "new term " + UUID.randomUUID().toString();

    public PropositionEditor() {
        super();
        proposition = new PropositionBo();
    }

    public PropositionEditor(PropositionBo proposition) {
        super();
        this.proposition = proposition;
    }

    public PropositionBo getProposition() {
        return proposition;
    }

    public void setProposition(PropositionBo proposition) {
        this.proposition = proposition;
    }

    public String getTermSpecId() {
        return this.getProposition().getTermSpecId();
    }

    public void setTermSpecId(String componentId) {
        this.getProposition().setTermSpecId(componentId);
    }

    public String getId(){
        return proposition.getId();
    }

    public String getDescription(){
        return proposition.getDescription();
    }

    public String getCompoundOpCode(){
        return proposition.getCompoundOpCode();
    }

    public List<PropositionParameterBo> getParameters(){
        return proposition.getParameters();
    }

    public String getTypeId(){
        return proposition.getTypeId();
    }

    public void setTypeId(String typeId){
        proposition.setTypeId(typeId);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMultipleCourseType() {
        return multipleCourseType;
    }

    public void setMultipleCourseType(String multipleCourseType) {
        this.multipleCourseType = multipleCourseType;
    }

    public String getGradeScale() {
        return gradeScale;
    }

    public void setGradeScale(String gradeScale) {
        this.gradeScale = gradeScale;
    }

}
