package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private String programType;
    private String gradeScale;
    private OrgInfo orgInfo;

    private static final String CLULIST_KEY = "kuali.term.parameter.type.course.nl.clu.list";
    private static final String CLUSETLIST_KEY = "kuali.term.parameter.type.course.nl.cluset.list";

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

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
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

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new EnrolPropositionEditor(definition);
    }

    @Override
    public Map<String, String> getNlParameters() {
        Map<String, String> nlParameters = super.getNlParameters();
        if (this.getCluSet() != null){
            nlParameters.put(CLULIST_KEY, this.getCluSet().getCluDelimitedString());
            nlParameters.put(CLUSETLIST_KEY, this.getCluSet().getCluSetDelimitedString());
        }
        return nlParameters;
    }
}
