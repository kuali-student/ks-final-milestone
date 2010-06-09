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

package org.kuali.student.core.enumerationmanagement.bo;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;


@Entity
@Table(name="KSEM_CTX_T", uniqueConstraints={@UniqueConstraint(columnNames={"CTX_KEY", "CTX_VAL"})})
public class EnumerationContext extends PersistableBusinessObjectBase {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    Long id;
    
    Long enumerationTableId;
    
    @Column(name="CTX_KEY")
    String contextKey;
    
    @Column(name="CTX_VAL")
    String contextValue;

    List<EnumerationContextValue> enumerationContextValueList;
    
    
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
        
        toStringMap.put("id", id);
        toStringMap.put("contextKey", contextKey);
        toStringMap.put("contextValue", contextValue);
        
        return toStringMap;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
	public List<EnumerationContextValue> getEnumerationContextValueList() {
		return enumerationContextValueList;
	}
	
	public void setEnumerationContextValueList(List<EnumerationContextValue> enumerationContextValueList) {
		this.enumerationContextValueList = enumerationContextValueList;
	}

    public Long getEnumerationTableId() {
        return enumerationTableId;
    }

    public void setEnumerationTableId(Long enumerationTableId) {
        this.enumerationTableId = enumerationTableId;
    }
    
}
