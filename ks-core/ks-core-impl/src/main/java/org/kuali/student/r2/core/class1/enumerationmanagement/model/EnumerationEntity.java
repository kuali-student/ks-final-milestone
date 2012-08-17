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

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.infc.Enumeration;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_ENUM_T")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="ENUM_KEY"))})
public class EnumerationEntity extends MetaEntity implements AttributeOwner<EnumerationAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String plain;

    @Column(name = "ENUM_TYPE", nullable = false)
    private String enumerationType;

    @Column(name = "ENUM_STATE", nullable = false)
    private String enumerationState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<EnumerationAttributeEntity> attributes = new HashSet<EnumerationAttributeEntity>();

    public EnumerationEntity() {}

    public EnumerationEntity(Enumeration enumeration) {
        super(enumeration);
        this.setId(enumeration.getKey());
        this.setName(enumeration.getName());
        if (enumeration.getDescr() != null) {
            this.setDescrFormatted(enumeration.getDescr().getFormatted());
            this.setDescrPlain(enumeration.getDescr().getPlain());
        }
        if (enumeration.getStateKey() != null) {
            this.setEnumerationState(enumeration.getStateKey());
        }
        this.setAttributes(new HashSet<EnumerationAttributeEntity>());
        if (null != enumeration.getAttributes()) {
            for (Attribute att : enumeration.getAttributes()) {
                this.getAttributes().add(new EnumerationAttributeEntity(att, this));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public String getEnumerationType() {
        return enumerationType;
    }

    public void setEnumerationType(String enumerationType) {
        this.enumerationType = enumerationType;
    }

    public String getEnumerationState() {
        return enumerationState;
    }

    public void setEnumerationState(String enumerationState) {
        this.enumerationState = enumerationState;
    }

    @Override
    public void setAttributes(Set<EnumerationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<EnumerationAttributeEntity> getAttributes() {
        return attributes;
    }

    public EnumerationInfo toDto() {
        EnumerationInfo enumeration = new EnumerationInfo();
        enumeration.setKey(getId());
        enumeration.setName(name);
        enumeration.setTypeKey(enumerationType);
        enumeration.setStateKey(enumerationState);
        enumeration.setMeta(super.toDTO());

        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(getDescrPlain());
        rti.setFormatted(getDescrFormatted());
        enumeration.setDescr(rti);

        List<AttributeInfo> enumerations = new ArrayList<AttributeInfo>();
        for (EnumerationAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            enumerations.add(attInfo);
        }
        enumeration.setAttributes(enumerations);

        return enumeration;
    }

}
