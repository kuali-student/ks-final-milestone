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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.lrc.infc.ResultOption;

/**
 * Information about a result option.
 * 
 * @author Sambit
 * @since
 * 
 */
@XmlType(name = "ResultOptionInfo", propOrder = { "key", "typeKey", "stateKey",
		"name", "descr", "resultUsageTypeKey", "resultComponentId",
		"effectiveDate", "expirationDate", "id", "metaInfo", "attributes",
		"_futureElements" })
public class ResultOptionInfo extends KeyEntityInfo implements ResultOption {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String resultUsageTypeKey;

	@XmlAttribute
	private String resultComponentId;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlAttribute
	private String id;
	
	
	public static ResultOptionInfo getInstance(String key, String typeKey, String stateKey, String name, RichText descr,
										        String resultUsageTypeKey, String resultComponentId,
										        Date effectiveDate, Date expirationDate, String id,
										        Meta metaInfo, List<? extends Attribute> attributes) {
        return new ResultOptionInfo(key, typeKey, stateKey, name, descr, resultUsageTypeKey, resultComponentId, effectiveDate, expirationDate, id, metaInfo, attributes);
    }

	private ResultOptionInfo() {

	}

	private ResultOptionInfo(String key, String typeKey, String stateKey, String name, RichText descr,
	                         String resultUsageTypeKey, String resultComponentId,
	                         Date effectiveDate, Date expirationDate, String id,
	                         Meta metaInfo, List<? extends Attribute> attributes) {
		super(name, id, descr, typeKey, stateKey, attributes, metaInfo);
		this.resultUsageTypeKey = resultUsageTypeKey;
		this.resultComponentId = resultComponentId;
		this.effectiveDate = new Date(effectiveDate.getTime());
		this.expirationDate = new Date(expirationDate.getTime());
    }

	@Override
    public String getResultUsageTypeKey() {
		return resultUsageTypeKey;
	}

	public void setResultUsageTypeKey(String resultUsageTypeKey) {
		this.resultUsageTypeKey = resultUsageTypeKey;
	}

	/**
	 * Unique identifier for a result component.
	 */
	@Override
	public String getResultComponentId() {
		return resultComponentId;
	}

	public void setResultComponentId(String resultComponentId) {
		this.resultComponentId = resultComponentId;
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
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}