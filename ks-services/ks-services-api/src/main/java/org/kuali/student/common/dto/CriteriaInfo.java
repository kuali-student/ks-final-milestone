/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.common.dto;

import org.kuali.student.common.infc.CriteriaInfc;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Query to return some information regarding LUI to person relationships.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:54:06 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationCriteria+Structure">LuiPersonRelationCriteria</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CriteriaInfo implements CriteriaInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String fieldKey;

    @XmlElement
    private final String operator;

    @XmlElement
    private final String value;

    private CriteriaInfo() {
    	fieldKey = null;
    	operator = null;
    	value = null;
    }
    	
    private CriteriaInfo(CriteriaInfc builder) {
		this.fieldKey = builder.getFieldKey();
		this.operator = builder.getOperator();
		this.value = builder.getValue();
	}

	/**
     * Name: Field Key
     * <p/>
     * Dot path notation to identity the name of field to be compared
     */
    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    /**
     * Name: Criteria Value
     * <p/>
     * Value to be compared
     */
    @Override
    public String getValue() {
        return value;
    }
    
    public static class Builder implements CriteriaInfc {
    	private String fieldKey;
		private String operator;
		private String value;
		
		public Builder() {}
		
    	public Builder(CriteriaInfc critInfo) {
    		this.fieldKey = critInfo.getFieldKey();
    		this.operator = critInfo.getOperator();
    		this.value = critInfo.getValue();
    	}
    	
    	public CriteriaInfo build() {
    		return new CriteriaInfo(this);
    	}

		@Override
		public String getFieldKey() {
			return fieldKey;
		}

		@Override
		public String getOperator() {
			return operator;
		}

		@Override
		public String getValue() {
			return value;
		}
    }
}