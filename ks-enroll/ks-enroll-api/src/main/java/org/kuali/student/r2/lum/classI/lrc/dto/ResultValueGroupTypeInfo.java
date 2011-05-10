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

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Type;
import org.kuali.student.r2.lum.classI.lrc.ResultValueGroupType;

/**
 * Detailed information about a single result component type.
 * 
 * 
 * @Author sambit
 * @Since
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultValueGroupTypeInfo implements ResultValueGroupType {

	private static final long serialVersionUID = 1L;

	private String name;

	private String key;

	private String descr;

	private String id;

	private List<? extends Attribute> attributes;

	private Date effectiveDate;

	private Date expirationDate;

	private String refObjectURI;

	public ResultValueGroupTypeInfo() {
	}

	public ResultValueGroupTypeInfo(String name, String key, String descr,
			String id, List<? extends Attribute> list, Date effectiveDate,
			Date expirationDate, String refObjectURI) {
		
		this.name = name;
		this.key = key;
		this.descr = descr;
		this.id = id;
		this.attributes = list;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.refObjectURI = refObjectURI;
	}

	public static ResultValueGroupTypeInfo createNewResultValueGroupTypeInfoFromResultValueGroupTypeInfo(
			ResultValueGroupTypeInfo resultValueGroupTypeInfo) {
		
		return new ResultValueGroupTypeInfo(resultValueGroupTypeInfo.getName(),resultValueGroupTypeInfo.getKey(), 
				resultValueGroupTypeInfo.getDescr(), resultValueGroupTypeInfo.getId(), resultValueGroupTypeInfo.getAttributes(),
				resultValueGroupTypeInfo.getEffectiveDate(),resultValueGroupTypeInfo.getExpirationDate(), resultValueGroupTypeInfo.getRefObjectURI());
	
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setRefObjectURI(String refObjectURI) {
		this.refObjectURI = refObjectURI;
	}

	@Override
	public String getRefObjectURI() {

		return this.refObjectURI;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public List<? extends Attribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

	@Override
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	@Override
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	@Override
	public String getId() {
		return this.id;
	}

}
