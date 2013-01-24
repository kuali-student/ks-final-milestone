/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by venkat on 1/22/13
 */
package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.infc.LuiSet;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is the entity representation for <code>KSEN_LUI_SET</code> table. And, this is mapped with the dto <code>LuiSetInfo</code>
 * at class1 and <code>ColocatedOfferingSetInfo</code> at class2 service.
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_LUI_SET")
@NamedQueries({
    @NamedQuery(name="LuiSet.getLuiSetIdsByType", query="Select luiSetEntity.id from LuiSetEntity luiSetEntity where luiSetEntity.luiSetType =:typeKey"),
    @NamedQuery(name="LuiSet.getLuiSetsByLui", query="Select luiSetEntity from LuiSetEntity luiSetEntity where :lui in elements(luiSetEntity.luiIds)")
})
public class LuiSetEntity extends MetaEntity implements AttributeOwner<LuiSetAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "LUI_SET_TYPE")
    private String luiSetType;

    @Column(name = "LUI_SET_STATE")
    private String luiSetState;

    @ElementCollection
    @CollectionTable(name ="KSEN_LUI_SET_LUI",joinColumns = @JoinColumn(name = "LUI_SET_ID"))
    @Column(name="LUI_ID")
    private List<String> luiIds = new ArrayList<String>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval=true, fetch = FetchType.EAGER)
    private Set<LuiSetAttributeEntity> attributes = new HashSet<LuiSetAttributeEntity>();

    public LuiSetEntity() {
    }

    public LuiSetEntity(LuiSet luiSet) {
        super(luiSet);
        this.setId(luiSet.getId());
        fromDto(luiSet);
    }

    public void fromDto(LuiSet luiSet) {

        this.setLuiSetType(luiSet.getTypeKey());
        this.setLuiSetState(luiSet.getStateKey());
        this.setName(luiSet.getName());
        this.getLuiIds().clear();
        this.getLuiIds().addAll(luiSet.getLuiIds());

        this.getAttributes().clear();
        for (Attribute attributeInfo : luiSet.getAttributes()) {
            LuiSetAttributeEntity luiSetAttributeEntity = new LuiSetAttributeEntity(attributeInfo,this);
            this.getAttributes().add(luiSetAttributeEntity);
        }

        if (luiSet.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(luiSet.getDescr().getFormatted());
            this.setDescrPlain(luiSet.getDescr().getPlain());
        }
    }

    public LuiSetInfo toDto() {

        LuiSetInfo luiSetInfo = new LuiSetInfo();

        luiSetInfo.setId(getId());
        luiSetInfo.setName(getName());
        luiSetInfo.setTypeKey(getLuiSetType());
        luiSetInfo.setStateKey(getLuiSetState());
        luiSetInfo.setMeta(super.toDTO());
        luiSetInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));

        if (luiSetInfo.getLuiIds() == null){
            luiSetInfo.setLuiIds(new ArrayList<String>());
        }

        luiSetInfo.getLuiIds().addAll(luiIds);

        luiSetInfo.setAttributes(TransformUtility.toAttributeInfoList(this));

        return luiSetInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLuiSetType() {
        return luiSetType;
    }

    public void setLuiSetType(String luiSetType) {
        this.luiSetType = luiSetType;
    }

    public String getLuiSetState() {
        return luiSetState;
    }

    public void setLuiSetState(String luiSetState) {
        this.luiSetState = luiSetState;
    }

    public List<String> getLuiIds() {
        return luiIds;
    }

    public void setLuiIds(List<String> luiIds) {
        this.luiIds = luiIds;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    @Override
    public void setAttributes(Set<LuiSetAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LuiSetAttributeEntity> getAttributes() {
        return attributes;
    }
}
