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
package org.kuali.student.r2.core.class1.process.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class represents table KSEN_PROCESS_CATEGORY_ATTR
 *
 * @author Kuali Student Team
 */
@Entity
@Table (name = "KSEN_PROCESS_CATEGORY_ATTR")
public class ProcessCategoryAttributeEntity extends BaseAttributeEntityNew<ProcessCategoryEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ProcessCategoryEntity owner;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public ProcessCategoryAttributeEntity () {
    }

    public ProcessCategoryAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ProcessCategoryAttributeEntity(Attribute att) {
        super(att);
    }

    public ProcessCategoryAttributeEntity(Attribute att, ProcessCategoryEntity owner) {
        super(att);
        setOwner(owner);
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public ProcessCategoryEntity getOwner() {
        return owner;
    }

    public void setOwner(ProcessCategoryEntity owner) {
        this.owner = owner;
    }
}
