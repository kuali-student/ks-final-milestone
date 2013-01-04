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

package org.kuali.student.r1.core.document.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;
@Deprecated
@Entity
@Table(name = "KSDO_DOCUMENT_TYPE")

public class DocumentType extends Type<DocumentTypeAttribute> {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<DocumentTypeAttribute> attributes;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "type",targetEntity=Document.class)
    private List<Document> documents;
    

    
    @Override
    public List<DocumentTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<DocumentTypeAttribute> attributes) {
        this.attributes=attributes;
    }
    
    public List<Document> getDocument(){
        return documents;
    }
    
    public void setDocument(List<Document> documents){
        this.documents=documents;
    }
    


}
