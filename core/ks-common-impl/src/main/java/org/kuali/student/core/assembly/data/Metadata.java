/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.assembly.binding.JaxbMetadataPropertyMapAdapter;
import org.kuali.student.core.assembly.data.masking.Mask;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Metadata implements Serializable {
    // TODO this class, and referenced classes, need to be moved into a GWT
    // module

    private static final long serialVersionUID = 1L;

    public enum WriteAccess {
        ON_CREATE, /* must also be required */
        ALWAYS, NEVER, WHEN_NULL
    }

    public enum Permission {
        EDIT("edit"), READ_ONLY("readonly"), UNMASK("unmask");
        final String kimName;
        private Permission(String kimName) {
            this.kimName = kimName;
        }
        @Override
        public String toString() {
            return kimName;
        }
        public static Permission kimValueOf(String kimName) {
            for(Permission p : values()) {
                if(p.kimName.equals(kimName)) {
                    return p;
                }
            }
            //fall through
            throw new IllegalArgumentException("The value " + kimName + " is not enumerated in Permission"); 
        }
    }
    private String name;
    private WriteAccess writeAccess;
    
    private boolean canUnmask = false;
    private boolean canView = true;
    private boolean canEdit = true;
    
    private boolean onChangeRefreshMetadata;

    private Mask mask;
    private Data.DataType dataType;
    
    @XmlAnyElement
    @XmlElementRefs({
        @XmlElementRef(type=Data.BooleanValue.class),
        @XmlElementRef(type=Data.StringValue.class),
        @XmlElementRef(type=Data.IntegerValue.class),
        @XmlElementRef(type=Data.DoubleValue.class),
        @XmlElementRef(type=Data.FloatValue.class),
        @XmlElementRef(type=Data.LongValue.class),
        @XmlElementRef(type=Data.ShortValue.class),
        @XmlElementRef(type=Data.DataValue.class),
        @XmlElementRef(type=Data.TimestampValue.class),
        @XmlElementRef(type=Data.TimeValue.class)
    })
    private Data.Value defaultValue;
    
    private String defaultValuePath;
    
    @XmlElement(name="constraint")
    @XmlElementWrapper
    private List<ConstraintMetadata> constraints;
    
    private LookupMetadata initialLookup;

    private String lookupContextPath;
    
    @XmlElement(name="lookupMetadata")
    @XmlElementWrapper(name="additionalLookups")
    private List<LookupMetadata> additionalLookups;

    @XmlElement(name="properties")
    @XmlJavaTypeAdapter(JaxbMetadataPropertyMapAdapter.class)
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
        this.constraints = toClone.constraints;
        this.dataType = toClone.dataType;
        this.defaultValue = toClone.defaultValue;
        this.defaultValuePath = toClone.defaultValuePath;
        this.lookupContextPath = toClone.lookupContextPath;
/*        if(toClone.lookupMetadata != null) {
            this.lookupMetadata = new LookupMetadata(toClone.lookupMetadata);
        }*/
        this.initialLookup = toClone.initialLookup;
        this.onChangeRefreshMetadata = toClone.onChangeRefreshMetadata;
        this.name = toClone.name;
        this.writeAccess = toClone.writeAccess;
        this.canEdit = toClone.canEdit;
        this.canView = toClone.canView; 
        this.canUnmask = toClone.canUnmask;
        if(toClone.childProperties != null) {
            this.childProperties = new HashMap<String, Metadata>();
            for(Map.Entry<String, Metadata> childProperty : toClone.childProperties.entrySet()) {
                this.childProperties.put(childProperty.getKey(), new Metadata(childProperty.getValue()));
            }
            
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        _toString(sb);
        return sb.toString();
    }

    protected void _toString(StringBuilder sb) {
        Data.DataType type = (dataType == null) ? Data.DataType.DATA : dataType;
        sb.append("Type: ");
        sb.append(type.toString());
        sb.append(", Default: ");
        sb.append(defaultValue == null ? "null" : defaultValue.toString());
        sb.append(", Properties: {");
        if (childProperties != null) {
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
        // TODO dump lookup/constraint/etc info as well
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

    public Data.DataType getDataType() {
        return dataType;
    }

    /**
     * @deprecated
     * @see #setDataType
     */
    public void setDataType(String strType) {
        for (Data.DataType dt : Data.DataType.values()) {
            if (dt.toString().equalsIgnoreCase(strType)) {
                setDataType(dt);
                return;
            }
        }
        throw new IllegalArgumentException(strType);
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
            additionalLookups = new ArrayList();
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

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
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
}
