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
 * User: hjohnson
 * Date: 2-Jun-2010
 * Time: 3:26:53 PM
 *
 */

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityGroupBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityTableBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroup;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityTable;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.SwapCompositeCondition;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.SwapCompositeConditionFieldConfig;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.TableFieldLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * This class creates a section containing a multiplicity widget based on the supplied configuration
 *
 * Sample code to use this class :-
 * 
 *
 * {@code
    private void addVersionCodeFields(Section section) {
        QueryPath parentPath = QueryPath.concat(COURSE, QueryPath.getPathSeparator(), VERSIONS);

        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP, getMetaData(parentPath.toString()));
        config.setAddItemLabel(getLabel(LUConstants.ADD_VERSION_CODE_LABEL_KEY));
        config.setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        config.setUpdateable(true);

        FieldDescriptor parentFd = buildFieldDescriptor(COURSE + QueryPath.getPathSeparator() + VERSIONS, getLabel(LUConstants.VERSION_CODES_LABEL_KEY), null);
        config.setParentFd(parentFd);

        FieldDescriptor versionCode = buildFieldDescriptor(CreditCourseVersionsConstants.VERSION_CODE, LUConstants.VERSION_CODE_LABEL_KEY, parentPath.toString());
        FieldDescriptor versionTitle = buildFieldDescriptor(CreditCourseVersionsConstants.VERSION_TITLE, LUConstants.TITLE_LABEL_KEY, parentPath.toString());
        config.addField(versionCode);
        config.addField(versionTitle);

        MultiplicitySection ms = new MultiplicitySection(config);
        section.addSection(ms);
        }
 * }
 * 
 * TODO:
 *   - Create factory methods for each 'flavour' of multiplicity
 *   - Styling options for table, e.g. no grid lines
 *   - For read-only multiplicities, set contained widgets to be read only too
 *   
 */
@Deprecated
    public class MultiplicitySection extends BaseSection {

    private MultiplicityConfiguration config;
    private Widget widget;
    private Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition;
    private List<String> deletionParentKeys;
    public MultiplicitySection(MultiplicityConfiguration config) {
        this.config = config;
        initialize();
    }

    public MultiplicitySection( 
            MultiplicityConfiguration config,
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        this.config = config;
        this.swappableFieldsDefinition = swappableFieldsDefinition;
        this.deletionParentKeys = deletionParentKeys;
        initialize();
    }
    

    private void initialize() {
        buildLayout();
        this.add(layout);
    }

    /**
     * Builds a suitable layout and adds the required multiplicity widget
     *
     * Builds a MultiplicityGroup for a grid layout or a MultiplicityTable
     * for a FlexTable layout . Sets the appropriate binding for the selected
     * widget
     *
     */
    private void buildLayout() {

        if (config.getParentFd() == null) {
            KSErrorDialog.show (new Throwable ("Multiplicity Parent FD cannot be null"));
            return;
        }
         switch (config.getLayoutType()) {
            case GROUP:
                layout = new VerticalFieldLayout(config.getTitle());
                if (config.getCustomMultiplicityGroup() == null) {
                    widget = new MultiplicityGroup(config, swappableFieldsDefinition, deletionParentKeys);
                } else {
                    widget = config.getCustomMultiplicityGroup();
                }
                config.getParentFd().setFieldWidget(widget);
                config.getParentFd().setWidgetBinding(new MultiplicityGroupBinding());
                this.addField(config.getParentFd());

                break;
            case TABLE:
                if (config.getFields().size() > 1) {
                    KSErrorDialog.show (new Throwable ("MultiplicityTable can have only one row defined"));
                    return;
                }
                if (config.getTitle() == null) {
                    layout = new TableFieldLayout();
                }
                else {
                    layout = new TableFieldLayout(config.getTitle(), false);
                }
                widget = new MultiplicityTable(config);
                config.getParentFd().setFieldWidget(widget);
                config.getParentFd().setWidgetBinding(new MultiplicityTableBinding());
                this.addField(config.getParentFd());

                break;
            default:
                layout = null;
            }
        }

    public void setParentPath(String parentPath) {
    	if (widget instanceof MultiplicityGroup) {
    		((MultiplicityGroup)widget).setParentPath(parentPath);
    	}
    	else {
            ((MultiplicityTable)widget).setParentPath(parentPath);
    	}
    	
    }

    @Override
    public void resetFieldInteractionFlags() {
    	super.resetFieldInteractionFlags();
		if (widget instanceof MultiplicityGroup){
			((MultiplicityGroup)widget).resetDirtyFlags();
		}		
    }

    @Override
	public void resetDirtyFlags() {
		super.resetDirtyFlags();
		if (widget instanceof MultiplicityGroup){
			((MultiplicityGroup)widget).resetDirtyFlags();
		}		
	}

	@Override
	public boolean isDirty() {
		boolean isDirty = super.isDirty();
		if (!isDirty && widget instanceof MultiplicityGroup){
			isDirty = ((MultiplicityGroup)widget).isDirty();
		}
		
		return isDirty;
	}
	
	public MultiplicityConfiguration getConfig(){
		return config;
	}
}
