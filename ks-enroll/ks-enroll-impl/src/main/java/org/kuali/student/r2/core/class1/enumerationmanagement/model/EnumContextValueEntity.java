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

package org.kuali.student.r2.core.class1.enumerationmanagement.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.r2.common.entity.BaseVersionEntity;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumContextValue;

@Entity
@Table(name="KSEM_CTX_T", uniqueConstraints={@UniqueConstraint(columnNames={"CTX_KEY", "CTX_VAL"})})
public class EnumContextValueEntity extends BaseVersionEntity {

    @Column(name="CTX_KEY")
    String contextKey;
    
    @Column(name="CTX_VAL")
    String contextValue;

    @ManyToMany(mappedBy="contextValueEntities", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<EnumeratedValueEntity> enumeratedValueList;
    
    public EnumContextValueEntity() {
    }

    public EnumContextValueEntity(EnumContextValue enumContextValue) {
        //super(enumContextValue);
        this.setContextKey(enumContextValue.getKey());
        this.setContextValue(enumContextValue.getValue());
        //this.setEnumeratedValueList(enumContextValue)
    }
    
    public String getContextKey() {
        return contextKey;
    }

    public void setContextKey(String type) {
        this.contextKey = type;
    }

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String value) {
        this.contextValue = value;
    }
    
	public List<EnumeratedValueEntity> getEnumeratedValueList() {
		return enumeratedValueList;
	}
	public void setEnumeratedValueList(
			List<EnumeratedValueEntity> enumeratedValueList) {
		this.enumeratedValueList = enumeratedValueList;
	}
    
}
