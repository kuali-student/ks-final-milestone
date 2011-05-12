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

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.Type;
import org.kuali.student.r2.lum.lrc.infc.ResultValueGroupType;

/**
 * Detailed information about a single result component type.
 * 
 * 
 * @Author sambit
 * @Since
 */
@XmlType(name = "ResultValueGroupInfo", propOrder = { "key", "typeKey",
		"stateKey", "name", "descr", "effectiveDate", "expirationDate", "id",
		"metaInfo", "attributes" })
public class ResultValueGroupTypeInfo extends KeyEntityInfo implements
		ResultValueGroupType {

	private static final long serialVersionUID = 1L;

	private String id;

	private Date effectiveDate;

	private Date expirationDate;

	public ResultValueGroupTypeInfo() {
	}

	public ResultValueGroupTypeInfo(String name, String key, RichText descr,
			String typeKey, String stateKey, String id,
			List<? extends Attribute> list, Date effectiveDate,
			Date expirationDate, Meta metaInfo) {
		//TODO once devs make objects mutable
		super();
		this.id = id;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;

	}

	public static ResultValueGroupTypeInfo createNewResultValueGroupTypeInfoFromResultValueGroupTypeInfo(
			ResultValueGroupTypeInfo resultValueGroupTypeInfo) {

		return new ResultValueGroupTypeInfo(resultValueGroupTypeInfo.getName(),
				resultValueGroupTypeInfo.getKey(),
				resultValueGroupTypeInfo.getDescr(),
				resultValueGroupTypeInfo.getTypeKey(),
				resultValueGroupTypeInfo.getStateKey(),
				resultValueGroupTypeInfo.getId(),
				resultValueGroupTypeInfo.getAttributes(),
				resultValueGroupTypeInfo.getEffectiveDate(),
				resultValueGroupTypeInfo.getExpirationDate(),
				resultValueGroupTypeInfo.getMetaInfo());

	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
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
