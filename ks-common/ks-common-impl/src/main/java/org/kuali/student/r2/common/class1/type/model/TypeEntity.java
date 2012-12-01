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
package org.kuali.student.r2.common.class1.type.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.infc.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_TYPE")
@NamedQueries({
    @NamedQuery(name = "Type.GetByRefObjectUri", query = "select type from TypeEntity type where type.refObjectURI=:refObjectURI"),
    @NamedQuery(name = "Type.GetAllRefObjectUris", query = "select distinct refObjectURI from TypeEntity")})
@AttributeOverrides({
    @AttributeOverride(name = "id", column =
    @Column(name = "TYPE_KEY"))})
public class TypeEntity extends MetaEntity implements AttributeOwner<TypeAttributeEntity> {

    @Column(name = "NAME")
    private String name;
    @Column(name = "REF_OBJECT_URI")
    private String refObjectURI;
    @Column(name= "SERVICE_URI")
    private String serviceUri;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable=false)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<TypeAttributeEntity> attributes = new HashSet<TypeAttributeEntity>();

    public TypeEntity() {
    }

    public TypeEntity(Type type) {
        super(type);
        this.setId(type.getKey());
        fromDto(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TypeAttributeEntity> getAttributes() {
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

    public String getServiceUri(){
        return serviceUri;
    }

    public void setServiceUri(String serviceUri){
        this.serviceUri=serviceUri;
    }

    public void setAttributes(Set<TypeAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void fromDto(Type type) {
        // NOTE: readonly fields are set only in the constructor above
        this.setName(type.getName());
        if (type.getDescr() == null) {
            this.setDescrPlain(null);
            this.setDescrFormatted(null);
        } else {
            this.setDescrPlain(type.getDescr().getPlain());
            this.setDescrFormatted(type.getDescr().getFormatted());
        }

        this.setRefObjectURI(type.getRefObjectUri());
        this.setServiceUri(type.getServiceUri());
        this.setEffectiveDate(type.getEffectiveDate());
        this.setExpirationDate(type.getExpirationDate());
        this.setAttributes(new HashSet<TypeAttributeEntity>());
        for (Attribute att : type.getAttributes()) {
            this.getAttributes().add(new TypeAttributeEntity(att, this));
        }
    }

    public TypeInfo toDto() {
        TypeInfo info = new TypeInfo();
        info.setKey(getId());
        info.setName(name);
        info.setRefObjectUri(refObjectURI);
        info.setServiceUri(serviceUri);
        RichTextInfo rti = new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted);
        info.setDescr(rti);
        info.setEffectiveDate(this.effectiveDate);
        info.setExpirationDate(this.expirationDate);
        info.setMeta(super.toDTO());
        if (attributes != null) {
            for (TypeAttributeEntity att : attributes) {
                info.getAttributes().add(att.toDto());
            }
        }
        return info;
    }
}
