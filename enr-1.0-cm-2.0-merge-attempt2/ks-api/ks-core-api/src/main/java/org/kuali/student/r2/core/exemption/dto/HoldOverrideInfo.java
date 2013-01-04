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

import org.kuali.student.r2.core.exemption.infc.HoldOverride;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldOverrideInfo", propOrder = { "holdId"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code })

public class HoldOverrideInfo implements HoldOverride, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String holdId;

//  TODO KSCM-372: Non-GWT translatable code
//	@XmlAnyElement
//	private List<Element> _futureElements;

	public HoldOverrideInfo() {
		super();
		holdId = null;
//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	/**
	 * Constructs a new HoldOverrideInfo from another HoldOverride.
	 * 
	 * @param exemption the HoldOverride to copy
	 */
	public HoldOverrideInfo(HoldOverride holdOverride) {
		super();
		if (null != holdOverride) {
			this.holdId = holdOverride.getHoldId();
		}

//	    TODO KSCM-372: Non-GWT translatable code
//		_futureElements = null;
	}

	@Override
	public String getHoldId() {
	    return holdId;
	}

	public void setHoldId(String holdId) {
	    this.holdId = holdId;
	}
}
