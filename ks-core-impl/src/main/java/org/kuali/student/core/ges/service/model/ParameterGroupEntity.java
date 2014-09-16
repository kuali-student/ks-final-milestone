/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by David Yin on 9/8/14
 */
package org.kuali.student.core.ges.service.model;

import org.kuali.student.core.ges.dto.ParameterGroupInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.infc.Parameter;
import org.kuali.student.core.ges.infc.ParameterGroup;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class is the entity mapping class for KSEN_GES_PARM_GRP table
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_GES_PARM_GRP")

public class ParameterGroupEntity extends MetaEntity
        implements AttributeOwner<ParameterGroupAttributeEntity> {

    //public static final String PARAMETER_QUERY_GET_IDS_BY_TYPE = "ParameterEntity.getIdsByType";

    ////////////////////
    // DATA FIELDS
    ////////////////////
    @Column(name = "GES_PARM_GRP_TYPE", nullable = false)
    private String typeKey;

    @Column(name = "GES_PARM_GRP_STATE", nullable = false)
    private String stateKey;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = true)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = true)
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<ParameterGroupAttributeEntity> attributes = new HashSet<ParameterGroupAttributeEntity>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_GES_PARM_GRP_JN_GES_PARM", joinColumns = {@JoinColumn(name = "GES_PARM_GRP_ID")}, inverseJoinColumns = {@JoinColumn(name = "GES_PARM_ID")})
    private Set<ParameterEntity> parameters;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////


    public ParameterGroupEntity() {
        super();
    }

    public ParameterGroupEntity(ParameterGroup dto) {
        super(dto);
        this.setId(dto.getKey());
        this.setTypeKey(dto.getTypeKey());
        this.fromDto(dto);
    }

    public void fromDto(ParameterGroup dto) {
        super.fromDTO(dto);
        setStateKey(dto.getStateKey());
        setName(dto.getName());

        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }

        // dynamic attributes
        this.getAttributes().clear();
        for (Attribute att : dto.getAttributes()) {
            ParameterGroupAttributeEntity attEntity = new ParameterGroupAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public ParameterGroupInfo toDto() {
        ParameterGroupInfo info = new ParameterGroupInfo();
        info.setKey(getId());
        info.setTypeKey(this.getTypeKey());
        info.setStateKey(this.getStateKey());
        info.setName(this.getName());
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        info.setMeta(super.toDTO());

        // dynamic attributes
        for (ParameterGroupAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return this.stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrPlain() {
        return this.descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return this.descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public Set<ParameterEntity> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterEntity> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setAttributes(Set<ParameterGroupAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    @Override
    public Set<ParameterGroupAttributeEntity> getAttributes() {
        return attributes;
    }


}
