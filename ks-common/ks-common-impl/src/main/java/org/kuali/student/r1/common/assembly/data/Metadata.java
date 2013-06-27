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

package org.kuali.student.r1.common.assembly.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Metadata implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum WriteAccess {
        ON_CREATE, /* must also be required */
        ALWAYS, NEVER, WHEN_NULL, REQUIRED
    }

    private String name;
    private String labelKey;
    private WriteAccess writeAccess;
    
    private boolean canUnmask = false;
    private boolean canView = true;
    private boolean canEdit = true;
    private boolean dynamic = false;
    
	protected String partialMaskFormatter;//Regex replace to do a partial mask  	
	protected String maskFormatter;//Regex replace to do a mask
	
	private boolean onChangeRefreshMetadata;

    private Data.DataType dataType;
    
    private Data.Value defaultValue;
    
    private String defaultValuePath;
    
    //TODO: When all dictionaries have been updated, this needs to be changed to a single value object.
    //No need for it to be a list with new dictionary structure. 
    private List<ConstraintMetadata> constraints;
    
    private LookupMetadata initialLookup;

    private String lookupContextPath;
    
    private List<LookupMetadata> additionalLookups;

    private Map<String, Metadata> childProperties;
    
    public Metadata() {
        
    }
    
    /**
     * 
     * This constructs a new Metadata instance. References to non-Metadata objects are maintained, as no permissions are applied to them.
     * 
     * @param toClone the Metadata instance to be cloned
     */
    public Metadata(Metadata toClone) {
        this.additionalLookups = toClone.additionalLookups;
        this.canEdit = toClone.canEdit;
        this.canUnmask = toClone.canUnmask;
        this.canView = toClone.canView;
        if(toClone.childProperties != null) {
            this.childProperties = new HashMap<String, Metadata>();
            for(Map.Entry<String, Metadata> childProperty : toClone.childProperties.entrySet()) {
                this.childProperties.put(childProperty.getKey(), new Metadata(childProperty.getValue()));
            }
            
        }
        this.constraints = toClone.constraints;
        this.dataType = toClone.dataType;
        this.defaultValue = toClone.defaultValue;
        this.defaultValuePath = toClone.defaultValuePath;
        this.dynamic = toClone.dynamic;
        this.initialLookup = toClone.initialLookup;
        this.labelKey = toClone.labelKey;
        this.lookupContextPath = toClone.lookupContextPath;
        this.maskFormatter = toClone.maskFormatter;
        this.name = toClone.name;
        this.onChangeRefreshMetadata = toClone.onChangeRefreshMetadata;
        this.partialMaskFormatter = toClone.partialMaskFormatter;
        this.writeAccess = toClone.writeAccess;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        _toString(sb);
        return sb.toString();
    }
    
    protected void _toString(StringBuilder sb) {
        Data.DataType type = (null == dataType) ? Data.DataType.DATA : dataType;
        sb.append("type: " + type.toString());
        sb.append(", canEdit: " + canEdit);
        sb.append(", canView: " + canView);
        sb.append(", defaultValue: ");
        sb.append(null == defaultValue ? "null" : defaultValue.toString());
        sb.append(", constraints: {");
        if (null != constraints) {
            for (ConstraintMetadata constraint : constraints) {
                sb.append(constraint.toString());
            }
        }
        sb.append("}");
        sb.append(", Properties: {");
        if (null != childProperties) {
            for (Entry<String, Metadata> e : childProperties.entrySet()) {
                sb.append("(");
                sb.append(e.getKey());
                sb.append(" = ");
                Metadata m = e.getValue();
                if (m == null) {
                    sb.append("null");
                } else {
                    m._toString(sb);
                }
                sb.append(");");
            }
        }
        sb.append("}");
        // TODO dump lookup/etc info as well
    }

    public List<ConstraintMetadata> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<ConstraintMetadata>();
        }
        return constraints;
    }

    public void setConstraints(List<ConstraintMetadata> constraints) {
    	this.constraints = constraints;
    }

    /**
     * This is used to set all non-server side constraints for the metadata.
     * 
     * @param constraints
     */
    public void setNonServerConstraints(List<ConstraintMetadata> constraints) {
    	if (constraints != null){
    		List<ConstraintMetadata> metadataConstraints = new ArrayList<ConstraintMetadata>();
    		for (ConstraintMetadata constraint:constraints){
    			if (!"single".equals(constraint.getId()) && 
    				!"optional".equals(constraint.getId()) &&
    				!constraint.isServerSide()){
    				metadataConstraints.add(constraint);
    			}
    		}
            this.constraints = metadataConstraints;
    	}
    }

    public Data.DataType getDataType() {
        return dataType;
    }

    public void setDataType(Data.DataType dataType) {
        this.dataType = dataType;
    }

    public Data.Value getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Data.Value defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValuePath() {
        return defaultValuePath;
    }

    public void setDefaultValuePath(String defaultValuePath) {
        this.defaultValuePath = defaultValuePath;
    }

    public LookupMetadata getInitialLookup() {
        return initialLookup;
    }

    public void setInitialLookup(LookupMetadata initialLookup) {
        this.initialLookup = initialLookup;
    }

    public String getLookupContextPath() {
        return lookupContextPath;
    }

    public void setLookupContextPath(String lookupContextPath) {
        this.lookupContextPath = lookupContextPath;
    }

    public List<LookupMetadata> getAdditionalLookups() {
        if (additionalLookups == null) {
            additionalLookups = new ArrayList<LookupMetadata>();
        }
        return additionalLookups;
    }

    public void setAdditionalLookups(List<LookupMetadata> additionalLookups) {
        this.additionalLookups = additionalLookups;
    }

    public Map<String, Metadata> getProperties() {
        if (childProperties == null) {
            childProperties = new LinkedHashMap<String, Metadata>();
        }
        return childProperties;
    }

    public void setProperties(Map<String, Metadata> properties) {
        this.childProperties = properties;
    }

    public WriteAccess getWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(WriteAccess writeAccess) {
        this.writeAccess = writeAccess;
    }

    
    public boolean isOnChangeRefreshMetadata() {
        return onChangeRefreshMetadata;
    }

    public void setOnChangeRefreshMetadata(boolean onChangeRefereshMetadata) {
        this.onChangeRefreshMetadata = onChangeRefereshMetadata;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

    public String getPartialMaskFormatter() {
		return partialMaskFormatter;
	}

	public void setPartialMaskFormatter(String partialMaskFormatter) {
		this.partialMaskFormatter = partialMaskFormatter;
	}

	public String getMaskFormatter() {
		return maskFormatter;
	}

	public void setMaskFormatter(String maskFormatter) {
		this.maskFormatter = maskFormatter;
	}
}
