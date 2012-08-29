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
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.exemption.infc.LearningResultOverride;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningResultOverrideInfo", propOrder = { "lrrIds"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class LearningResultOverrideInfo implements LearningResultOverride, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private List<String> lrrIds;

//  TODO KSCM-372: Non-GWT translatable code
//	@XmlAnyElement
//	private List<Element> _futureElements;

	public LearningResultOverrideInfo() {
		super();
		lrrIds = null;
		
//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	/**
	 * Constructs a new LearningResultOverrideInfo from another
	 * LearningResultOverride.
	 * 
	 * @param exemption the LearningResultOverride to copy
	 */
	public LearningResultOverrideInfo(LearningResultOverride learningResultOverride) {
		super();
		if (null != learningResultOverride) {
		    this.lrrIds = new ArrayList(learningResultOverride.getLrrIds());
		}

//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	@Override
	public List<String> getLrrIds() {
	    return lrrIds;
	}

	public void setLrrIds(List<String> lrrIds) {
	    this.lrrIds = lrrIds;
	}
}
