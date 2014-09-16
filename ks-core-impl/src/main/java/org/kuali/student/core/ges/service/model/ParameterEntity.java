/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.ges.service.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.infc.Parameter;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;


@Entity
@Table(name = "KSEN_GES_PARM")
@NamedQueries({
        @NamedQuery(name = "ParameterEntity.getIdsByType", query = "select id from ParameterEntity where typeKey = :type"),
        @NamedQuery(name = "ParameterEntity.getParameterKeysForParameterGroup", query = "select p.id from ParameterEntity p join p.parameterGroups g where g.id = :paramGrpKey")
})
public class ParameterEntity extends MetaEntity
        implements AttributeOwner<ParameterAttributeEntity> {

    public static final String PARAMETER_QUERY_GET_IDS_BY_TYPE = "ParameterEntity.getIdsByType";
    public static final String PARAMETER_QUERY_GET_PARAM_KEYS_FOR_PARAMGRP_KEY = "ParameterEntity.getParameterKeysForParameterGroup";

    ////////////////////
    // DATA FIELDS
    ////////////////////
    @Column(name = "GES_PARM_TYPE", nullable = false)
    private String typeKey;

    @Column(name = "GES_PARM_STATE", nullable = false)
    private String stateKey;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = true)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = true)
    private String descrFormatted;

    @Enumerated(EnumType.STRING)
    @Column(name = "GES_VALUE_TYPE_ID", nullable = false)
    private org.kuali.student.core.ges.infc.GesValueTypeEnum gesGesValueTypeEnum;

    @Column(name = "REQ_UNIQUE_PRIORITY_IND", nullable = true)
    private Boolean requireUniquePriorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<ParameterAttributeEntity> attributes = new HashSet<ParameterAttributeEntity>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "parameters")
    private Set<ParameterGroupEntity> parameterGroups;


    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////


    public ParameterEntity() {
        super();
    }

    public ParameterEntity(Parameter dto) {
        super(dto);
        this.setId(dto.getKey());
        this.setTypeKey(dto.getTypeKey());
        this.fromDto(dto);
    }

    public void fromDto(Parameter dto) {
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

        setGesValueTypeEnum(dto.getGesValueTypeEnum());
        setRequireUniquePriorities(dto.getRequireUniquePriorities());

        // dynamic attributes
        this.getAttributes().clear();
        for (Attribute att : dto.getAttributes()) {
            ParameterAttributeEntity attEntity = new ParameterAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public ParameterInfo toDto() {
        ParameterInfo info = new ParameterInfo();
        info.setKey(getId());
        info.setTypeKey(this.getTypeKey());
        info.setStateKey(this.getStateKey());
        info.setName(this.getName());
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        info.setGesValueTypeEnum(this.getGesGesValueTypeEnum());
        info.setRequireUniquePriorities(this.getRequireUniquePriorities());
        info.setMeta(super.toDTO());

        // dynamic attributes
        for (ParameterAttributeEntity att : getAttributes()) {
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

    public org.kuali.student.core.ges.infc.GesValueTypeEnum getGesGesValueTypeEnum() {
        return this.gesGesValueTypeEnum;
    }

    public void setGesValueTypeEnum(org.kuali.student.core.ges.infc.GesValueTypeEnum gesGesValueTypeEnum) {
        this.gesGesValueTypeEnum = gesGesValueTypeEnum;
    }

    public Boolean getRequireUniquePriorities() {
        return this.requireUniquePriorities;
    }

    public void setRequireUniquePriorities(Boolean requireUniquePriorities) {
        this.requireUniquePriorities = requireUniquePriorities;
    }

    public Set<ParameterGroupEntity> getParameterGroups() {
        return parameterGroups;
    }

    public void setParameterGroups(Set<ParameterGroupEntity> parameterGroups) {
        this.parameterGroups = parameterGroups;
    }

    @Override
    public void setAttributes(Set<ParameterAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    @Override
    public Set<ParameterAttributeEntity> getAttributes() {
        return attributes;
    }


}

