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
package org.kuali.student.enrollment.exemption.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.exemption.infc.HoldOverride;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldOverrideInfo", propOrder = { "id", "typeKey", "stateKey", "name",
                "descr", "holdId", "meta", "attributes", "_futureElements" })

public class HoldOverrideInfo implements HoldOverride, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String holdId;

	@XmlAnyElement
	private List<Element> _futureElements;

	public HoldOverrideInfo() {
		super();
		holdId = null;
		_futureElements = null;
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

		_futureElements = null;
	}

	@Override
	public String getHoldId() {
	    return holdId;
	}

	public void setHoldId(String holdId) {
	    this.holdId = holdId;
	}
}
