/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.HasTypeState;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.program.infc.ProgramRequirement;

/**
 * Detailed information about a program requirement
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:56:20 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/programRequirementInfo+Structure">ProgramRequirementInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramRequirementInfo extends IdEntityInfo implements ProgramRequirement {

    private static final long serialVersionUID = 1L;

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
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }


    public List<LoDisplayInfo> getLearningObjectives() {
        if(null == learningObjectives) {
            learningObjectives = new ArrayList<LoDisplayInfo>(0);
        }
        return learningObjectives;
    }

    public void setLearningObjectives(List<LoDisplayInfo> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public StatementTreeViewInfo getStatement() {
        return statement;
    }

    public void setStatement(StatementTreeViewInfo statement) {
        this.statement = statement;
    }
    
    public Integer getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(Integer minCredits) {
        this.minCredits = minCredits;
    }

    public Integer getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }
}
