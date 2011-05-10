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

package org.kuali.student.r2.lum.classI.lrc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.classI.lrc.ResultValueGroup;
import org.kuali.student.r2.lum.classI.lrc.ResultValueGroupType;

/**
 * Detailed information about a result component.
 * 
 * @Author sambit
 * @Since Tue Apr 21 13:47:47 PDT 2009
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ResultValueGroupInfo extends KeyEntityInfo implements
		ResultValueGroup {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String name;

	@XmlElement
	private String desc;

	@XmlElement
	private List<String> resultValues;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlAttribute
	private ResultValueGroupType type;

	@XmlAttribute
	private String id;

	public ResultValueGroupInfo() {

	}

	public ResultValueGroupInfo(String id, String name, String desc,
			List<String> resultValues, Date effectiveDate, Date expirationDate,
			ResultValueGroupType type) {

		this.name = name;
		this.desc = desc;
		this.resultValues = resultValues;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.type = type;
		this.id = id;
	}

	public static ResultValueGroupInfo createNewResultValueGroupInfoFromResultValueGroupInfo(
			ResultValueGroupInfo resultValueGroupInfo) {
		return new ResultValueGroupInfo(resultValueGroupInfo.getId(),
				resultValueGroupInfo.getName(), resultValueGroupInfo.getDesc(),
				resultValueGroupInfo.getResultValues(),
				resultValueGroupInfo.getEffectiveDate(),
				resultValueGroupInfo.getExpirationDate(),
				resultValueGroupInfo.getType());
	}

	/**
	 * Friendly name of the result component
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Narrative description of the result component
	 */
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * List of result values
	 */
	public List<String> getResultValues() {
		if (resultValues == null) {
			resultValues = new ArrayList<String>(0);
		}
		return resultValues;
	}

	public void setResultValues(List<String> resultValues) {
		this.resultValues = resultValues;
	}

	/**
	 * Date and time that this result component became effective. This is a
	 * similar concept to the effective date on enumerated values. When an
	 * expiration date has been specified, this field must be less than or equal
	 * to the expiration date.
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Date and time that this result component expires. This is a similar
	 * concept to the expiration date on enumerated values. If specified, this
	 * should be greater than or equal to the effective date. If this field is
	 * not specified, then no expiration date has been currently defined and
	 * should automatically be considered greater than the effective date.
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Unique identifier for a result component type.
	 */
	public ResultValueGroupType getType() {
		return type;
	}

	public void setType(ResultValueGroupType type) {
		this.type = type;
	}

	/**
	 * Unique identifier for a result component. This is optional, due to the
	 * identifier being set at the time of creation. Once the result component
	 * has been created, this should be seen as required.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
