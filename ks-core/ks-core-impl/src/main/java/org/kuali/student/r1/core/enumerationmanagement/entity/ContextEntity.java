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

package org.kuali.student.r1.core.enumerationmanagement.entity;

import org.kuali.student.r1.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="KSEM_CTX_T", uniqueConstraints={@UniqueConstraint(columnNames={"CTX_KEY", "CTX_VAL"})})
public class ContextEntity extends BaseEntity {
    
    @Column(name="CTX_KEY")
    String contextKey;
    
    @Column(name="CTX_VAL")
    String contextValue;

    @ManyToMany(mappedBy="contextEntityList", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<EnumeratedValue> enumeratedValueList;
    
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
    
	public List<EnumeratedValue> getEnumeratedValueList() {
		return enumeratedValueList;
	}
	public void setEnumeratedValueList(
			List<EnumeratedValue> enumeratedValueList) {
		this.enumeratedValueList = enumeratedValueList;
	}
    
}
