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

package org.kuali.student.common.assembly.dictionary.old;

import java.util.List;

import org.kuali.student.common.assembly.data.ConstraintMetadata;
import org.kuali.student.common.assembly.data.LookupMetadata;


/**
 * This defines a field in the orchestration data object
 * 
 *   This is similar to the org.kuali.student.common.assembly.client.Metadata and
 *   the org.kuali.student.core.dictionary.dto.Field and org.kuali.student.core.dictionary.dto.FieldDescriptor objects. 
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public class DataFieldDescriptor {    
    protected String name;
    
    protected String desc;
    
    protected String writeAccess;
    
    private boolean canUnmask = false;

    private boolean canView = true;
    
    private boolean canEdit = true;

    protected String permission;
    
    protected String dataType;
    
    protected Object defaultValue;
    
    protected String defaultValuePath;

    protected boolean onChangeRefreshMetadata;
    
    protected String lookupContextPath;
    
    protected List<ConstraintMetadata> constraints;
    
    protected LookupMetadata initialLookup;
    
    protected List<LookupMetadata> additionalLookups;   
    
    protected DataObjectStructure dataObjectStructure;
        
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

    public LookupMetadata getInitialLookup() {
        return initialLookup;
    }

    public void setInitialLookup(LookupMetadata initialLookup) {
        this.initialLookup = initialLookup;
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

    public boolean isCanUnmask() {
        return canUnmask;
    }

    public void setCanUnmask(boolean canUnmask) {
        this.canUnmask = canUnmask;
    }

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public void setAdditionalLookups(List<LookupMetadata> additionalLookups) {
        this.additionalLookups = additionalLookups;
    }
}
