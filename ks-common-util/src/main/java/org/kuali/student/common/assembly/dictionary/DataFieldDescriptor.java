/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.common.assembly.dictionary;

import java.util.List;

import org.kuali.student.common.assembly.client.ConstraintMetadata;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.masking.Mask;


/**
 * This defines a field in the orchestration data object
 * 
 *   This is similar to the org.kuali.student.common.assembly.client.Metadata and
 *   the org.kuali.student.core.dictionary.dto.Field and org.kuali.student.core.dictionary.dto.FieldDescriptor objects. 
 * 
 * @author Kuali Student Team
 *
 */
public class DataFieldDescriptor {    
    protected String name;
    
    protected String desc;
    
    protected String writeAccess;
    
    protected String permission;
    
    protected String dataType;
    
    protected Object defaultValue;
    
    protected String defaultValuePath;

    protected boolean onChangeRefreshMetadata;
    
    protected String lookupContextPath;
    
    protected List<ConstraintMetadata> constraints;
    
    protected LookupMetadata lookups;
    
    protected List<LookupMetadata> additionalLookups;   
    
    protected DataObjectStructure dataObjectStructure;
    
    protected Mask mask;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWriteAccess() {
        return writeAccess;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    
    public void setWriteAccess(String writeAccess) {
        this.writeAccess = writeAccess;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<ConstraintMetadata> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<ConstraintMetadata> constraints) {
        this.constraints = constraints;
    }

    public boolean isOnChangeRefreshMetadata() {
        return onChangeRefreshMetadata;
    }

    public void setOnChangeRefreshMetadata(boolean onChangeRefreshMetadata) {
        this.onChangeRefreshMetadata = onChangeRefreshMetadata;
    }

    public String getLookupContextPath() {
        return lookupContextPath;
    }

    public void setLookupContextPath(String lookupContextPath) {
        this.lookupContextPath = lookupContextPath;
    }

    public LookupMetadata getLookups() {
        return lookups;
    }

    public void setLookups(LookupMetadata lookups) {
        this.lookups = lookups;
    }

    public List<LookupMetadata> getAdditionalLookups() {
        return additionalLookups;
    }

    public String getDefaultValuePath() {
        return defaultValuePath;
    }

    public void setDefaultValuePath(String defaultValuePath) {
        this.defaultValuePath = defaultValuePath;
    }

    public DataObjectStructure getDataObjectStructure() {
        return dataObjectStructure;
    }

    public void setDataObjectStructure(DataObjectStructure dataObjectStructure) {
        this.dataObjectStructure = dataObjectStructure;
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }
}
