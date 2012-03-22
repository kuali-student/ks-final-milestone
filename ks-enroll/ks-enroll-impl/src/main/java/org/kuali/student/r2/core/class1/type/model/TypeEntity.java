/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.type.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.infc.Type;

@Entity
@Table(name = "KSEN_TYPE")
@NamedQueries({@NamedQuery(name = "Type.GetByRefObjectUri", query = "select type.refObjectURI from TypeEntity type where type.refObjectURI=:refObjectURI"),@NamedQuery(name = "Type.GetAllRefObjectUris", query = "select refObjectURI from TypeEntity")})
public class TypeEntity extends MetaEntity implements AttributeOwner<TypeAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "REF_OBJECT_URI")
    private String refObjectURI;

    @Column(name = "DESCR_PLAIN")
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED")
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TypeAttributeEntity> attributes ;

    public TypeEntity(){}

    public TypeEntity(Type type){
        

        super(type);
        this.setName(type.getName());
        this.setAttributes(new ArrayList<TypeAttributeEntity>());
        if (null != type.getAttributes()) {
            for (Attribute att : type.getAttributes()) {
                this.getAttributes().add(new TypeAttributeEntity(att, this));
            }
        }
    
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeAttributeEntity> getAttributes() {
        return attributes;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

 

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    public String getRefObjectURI() {
        return refObjectURI;
    }
    
    @Override
    public void setAttributes(List<TypeAttributeEntity> attributes) {
        this.attributes = attributes;
        
    }

    public TypeInfo toDto() {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName(this.getName());
        typeInfo.setKey(this.getId());
        typeInfo.setRefObjectUri(getRefObjectURI());
        typeInfo.setAttributes(new ArrayList<AttributeInfo>());
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (BaseAttributeEntity<?> att : this.getAttributes()) {
            atts.add(att.toDto());
        }
        typeInfo.setAttributes(atts);
        return typeInfo;
    }

   


}
