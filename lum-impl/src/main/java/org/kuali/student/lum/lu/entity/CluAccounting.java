/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.lu.entity;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@Entity
@Table(name = "KS_CLU_ACCOUNTING_T")
public class CluAccounting implements AttributeOwner<CluAccountingAttribute> {

    @Id
    @Column(name = "ID")
    private String id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAccountingAttribute> attributes;
    
    public List<CluAccountingAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CluAccountingAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<CluAccountingAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}