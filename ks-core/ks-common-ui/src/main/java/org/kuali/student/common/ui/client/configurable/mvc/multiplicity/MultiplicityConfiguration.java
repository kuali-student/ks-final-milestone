/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"                       
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
  *
  * MultiplicityConfiguration is passed into a MultiplicitySection to control the creation of the multiplicity.
  *
  * This class is used to control the Multiplicity, e.g. if its updateable, has headers, min number of items, labels etc.
  * It also holds FieldDescriptors for the fields required to be included in the multiplicity. They are
  * held in a HashMap keyed by rows, i.e. Map<Integer, List<FieldDescriptor>> .
  *
  * The parent for the multiplicity refers to the path to the high level field that owns
  * the repeating elements, e.g. course/versions is the parent field for course version fields versionCode and
  * versionTitle
  *
  * For multiplicities nested inside another multiplicity, create a MultiplicityConfiguration for the nested
  * multiplicity and call setNestedConfig to add the lower level multiplicity
  *
  *
 */
public class MultiplicityConfiguration {

    private MultiplicityType multiplicityType;
    
    private StyleType styleType;
    private boolean updateable = false;

    private String itemLabel;
    private String addItemLabel;
    
    private Metadata metaData;
    
    private SectionTitle title;

    int row = 0;
    
    private FieldDescriptor parentFd;
    private Map<Integer, List<MultiplicityFieldConfiguration>> fields = new HashMap<Integer, List<MultiplicityFieldConfiguration>>();
    protected Map<String, String> concatenatedFields = new HashMap<String, String>();
    
    private MultiplicityConfiguration nestedConfig ;
    private MultiplicityGroup customMultiplicityGroup;
    private boolean showHeaders;

    public static enum MultiplicityType {GROUP, TABLE }
    public static enum StyleType {
        TOP_LEVEL_GROUP, SUB_LEVEL_GROUP, BORDERLESS_TABLE, BORDERED_TABLE
    }


     /**
     *
     * Creates a new MultiplicityConfiguration.
     *
     *
     * @param multiplicityType      the type of multiplicity required, MultiplicityType
     * @param styleType             the style type for this multiplicity, StyleType
     * @param metaData              the metadata for the parentFd
     */
     public MultiplicityConfiguration(MultiplicityType multiplicityType, StyleType styleType, Metadata metaData) {
        this.multiplicityType = multiplicityType;
        this.styleType = styleType;
        this.metaData = metaData;
    }

    /**
     * Causes a line throw so that the subsequent fields are placed on the next line of the screen
     *
     */
    public void nextLine() {
    	row++;
    }

    /**
     * Includes this field (as defined by the FieldDescriptor) on the current line at the next horizontal position
     *
     *
     * @param fieldDescriptor
     */
    public void addFieldConfiguration(MultiplicityFieldConfiguration fieldDescriptor) {
    	
        List<MultiplicityFieldConfiguration> fds;
        if (fields.containsKey(row)) {
            fds = fields.get(row);
        }
        else {
            fds = new ArrayList<MultiplicityFieldConfiguration>();
        }
        fds.add(fieldDescriptor);
        fields.put(row, fds);
    }
    
    public void setFields(Map<Integer, List<MultiplicityFieldConfiguration>> fields) {
        if (fields == null || fields.isEmpty()) {
            row = 0;
        } else {
            row = fields.size() - 1;
        }
        this.fields = fields;
    }

	/**
	 * Concatenates multiple field values into a single table cell
     *
     *  !!!!!  Currently only implemented for MultiplicityTable !!!!!
     *
     * The parentField should define a field with a Data value. The fieldKey should resolve to a single value
	 * field in that Data value. The binding will iterate the values in the Data object
	 * and concatenate the named fieldKeys into the same cell in the current row of the table
	 * separated by commas.
     *
     * e.g. parentFD = "course/versions", fieldKey = "versionCode", version codes will be concatenated to
     * display "code1, code2, code3" in a single table cell
	 *
	 * @param fieldKey
	 */
	public void addConcatenatedField(MultiplicityFieldConfiguration parentField, String fieldKey){
		addFieldConfiguration(parentField);
		concatenatedFields.put(parentField.getFieldPath(), fieldKey);
	}

    /**
     * Creates a copy of this MultiplicityConfiguration. Used when creating a new MultiplicityItem
     *
     * @return  MultiplicityConfiguration
     */
    public MultiplicityConfiguration copy() {
        MultiplicityConfiguration copy = new MultiplicityConfiguration(getMultiplicityType(), getStyleType(), getMetaData());
        copy.setAddItemLabel(getAddItemLabel());
        copy.setItemLabel(getItemLabel());
        copy.setMetaData(getMetaData());
        copy.setUpdateable(isUpdateable());
        if (getNestedConfig() != null) {
            copy.setNestedConfig(getNestedConfig().copy());
        }
        copy.setParent(new FieldDescriptor(getParentFd().getFieldKey(), getParentFd().getMessageKey(), getParentFd().getMetadata()));
        for (Integer row  : getFields().keySet()) {
            List<MultiplicityFieldConfiguration> fields = getFields().get(row);
            for (MultiplicityFieldConfiguration fieldConfig : fields) {
                MultiplicityFieldConfiguration newfieldConfig = new MultiplicityFieldConfiguration(fieldConfig.getFieldPath(), fieldConfig.getMessageKeyInfo(), fieldConfig.getMetadata(), fieldConfig.getFieldWidgetInitializer());
                copy.addFieldConfiguration(newfieldConfig);
            }
            copy.nextLine();
        }
        return copy;
    }

