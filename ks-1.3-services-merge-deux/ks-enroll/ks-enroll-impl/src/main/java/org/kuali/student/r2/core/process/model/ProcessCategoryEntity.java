/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Mezba Mahtab on 6/7/12
 */
package org.kuali.student.r2.core.process.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.infc.ProcessCategory;

/**
 * This class represents the KSEN_PROCESS_CATEGORY table.
 *
 * @author Mezba Mahtab
 */
@Entity
@Table (name = "KSEN_PROCESS_CATEGORY")
public class ProcessCategoryEntity extends MetaEntity implements AttributeOwner<ProcessCategoryAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "PROCESS_CATEGORY_TYPE", nullable = false)
    private String categoryType;

    @Column(name = "PROCESS_CATEGORY_STATE", nullable = false)
    private String categoryState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch=FetchType.EAGER)
    private Set<ProcessCategoryAttributeEntity> attributes;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public ProcessCategoryEntity() {}

    public ProcessCategoryEntity(ProcessCategory processCategory) {
        super(processCategory);
        this.setId(processCategory.getId());
        categoryType = processCategory.getTypeKey();
        this.fromDTO(processCategory);
    }

    public void fromDTO(ProcessCategory processCategory) {
        categoryState = processCategory.getStateKey();
        name = processCategory.getName();
        if (processCategory.getDescr() != null) {
            descrFormatted = processCategory.getDescr().getFormatted();
            descrPlain = processCategory.getDescr().getPlain();
        } else {
            descrFormatted = null;
            descrPlain = null;
        }
        this.setAttributes(new HashSet<ProcessCategoryAttributeEntity>(processCategory.getAttributes().size()));
        for (Attribute att : processCategory.getAttributes()) {
            this.getAttributes().add(new ProcessCategoryAttributeEntity(att, this));
        }
    }

    /**
     * @return Process Check DTO
     */
    public ProcessCategoryInfo toDto() {
        ProcessCategoryInfo processCategoryInfo = new ProcessCategoryInfo();
        processCategoryInfo.setMeta(super.toDTO());
        processCategoryInfo.setId(getId());
        processCategoryInfo.setTypeKey(categoryType);
        processCategoryInfo.setStateKey(categoryState);
        processCategoryInfo.setName(name);
        processCategoryInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        List<AttributeInfo> attributes = processCategoryInfo.getAttributes();
        if (getAttributes() != null) {
            for (ProcessCategoryAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        processCategoryInfo.setAttributes(attributes);
        return processCategoryInfo;
    }

        ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////


    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryState() {
        return categoryState;
    }

    public void setCategoryState(String categoryState) {
        this.categoryState = categoryState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public Set<ProcessCategoryAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<ProcessCategoryAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}
