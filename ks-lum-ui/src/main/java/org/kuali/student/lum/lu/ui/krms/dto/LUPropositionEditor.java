/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.util.CluSetRangeWrapper;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class LUPropositionEditor extends KSPropositionEditor {

    private static final long serialVersionUID = 1L;

    private CourseInfo courseInfo;
    private CluSetInformation courseSet;
    private CluSetInformation courseSetParent;
    private String multipleCourseType = new String();

    private String programType;
    private String gradeScale;
    private OrgInfo orgInfo;
    private CluSetInformation programSet;
    private CluSetInformation programSetParent;
    private String termCode;
    private String termCode2;
    private TermInfo termInfo;
    private TermInfo termInfo2;

    private CluSetInformation cluSets;
    private CluSetRangeWrapper cluSetRange = new CluSetRangeWrapper();

        private static final String CLULIST_KEY = "kuali.term.parameter.type.course.nl.clu.list";
    private static final String CLUSETLIST_KEY = "kuali.term.parameter.type.course.nl.cluset.list";

    public LUPropositionEditor(){
        super();
    }

    public LUPropositionEditor(PropositionDefinitionContract definition) {
        super(definition);
    }

    public void clear(){
        super.clear();
        this.courseInfo = null;
        this.courseSet = null;
        this.multipleCourseType = new String();
        this.programType = null;
        this.gradeScale = null;
        this.orgInfo = null;
        this.programSet = null;
        this.termCode = null;
        this.termCode2 = null;
        this.termInfo = null;
        this.termInfo2 = null;
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

    public CluSetInformation getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(CluSetInformation courseSet) {
        this.courseSet = courseSet;
    }

    public CluSetInformation getCourseSetParent() {
        return courseSetParent;
    }

    public void setCourseSetParent(CluSetInformation courseSetParent) {
        this.courseSetParent = courseSetParent;
    }

    public String getGradeScale() {
        return gradeScale;
    }

    public void setGradeScale(String gradeScale) {
        this.gradeScale = gradeScale;
    }

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    public CluSetInformation getProgramSet() {
        return programSet;
    }

    public void setProgramSet(CluSetInformation programSet) {
        this.programSet = programSet;
    }

    public CluSetInformation getProgramSetParent() {
        return programSetParent;
    }

    public void setProgramSetParent(CluSetInformation programSetParent) {
        this.programSetParent = programSetParent;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public TermInfo getTermInfo(){
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo){
        this.termInfo = termInfo;
    }

    public TermInfo getTermInfo2() {
        return termInfo2;
    }

    public void setTermInfo2(TermInfo termInfo2) {
        this.termInfo2 = termInfo2;
    }

    public String getTermCode2() {
        return termCode2;
    }

    public void setTermCode2(String termCode2) {
        this.termCode2 = termCode2;
    }

    public CluSetInformation getCluSets() {
        return cluSets;
    }

    public void setCluSets(CluSetInformation cluSets) {
        this.cluSets = cluSets;
    }

    public CluSetRangeWrapper getCluSetRange() {
        return cluSetRange;
    }

    public void setCluSetRange(CluSetRangeWrapper cluSetRange) {
        this.cluSetRange = cluSetRange;
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new LUPropositionEditor(definition);
    }

    @Override
    public Map<String, String> getNlParameters() {
        Map<String, String> nlParameters = super.getNlParameters();
        if (this.getCourseSet() != null){
            nlParameters.put(CLULIST_KEY, this.getCourseSet().getCluDelimitedString());
            nlParameters.put(CLUSETLIST_KEY, this.getCourseSet().getCluSetDelimitedString());
        }
        else if (this.getProgramSet() != null)   {
            nlParameters.put(CLULIST_KEY, this.getProgramSet().getCluDelimitedString());
            nlParameters.put(CLUSETLIST_KEY, this.getProgramSet().getCluSetDelimitedString());
        }
        return nlParameters;
    }
}