    public MultiplicityType getMultiplicityType() {
        return multiplicityType;
    }

     /**
      * Sets the MultiplicityType required for this config
      *
      * Valid values are defined in {@link #MultiplicityConfiguration.MultiplicityType}
      * @param multiplicityType
      */
     public void setMultiplicityType(MultiplicityType multiplicityType) {
        this.multiplicityType = multiplicityType;
    }

	public FieldDescriptor getParentFd() {
		return parentFd;
	}

    /**
     * The parent fd defines the high level parent field that contains the repeating elements
     *
     * @param parentFd
     */
    public void setParent(FieldDescriptor parentFd) {
		this.parentFd = parentFd;
	}

//	public Map<Integer, List<FieldDescriptor>> getFields() {return null;}

	public Map<Integer, List<MultiplicityFieldConfiguration>> getFields() {
		return fields;
	}

    public String getItemLabel() {
        return itemLabel;
    }

    /**
     * Sets text to be used as the header for each multiplicity item
     * @param itemLabel
     */
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getAddItemLabel() {
        return addItemLabel;
    }

    /**
     * Sets text to be used as the button text to add a new empty item in the multiplicity
     * @param addItemLabel
     */
    public void setAddItemLabel(String addItemLabel) {
        this.addItemLabel = addItemLabel;
    }

	public StyleType getStyleType() {
		return styleType;
	}

	public void setStyleType(StyleType styleType) {
		this.styleType = styleType;
	}

	public MultiplicityConfiguration getNestedConfig() {
		return nestedConfig;
	}

    /**
     * If this multiplicity is to contain other nested multiplicities, create a MultiplicityConfiguration for the child
     * multiplicity and set as a nested config in the parent.
     *
     * @param config
     */
    public void setNestedConfig(MultiplicityConfiguration config) {
		this.nestedConfig = config;
	}

    public boolean isShowHeaders() {
         return showHeaders;
    }

    /**
     *
     * @param showHeaders
     */
    public void setShowHeaders(boolean showHeaders) {
        this.showHeaders = showHeaders;
    }

	public Map<String, String> getConcatenatedFields() {
		return concatenatedFields;
	}

    public boolean isUpdateable() {
        return updateable;
    }

    /**
     * Sets if this multiplicity will be updateable or display.
     * Add delete buttons/links will not be shown for display multiplicities
     *
     * @param updateable
     */
    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public MultiplicityType getLayoutType() {
        return multiplicityType;
    }

    public void setLayoutType(MultiplicityType multiplicityType) {
        this.multiplicityType = multiplicityType;
    }

	public Metadata getMetaData() {
		return metaData;
	}

	public void setMetaData(Metadata metaData) {
		this.metaData = metaData;
	}


     public SectionTitle getTitle() {
        return title;
    }

    public void setTitle(SectionTitle title) {
        this.title = title;
    }

    /**
     * Creates a field descriptor for the parent for this multiplicity
     * This defines the high level parent field that contains the repeating elements
     * Will use default widget and binding. For more complex fields create your own
     * fieldDescriptor and use the other setParent method
     *
     * @param fieldKey
     * @param messageKey
     * @param parentPath
     * @param meta
     */
    public void setParent(String fieldKey, String messageKey,  String parentPath, Metadata meta) {

        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        FieldDescriptor fd = new FieldDescriptor(path.toString(), new MessageKeyInfo(messageKey), meta);
        fd.getFieldElement().setRequired(false);

        setParent(fd);

    }

    /**
     * Includes this field on the current line at the next horizontal position
     *
     * Will use default widget and binding. For more complex fields create your own
     * FieldDescriptor and pass it in using the other addField method
     *
     * @param fieldKey
     * @param messageKey
     * @param parentPath
     * @param meta
     */
    public void addField(String fieldKey, String messageKey,  String parentPath, Metadata meta) {

        QueryPath path = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);

        MultiplicityFieldConfiguration fieldConfig = new MultiplicityFieldConfiguration(path.toString(), new MessageKeyInfo(messageKey), meta, null);
        fieldConfig.setRequired(false);
        addFieldConfiguration(fieldConfig);
    }

//    private String translatePath(String path, Metadata metadata) {
//
//        String result = path;
//
//        QueryPath qPath = QueryPath.parse(path);
//
//        if(metadata!=null&&metadata.getInitialLookup()!=null){
//            QueryPath translationPath = qPath.subPath(0, qPath.size()-1);
//            if (metadata.getDataType().equals(Data.DataType.STRING)) {
//                translationPath.add(new Data.StringKey("_runtimeData"));
//                translationPath.add(new Data.StringKey((String)qPath.get(qPath.size() - 1).get()));
//                translationPath.add(new Data.StringKey("id-translation"));
//                result = translationPath.toString();
//            }
//        }
//        return result;
//    }

    public MultiplicityGroup getCustomMultiplicityGroup() {
        return customMultiplicityGroup;
    }

    public void setCustomMultiplicityGroup(MultiplicityGroup customMultiplicityGroup) {
        this.customMultiplicityGroup = customMultiplicityGroup;
    }
    
    
}

