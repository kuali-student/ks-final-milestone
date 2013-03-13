package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolPropositionEditor extends PropositionEditor {

    private static final long serialVersionUID = 1L;

    private CourseInfo courseInfo;
    private CluSetInformation cluSet;
    private String multipleCourseType;
    private String gradeScale;

    public EnrolPropositionEditor(){
        super();
    }

    public EnrolPropositionEditor(PropositionDefinitionContract definition) {
        super(definition);
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
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

    public CluSetInformation getCluSet() {
        return cluSet;
    }

    public void setCluSet(CluSetInformation cluSet) {
        this.cluSet = cluSet;
    }

    public int getCluListSize(){
        if ((this.getCluSet() != null) && (this.getCluSet().getClus() != null)){
            return this.getCluSet().getClus().size();
        } else {
            return 0;
        }
    }

    public int getCluSetListSize(){
        if ((this.getCluSet() != null) && (this.getCluSet().getCluSets() != null)){
            return this.getCluSet().getCluSets().size();
        } else {
            return 0;
        }
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new EnrolPropositionEditor(definition);
    }

}
