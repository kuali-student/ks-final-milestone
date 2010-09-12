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

package org.kuali.student.core.enumerationmanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="KSEM_ENUM_META_ENT")
public class EnumerationMetaEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="ID")
    String id;

    @Column(name="ENUM_KEY")
    String enumerationKey;
    @Column(name="NAME")
    String name;
    @Column(name="ENUM_META_KEY_DESC")
    String enumerationMetaKeyDesc;
    
    @OneToMany(/*mappedBy="enumerationMetaEntity",*/ fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="ENUM_META_ENT_ID")
    List<EnumeratedValueFieldEntity> enumeratedValueFieldList = new ArrayList<EnumeratedValueFieldEntity>();
    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String key) {
        this.enumerationKey = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnumerationMetaKeyDesc() {
        return enumerationMetaKeyDesc;
    }

    public void setEnumerationMetaKeyDesc(String desc) {
        this.enumerationMetaKeyDesc = desc;
    }
    public List<EnumeratedValueFieldEntity> getEnumeratedValueFieldList() {
        return enumeratedValueFieldList;
    }
    public void setEnumeratedValueFieldList(List<EnumeratedValueFieldEntity> enumeratedValueFieldList) {
        this.enumeratedValueFieldList = enumeratedValueFieldList;
    }

}
