/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.infc.ProgramRequirement;

/**
 * Detailed information about a program requirement
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "ProgramRequirementInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "descr",
    "shortTitle",
    "longTitle",
    "learningObjectives",
    "statement",
    "minCredits",
    "maxCredits",
    "meta",
    "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
    
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramRequirementInfo extends IdNamelessEntityInfo implements ProgramRequirement, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private RichTextInfo descr;
    @XmlElement
    private String shortTitle;
    @XmlElement
    private String longTitle;
    @XmlElement
    private List<LoDisplayInfo> learningObjectives;
    @XmlElement
    private StatementTreeViewInfo statement;
    @XmlElement
    private Integer minCredits;
    @XmlElement
    private Integer maxCredits;
    

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ProgramRequirementInfo() {
    }

    public ProgramRequirementInfo(ProgramRequirement programRequirement) {
        super(programRequirement);
        if (programRequirement != null) {
            if (programRequirement.getDescr() != null) {
                this.descr = new RichTextInfo (programRequirement.getDescr());
            }
            this.shortTitle = programRequirement.getShortTitle();
            this.longTitle = programRequirement.getLongTitle();
            this.statement = programRequirement.getStatement();
            this.minCredits = programRequirement.getMinCredits();
            this.maxCredits = programRequirement.getMaxCredits();
            List<LoDisplayInfo> los = new ArrayList<LoDisplayInfo>();
            if (programRequirement.getLearningObjectives() != null) {
                for (LoDisplayInfo loDisplay : programRequirement.getLearningObjectives()) {
                    los.add(new LoDisplayInfo(loDisplay));
                }
            }
        }
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    @Override
    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    @Override
    public List<LoDisplayInfo> getLearningObjectives() {
        if (null == learningObjectives) {
            learningObjectives = new ArrayList<LoDisplayInfo>(0);
        }
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    @Override
    public StatementTreeViewInfo getStatement() {
        return statement;
    }

    public void setStatement(StatementTreeViewInfo statement) {
        this.statement = statement;
    }

    @Override
    public Integer getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(Integer minCredits) {
        this.minCredits = minCredits;
    }

    @Override
    public Integer getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }



}
