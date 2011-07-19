/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.r2.lum.lrc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.w3c.dom.Element;

/**
 * Detailed information about a group of result values.
 * 
 * This grouping can be expressed two ways:
 * (1) as an explicit list of values, i.e. A, B, C, etc
 * (2) as a range of numeric values 1-100 with .01 increments
 * 
 * It may also combine the two approaches.
 * (3) A numeric range for a grade 1-100 but also allow for grades like I for incomplete.
 * 
 * Note: This object has been renamed from R1, previously it was called ResultComponent.
 * 
 * @Author sambit
 * @Since Tue Apr 21 13:47:47 PDT 2009
 */

@XmlType(name = "ResultValuesGroupInfo", propOrder = { "id", "typeKey",
		"stateKey", "name", "descr", "resultValueIds", "resultValueRange", "effectiveDate",
		"expirationDate", "meta", "attributes", "_futureElements" })
public class ResultValuesGroupInfo extends IdEntityInfo implements
		ResultValuesGroup {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private List<String> resultValueIds;

    @XmlElement
    private ResultValueRangeInfo resultValueRange;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

	public ResultValuesGroupInfo() {

	}

	public ResultValuesGroupInfo(ResultValuesGroup resultValueGroupInfo) {
		super(resultValueGroupInfo);
		if (null != resultValueGroupInfo) {
			this.resultValueIds = new ArrayList<String>(
					resultValueGroupInfo.getResultValueIds());
            this.resultValueRange = new ResultValueRangeInfo(resultValueGroupInfo.getResultValueRange());
			this.effectiveDate = new Date(resultValueGroupInfo.getEffectiveDate().getTime());
			this.expirationDate = new Date(resultValueGroupInfo.getExpirationDate().getTime());
 		}
	}

	@Override
	public List<String> getResultValueIds() {
		if (resultValueIds == null) {
			resultValueIds = new ArrayList<String>(0);
		}
		return resultValueIds;
	}

	public void setResultValueIds(List<String> resultValueIds) {
		this.resultValueIds = resultValueIds;
	}

	@Override
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setResultValueRange(ResultValueRangeInfo resultValueRange) {
		this.resultValueRange = resultValueRange;
	}

	@Override
	public ResultValueRangeInfo getResultValueRange() {
		return resultValueRange;
	}

}
