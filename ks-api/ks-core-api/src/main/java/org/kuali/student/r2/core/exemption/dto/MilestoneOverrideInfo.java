/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.exemption.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.exemption.infc.MilestoneOverride;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MilestoneOverrideInfo", propOrder = { "milestoneId", 
                 "effectiveMilestoneId", "_futureElements" }) 

public class MilestoneOverrideInfo implements MilestoneOverride, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String milestoneId;

	@XmlElement
	private String effectiveMilestoneId;

    @XmlAnyElement
    private List<Object> _futureElements;  

	public MilestoneOverrideInfo() {
		super();
		milestoneId = null;
		effectiveMilestoneId = null;
		
		_futureElements = null;
	}

	/**
	 * Constructs a new MilestoneOverrideInfo from another
	 * MilestoneOverride.
	 * 
	 * @param exemption the MilestoneOverride to copy
	 */
	public MilestoneOverrideInfo(MilestoneOverride milestoneOverride) {
		super();
		if (null != milestoneOverride) {
		    this.milestoneId = milestoneOverride.getMilestoneId();
		    this.effectiveMilestoneId = milestoneOverride.getEffectiveMilestoneId();
		}

		_futureElements = null;
	}

	@Override
	public String getMilestoneId() {
	    return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
	    this.milestoneId = milestoneId;
	}

	@Override
	public String getEffectiveMilestoneId() {
	    return effectiveMilestoneId;
	}

	public void setEffectiveMilestoneId(String effectiveMilestoneId) {
	    this.effectiveMilestoneId = effectiveMilestoneId;
	}
}
