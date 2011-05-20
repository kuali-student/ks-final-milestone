/**
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

package org.kuali.student.r2.lum.lrc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultRankingPair;
import org.kuali.student.r2.lum.lrc.infc.ResultValueGroup;
import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;

/**
 * Detailed information about a result component.
 * 
 * @Author sambit
 * @Since Tue Apr 21 13:47:47 PDT 2009
 */

@XmlType(name = "ResultValueGroupInfo", propOrder = { "key", "typeKey",
		"stateKey", "name", "descr", "resultValues", "effectiveDate","resultRankingPairs",
		"expirationDate", "id", "resultValueRange", "meta", "attributes" })
public class ResultValueGroupInfo extends KeyEntityInfo implements
		ResultValueGroup {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private List<String> resultValues;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	private List<ResultRankingPairInfo> resultRankingPairs;

	@XmlAttribute
	private String id;

	@XmlElement
	private ResultValueRangeInfo resultValueRange;

	public ResultValueGroupInfo() {

	}

	public ResultValueGroupInfo(ResultValueGroup resultValueGroupInfo) {
		super(resultValueGroupInfo);
		if (null != resultValueGroupInfo) {
			this.resultValues = new ArrayList<String>(
					resultValueGroupInfo.getResultValues());
			this.effectiveDate = new Date(resultValueGroupInfo.getEffectiveDate().getTime());
			this.expirationDate = new Date(resultValueGroupInfo.getExpirationDate().getTime());
			this.resultValueRange = new ResultValueRangeInfo(resultValueGroupInfo.getResultValueRange()); 
			//this.resultRankingPairs =  	resultValueGroupInfo.getResultRankingPairs();
		}

	}

	public List<String> getResultValues() {
		if (resultValues == null) {
			resultValues = new ArrayList<String>(0);
		}
		return resultValues;
	}

	public void setResultValues(List<String> resultValues) {
		this.resultValues = resultValues;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<? extends ResultRankingPair> getResultRankingPairs() {
		return resultRankingPairs;
	}

	public void setResultRankingPairs(List<ResultRankingPairInfo> resultRankings) {
		this.resultRankingPairs = resultRankings;
	}

	public void setResultValueRange(ResultValueRangeInfo resultValueRange) {
		this.resultValueRange = resultValueRange;
	}

	public ResultValueRange getResultValueRange() {
		return resultValueRange;
	}

}
